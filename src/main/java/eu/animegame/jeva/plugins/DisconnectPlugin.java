package eu.animegame.jeva.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.JEvaIrcEngine;
import eu.animegame.jeva.core.JEvaIrcPlugin;
import eu.animegame.jeva.irc.commands.Quit;

/**
 * This plugin tells the IRC server to close the connection. By IRC-Protocol a client should send a {@link Quit} command
 * before closing its connection.<br>
 * It is not necessary to have this plugin registered to a {@link JEvaIrcEngine} as closing the connection results in
 * the same behavior. The irc server will just say that the connection was closed instead of showing a client generated
 * quit message.<br>
 * 
 * @author radiskull
 */
public class DisconnectPlugin implements JEvaIrcPlugin {

  public static final String PROP_QUIT_MESSAGE = "jeva.irc.plugin.disconnect.quitmessage";

  private static final String DEFAULT_QUIT_MESSAGE = "Powered by jEva!";

  private static final Logger LOG = LoggerFactory.getLogger(DisconnectPlugin.class);

  private String quitMessage;

  @Override
  public void initialize(JEvaIrcEngine jEvaIrcEngine) {
    LOG.info("read config: [property: {}]", PROP_QUIT_MESSAGE);
    quitMessage = jEvaIrcEngine.getConfig().getProperty(PROP_QUIT_MESSAGE, DEFAULT_QUIT_MESSAGE);
  }

  @Override
  public void disconnect(JEvaIrcEngine jEvaIrcEngine) {
    jEvaIrcEngine.sendCommand(new Quit(quitMessage));
  }

  public String getQuitMessage() {
    return quitMessage;
  }
}
