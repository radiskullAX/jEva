package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Arrays;
import java.util.Properties;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.plugins.AutoJoinPlugin;
import eu.animegame.jeva.plugins.ReJoinPlugin;

/**
 * 
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class JEvaIrcEngineBuilderTest {

  private static final String SERVER = "irc.animegame.eu";

  private static final String SERVER_PASSWORD = "secret";

  private static final String PORT = "6668";

  private static final String NICK = "IrcUser";

  private static final String MODE = "12";

  private static final String REAL_NAME = "jEva";

  private static final String CHANNEL = "#test";

  private JEvaIrcEngineBuilder builder;

  private JEvaIrcEngineBuilderTest() {
    builder = JEvaIrcEngineBuilder.create();
  }

  @Test
  void create() {
    var engine = builder.build();

    assertNotNull(engine);
    assertNotNull(engine.getConfig());
    assertEquals(0, engine.getConfig().size());
    assertNotNull(engine.getConnection());
    assertEquals(4, engine.getPlugins().size());
  }

  @Test
  void properties() {
    var props = new Properties();
    props.put(IrcConfig.PROP_SERVER, SERVER);
    var engine = builder.properties(props).build();

    var actual = engine.getConfig().getProperty(IrcConfig.PROP_SERVER);
    assertEquals(SERVER, actual);
  }

  @Test
  void server() {
    var engine = builder.server(SERVER).build();

    var actual = engine.getConfig().getProperty(IrcConfig.PROP_SERVER);
    assertEquals(SERVER, actual);
  }

  @Test
  void serverPassword() {
    var engine = builder.serverPassword(SERVER_PASSWORD).build();

    var actual = engine.getConfig().getProperty(IrcConfig.PROP_SERVER_PASSWORD);
    assertEquals(SERVER_PASSWORD, actual);
  }

  @Test
  void port() {
    var engine = builder.port(PORT).build();

    var actual = engine.getConfig().getProperty(IrcConfig.PROP_PORT);
    assertEquals(PORT, actual);
  }

  @Test
  void nick() {
    var engine = builder.nick(NICK).build();

    var actual = engine.getConfig().getProperty(IrcConfig.PROP_NICK);
    assertEquals(NICK, actual);
  }

  @Test
  void mode() {
    var engine = builder.mode(MODE).build();

    var actual = engine.getConfig().getProperty(IrcConfig.PROP_MODE);
    assertEquals(MODE, actual);
  }

  @Test
  void realName() {
    var engine = builder.realName(REAL_NAME).build();

    var actual = engine.getConfig().getProperty(IrcConfig.PROP_REAL_NAME);
    assertEquals(REAL_NAME, actual);
  }

  @Test
  void channel() {
    var engine = builder.channel(CHANNEL).build();

    var opt = engine.getPlugin(AutoJoinPlugin.class);
    assertTrue(opt.isPresent());

    var channels = opt.get().getChannels();
    assertIterableEquals(Arrays.asList(CHANNEL), channels);
  }

  @Test
  void autoRejoin() {
    var engine = builder.autoRejoin(true).build();

    var opt = engine.getPlugin(ReJoinPlugin.class);
    assertTrue(opt.isPresent());
  }

  @Test
  void plugin() {
    var plugin = mock(JEvaIrcPlugin.class);
    var engine = builder.plugin(() -> plugin).build();

    var opt = engine.getPlugin(plugin.getClass());
    assertTrue(opt.isPresent());
    assertEquals(5, engine.getPlugins().size());
  }

  @Test
  void buildCreatesDifferentObjects() {
    builder.channel(CHANNEL).autoRejoin(true).plugin(() -> mock(JEvaIrcPlugin.class));
    var engine1 = builder.build();
    var engine2 = builder.build();
    var pluginList1 = engine1.getPlugins();
    var pluginList2 = engine2.getPlugins();

    engine1.getConfig().put(IrcConfig.PROP_SERVER, SERVER);

    assertNotEquals(engine1, engine2);
    assertNotEquals(engine1.getConfig(), engine2.getConfig());
    assertEquals(null, engine2.getConfig().getProperty(IrcConfig.PROP_SERVER));
    assertNotEquals(engine1.getConnection(), engine2.getConnection());

    assertEquals(pluginList1.size(), pluginList2.size());
    for (int i = 0; i < pluginList1.size(); i++) {
      var plugin1 = pluginList1.get(i);
      var plugin2 = pluginList2.get(i);

      assertNotEquals(plugin1, plugin2);
    }
  }
}
