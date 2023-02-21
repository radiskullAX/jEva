package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Arrays;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.plugins.AutoJoinPlugin;
import eu.animegame.jeva.plugins.ReJoinPlugin;

/**
 * 
 *
 * @author radiskull
 */
class JEvaIrcClientBuilderTest {

  private static final String SERVER = "irc.animegame.eu";

  private static final String SERVER_PASSWORD = "secret";

  private static final String PORT = "6668";

  private static final String NICK = "IrcUser";

  private static final String MODE = "12";

  private static final String REAL_NAME = "jEva";

  private static final String CHANNEL = "#test";

  private JEvaIrcClientBuilder builder;

  private JEvaIrcClientBuilderTest() {
    builder = JEvaIrcClientBuilder.create();
  }

  @Test
  void create() {
    var client = builder.build();

    assertNotNull(client);
    assertNotNull(client.getConfig());
    assertEquals(0, client.getConfig().size());
    assertNotNull(client.getConnection());
    assertEquals(2, client.getPlugins().size());
  }

  @Test
  @Disabled
  void args() {
    // TODO: implement when args parsing works
  }

  @Test
  void server() {
    var client = builder.server(SERVER).build();

    var actual = client.getConfig().getProperty(IrcConfig.PROP_SERVER);
    assertEquals(SERVER, actual);
  }

  @Test
  void serverPassword() {
    var client = builder.serverPassword(SERVER_PASSWORD).build();

    var actual = client.getConfig().getProperty(IrcConfig.PROP_SERVER_PASSWORD);
    assertEquals(SERVER_PASSWORD, actual);
  }

  @Test
  void port() {
    var client = builder.port(PORT).build();

    var actual = client.getConfig().getProperty(IrcConfig.PROP_PORT);
    assertEquals(PORT, actual);
  }

  @Test
  void nick() {
    var client = builder.nick(NICK).build();

    var actual = client.getConfig().getProperty(IrcConfig.PROP_NICK);
    assertEquals(NICK, actual);
  }

  @Test
  void mode() {
    var client = builder.mode(MODE).build();

    var actual = client.getConfig().getProperty(IrcConfig.PROP_MODE);
    assertEquals(MODE, actual);
  }

  @Test
  void realName() {
    var client = builder.realName(REAL_NAME).build();

    var actual = client.getConfig().getProperty(IrcConfig.PROP_REAL_NAME);
    assertEquals(REAL_NAME, actual);
  }

  @Test
  void channel() {
    var client = builder.channel(CHANNEL).build();

    var opt = client.getPlugin(AutoJoinPlugin.class);
    assertTrue(opt.isPresent());

    var channels = opt.get().getChannels();
    assertIterableEquals(Arrays.asList(CHANNEL), channels);
  }

  @Test
  void autoRejoin() {
    var client = builder.autoRejoin(true).build();

    var opt = client.getPlugin(ReJoinPlugin.class);
    assertTrue(opt.isPresent());
  }

  @Test
  void plugin() {
    var plugin = mock(JEvaIrcPlugin.class);
    var client = builder.plugin(() -> plugin).build();

    var opt = client.getPlugin(plugin.getClass());
    assertTrue(opt.isPresent());
    assertEquals(3, client.getPlugins().size());
  }

  @Test
  void buildCreatesDifferentObjects() {
    builder.channel(CHANNEL).autoRejoin(true).plugin(() -> mock(JEvaIrcPlugin.class));
    var client1 = builder.build();
    var client2 = builder.build();
    var pluginList1 = client1.getPlugins();
    var pluginList2 = client2.getPlugins();

    client1.getConfig().put(IrcConfig.PROP_SERVER, SERVER);

    assertNotEquals(client1, client2);
    assertNotEquals(client1.getConfig(), client2.getConfig());
    assertEquals(null, client2.getConfig().getProperty(IrcConfig.PROP_SERVER));
    assertNotEquals(client1.getConnection(), client2.getConnection());

    assertEquals(pluginList1.size(), pluginList2.size());
    for (int i = 0; i < pluginList1.size(); i++) {
      var plugin1 = pluginList1.get(i);
      var plugin2 = pluginList2.get(i);

      assertNotEquals(plugin1, plugin2);
    }
  }
}
