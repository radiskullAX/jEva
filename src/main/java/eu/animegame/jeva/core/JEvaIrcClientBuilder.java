package eu.animegame.jeva.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import eu.animegame.jeva.plugins.AutoJoinPlugin;
import eu.animegame.jeva.plugins.ConnectPlugin;
import eu.animegame.jeva.plugins.PingPlugin;
import eu.animegame.jeva.plugins.ReJoinPlugin;

/**
 *
 * @author radiskull
 */
public class JEvaIrcClientBuilder {

  private Properties properties;

  private List<Supplier<JEvaIrcPlugin>> plugins;

  private List<String> channels;

  private String[] args;

  private boolean autoRejoin;

  private JEvaIrcClientBuilder() {
    this.properties = new Properties();
    this.channels = new ArrayList<>();
    this.plugins = new ArrayList<>();
    this.autoRejoin = false;
  }

  public static JEvaIrcClientBuilder create() {
    return new JEvaIrcClientBuilder();
  }

  public JEvaIrcClientBuilder args(String... args) {
    this.args = args;
    return this;
  }

  public JEvaIrcClientBuilder server(String server) {
    properties.put(IrcConfig.PROP_SERVER, server);
    return this;
  }

  public JEvaIrcClientBuilder serverPassword(String password) {
    properties.put(IrcConfig.PROP_SERVER_PASSWORD, password);
    return this;
  }

  public JEvaIrcClientBuilder port(String port) {
    properties.put(IrcConfig.PROP_PORT, port);
    return this;
  }

  public JEvaIrcClientBuilder nick(String nick) {
    properties.put(IrcConfig.PROP_NICK, nick);
    return this;
  }

  public JEvaIrcClientBuilder mode(String mode) {
    properties.put(IrcConfig.PROP_MODE, mode);
    return this;
  }

  public JEvaIrcClientBuilder realName(String realName) {
    properties.put(IrcConfig.PROP_REAL_NAME, realName);
    return this;
  }

  public JEvaIrcClientBuilder channel(String channel) {
    channels.add(channel);
    return this;
  }

  public JEvaIrcClientBuilder autoRejoin(boolean enableAutoRejoin) {
    this.autoRejoin = enableAutoRejoin;
    return this;
  }

  public JEvaIrcClientBuilder plugin(Supplier<JEvaIrcPlugin> plugin) {
    plugins.add(plugin);
    return this;
  }

  // TODO: Eventually add a Connection method too .. when there is an SSLConnection
  public JEvaIrcClient build() {
    var ircConfig = new IrcConfig(properties);
    var client = new JEvaIrcClient(new SocketConnection(), ircConfig, args);
    client.addPlugin(new ConnectPlugin());
    client.addPlugin(new PingPlugin());
    if (!channels.isEmpty()) {
      addPluginAndChannels(client);
    }
    if (autoRejoin) {
      client.addPlugin(new ReJoinPlugin());
    }
    for (Supplier<JEvaIrcPlugin> plugin : plugins) {
      client.addPlugin(plugin.get());
    }
    return client;
  }

  private void addPluginAndChannels(JEvaIrcClient client) {

    var joinPlugin = new AutoJoinPlugin();
    client.addPlugin(joinPlugin);

    for (String channel : channels) {
      joinPlugin.addChannel(channel);
    }
  }
}
