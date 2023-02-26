package eu.animegame.jeva.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import eu.animegame.jeva.plugins.AutoJoinPlugin;
import eu.animegame.jeva.plugins.ConnectPlugin;
import eu.animegame.jeva.plugins.DisconnectPlugin;
import eu.animegame.jeva.plugins.PingPlugin;
import eu.animegame.jeva.plugins.ReJoinPlugin;

/**
 *
 * @author radiskull
 */
public class JEvaIrcEngineBuilder {

  private Properties properties;

  private List<Supplier<JEvaIrcPlugin>> plugins;

  private List<String> channels;

  private String[] args;

  private boolean autoRejoin;

  private JEvaIrcEngineBuilder() {
    this.properties = new Properties();
    this.channels = new ArrayList<>();
    this.plugins = new ArrayList<>();
    this.autoRejoin = false;
  }

  public static JEvaIrcEngineBuilder create() {
    return new JEvaIrcEngineBuilder();
  }

  public JEvaIrcEngineBuilder args(String... args) {
    this.args = args;
    return this;
  }

  public JEvaIrcEngineBuilder server(String server) {
    properties.put(IrcConfig.PROP_SERVER, server);
    return this;
  }

  public JEvaIrcEngineBuilder serverPassword(String password) {
    properties.put(IrcConfig.PROP_SERVER_PASSWORD, password);
    return this;
  }

  public JEvaIrcEngineBuilder port(String port) {
    properties.put(IrcConfig.PROP_PORT, port);
    return this;
  }

  public JEvaIrcEngineBuilder nick(String nick) {
    properties.put(IrcConfig.PROP_NICK, nick);
    return this;
  }

  public JEvaIrcEngineBuilder mode(String mode) {
    properties.put(IrcConfig.PROP_MODE, mode);
    return this;
  }

  public JEvaIrcEngineBuilder realName(String realName) {
    properties.put(IrcConfig.PROP_REAL_NAME, realName);
    return this;
  }

  public JEvaIrcEngineBuilder channel(String channel) {
    channels.add(channel);
    return this;
  }

  public JEvaIrcEngineBuilder autoRejoin(boolean enableAutoRejoin) {
    this.autoRejoin = enableAutoRejoin;
    return this;
  }

  public JEvaIrcEngineBuilder plugin(Supplier<JEvaIrcPlugin> plugin) {
    plugins.add(plugin);
    return this;
  }

  // TODO: Eventually add a Connection method too .. when there is an SSLConnection
  public JEvaIrcEngine build() {
    var ircConfig = new IrcConfig(properties);
    var engine = new JEvaIrcEngine(new SocketConnection(), ircConfig, args);
    engine.addPlugin(new ConnectPlugin());
    engine.addPlugin(new PingPlugin());
    if (!channels.isEmpty()) {
      addPluginAndChannels(engine);
    }
    if (autoRejoin) {
      engine.addPlugin(new ReJoinPlugin());
    }
    for (Supplier<JEvaIrcPlugin> plugin : plugins) {
      engine.addPlugin(plugin.get());
    }
    engine.addPlugin(new DisconnectPlugin());
    return engine;
  }

  private void addPluginAndChannels(JEvaIrcEngine engine) {

    var joinPlugin = new AutoJoinPlugin();
    engine.addPlugin(joinPlugin);

    for (String channel : channels) {
      joinPlugin.addChannel(channel);
    }
  }
}
