package eu.animegame.jeva.poc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;

/**
 *
 * @author radiskull
 */
public class TestHandler {

	List<IrcHandlerPlugin> list = new ArrayList<>();
	Map<String, List<Method>> callbacks = new HashMap<>();

	public static void main(String[] args) {
		TestHandler handler = new TestHandler();
		handler.addPlugin(new TestPlugin());
		handler.init();
		handler.recieveEvent("PING");
		handler.recieveEvent("PONG");
		handler.recieveEvent("TEST");
	}

	private void recieveEvent(String event) {
		System.out.println(callbacks);
		List<Method> methods = callbacks.get(event);
		if (methods != null) {
			methods.forEach(m -> {
				try {
					m.invoke(list.get(0));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			});
		}
	}

	private void init() {
		for (IrcHandlerPlugin plugin : list) {
			System.out.println(plugin.getClass().getName());
			for (Method method : plugin.getClass().getMethods()) {
				IrcEvent annotation = method.getAnnotation(IrcEvent.class);
				if (annotation != null) {
					String event = annotation.type();
					System.out.println(event);
					List<Method> methods = callbacks.get(event);
					if (methods == null) {
						methods = new ArrayList<>();
						methods.add(method);
						callbacks.put(event, methods);
					} else {
						if (!methods.contains(method)) {
							methods.add(method);
						}
					}
				}
			}
		}
	}

	public void addPlugin(IrcHandlerPlugin plugin) {
		if (!list.contains(plugin)) {
			list.add(plugin);
		}
	}

}
