package eu.animegame.jeva.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author radiskull
 */
public class IrcPluginController {

  private static final Logger LOG = LoggerFactory.getLogger(IrcPluginController.class);

  private final JEvaIrcClient jEvaClient;

  private final List<JEvaIrcPlugin> plugins;

  private Map<String, List<CallbackEntry>> callbacks = new HashMap<>();

  public IrcPluginController(JEvaIrcClient jEvaClient) {
    this.jEvaClient = jEvaClient;
    this.plugins = new ArrayList<>();
  }

  public void addPlugin(JEvaIrcPlugin plugin) {
    if (plugin != null && !plugins.contains(plugin)) {
      LOG.trace("add plugin: {}", plugin.getClass().getSimpleName());
      plugins.add(plugin);
    }
  }

  public boolean removePlugin(JEvaIrcPlugin plugin) {
    if (plugin != null) {
      // TODO: if a plugin gets removed, we should also remove all callbacks
      LOG.trace("remove plugin: {}", plugin.getClass().getSimpleName());
      return plugins.remove(plugin);
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  public <P> Optional<P> getPlugin(Class<P> clazz) {
    LOG.trace("get plugin: {}", clazz);
    return (Optional<P>) plugins.stream()
        .filter(plugin -> plugin.getClass().equals(clazz)) //
        .findFirst();
  }

  public List<JEvaIrcPlugin> getPlugins() {
    return Collections.unmodifiableList(plugins);
  }

  public void fireLifecycleState(Consumer<JEvaIrcPlugin> consumer) {
    plugins.forEach(consumer);
  }

  // TODO: maybe do this when a plugin gets registered, this will be needed for plug & play
  public void lookup() {
    callbacks = plugins.stream()
        .map(this::toListOfCallbacks) //
        .flatMap(map -> map.entrySet().stream()) //
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, this::mergeLists));
    LOG.info("lookup plugins for @IrcEventAcceptor: [numberOfMethods: {}, numberOfCommands: {}]",
        callbacks.values().stream().mapToInt(l -> l.size()).sum(), callbacks.size());
    LOG.debug("commands: {}", callbacks.keySet().stream().collect(Collectors.joining(",")));
  }

  private List<CallbackEntry> mergeLists(List<CallbackEntry> leftList, List<CallbackEntry> rightList) {
    leftList.addAll(rightList);
    return leftList;
  }

  private Map<String, List<CallbackEntry>> toListOfCallbacks(JEvaIrcPlugin plugin) {
    return Stream.of(plugin.getClass().getMethods())
        .filter(method -> Objects.nonNull(method.getAnnotation(IrcEventAcceptor.class)))
        .map(method -> new CallbackEntry(plugin, method))
        .collect(
            Collectors.groupingBy(cbe -> cbe.method.getAnnotation(IrcEventAcceptor.class).command().toUpperCase()));
  }

  public void fireIrcEvent(final IrcBaseEvent event) {
    List<CallbackEntry> entries = callbacks.get(event.getCommand());
    if (entries != null) {
      entries.stream().forEach(method -> invokeMethod(method, event));
    }
  }

  private void invokeMethod(CallbackEntry entry, IrcBaseEvent event) {
    var method = entry.method;
    var args = new Object[0];
    try {
      args = buildArguments(method, event);
      method.invoke(entry.plugin, args);
    } catch (InvocationTargetException e) {
      LOG.warn("uncatched exception from method: [class: '{}', method: '{}']",
          method.getDeclaringClass().getSimpleName(), method.getName(), e.getCause());
    } catch (ReflectiveOperationException | IllegalArgumentException | SecurityException e) {
      LOG.warn("failed to invoke method: [class: '{}', method: '{}', injectedArgs: '{}']",
          entry.plugin.getClass().getSimpleName(), method.getName(), args, e);
    }
  }

  private Object[] buildArguments(Method method, IrcBaseEvent event)
      throws ReflectiveOperationException, IllegalArgumentException, SecurityException {
    var args = new Object[method.getParameterCount()];
    var params = method.getParameterTypes();

    for (int i = 0; i < params.length; i++) {
      Class<?> parameterType = params[i];
      if (IrcBaseEvent.class.isAssignableFrom(parameterType)) {
        // TODO: Buffer transformed event for other calls
        args[i] = getExactEvent(parameterType, event);
      } else if (JEvaIrcClient.class.equals(parameterType)) {
        args[i] = jEvaClient;
      } else {
        throw new IllegalArgumentException("Argument '" + parameterType.getName() + "' is not supported");
      }
    }
    return args;
  }

  private IrcBaseEvent getExactEvent(Class<?> parameterType, IrcBaseEvent event)
      throws ReflectiveOperationException, IllegalArgumentException, SecurityException {
    if (event.getClass().equals(parameterType)) {
      return event;
    }
    return (IrcBaseEvent) parameterType.getDeclaredConstructor(IrcBaseEvent.class).newInstance(event);
  }

  private class CallbackEntry {

    private final JEvaIrcPlugin plugin;

    private final Method method;

    public CallbackEntry(JEvaIrcPlugin plugin, Method method) {
      this.plugin = plugin;
      this.method = method;
    }
  }
}
