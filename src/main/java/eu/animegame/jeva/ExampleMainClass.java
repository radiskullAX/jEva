package eu.animegame.jeva;

import eu.animegame.jeva.plugins.AutoJoinPlugin;
import eu.animegame.jeva.plugins.BotControllPlugin;
import eu.animegame.jeva.plugins.ReconnectPlugin;

/**
 *
 * @author radiskull
 */
public class ExampleMainClass {

  /**
   * Starts an IrcBot with basic functions support ;)
   * 
   * @param args
   */
  public static void main(String[] args) {
    // TODO: geworfene Exceptions sollen in eine externe Datei über die Logger
    // geschrieben werden
    // TODO: Alle Header der dateien durchgehen und evtl ändern
    // /ADD radiskull ~chatzilla@radiskull.users.quakenet.org
    // /ADD botname ~botname@195.37.166.242
    /*
     * Pattern pattern = Pattern.compile("^:(?:[^\\s]+\\s){2}(([\\w\\p{Punct}]+)\\s*(.*))"); String test =
     * "NOTICE AUTH :*** Looking up your hostname";
     * System.out.println(Arrays.toString(RegexHelper.parseStringGroups(pattern, test)));
     */
    System.getProperties().setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    QuakenetHandler client = IrcClientBuilder.create().withAddress(args[0]).withNick("IrcTestBot")
        .withRealName("A true bot")
        .withPort(6667).withPlugin(new ReconnectPlugin()).withPlugin(new AutoJoinPlugin(args[1]))
        .withPlugin(new BotControllPlugin()).build();

    client.start();
    /*
     * GUICallback gui = new GUICallback(); gui.addPanel(new CallbackOverview()); InputPanel input = new InputPanel();
     * input.addInputListener(new testInput()); gui.addPanel(input); gui.registerCallbackEvents(ib);
     * 
     * AutoJoinCallback ajcb = new AutoJoinCallback("#radi-o"); ajcb.registerCallbackEvents(ib);
     * 
     * BotControllCallback bccb = new BotControllCallback(IrcUserType.AUTHED); RegistrationCallback rcb = new
     * RegistrationCallback();
     * 
     * //gui.registerCallbackEvents(ib); bccb.registerCallbackEvents(ib); rcb.registerCallbackEvents(ib);
     * 
     * ib.addIrcEventCallback(IrcMessageType.UNDEFINED, input); ib.addIrcEventCallback(IrcMessageType.JOIN, input);
     * ib.addIrcEventCallback(IrcMessageType.PRIVMSG, input); ib.addIrcEventCallback(IrcMessageType.INVITE, input);
     * ib.addIrcEventCallback(IrcMessageType.KICK, input); ib.addIrcEventCallback(IrcMessageType.NICK, input);
     * ib.addIrcEventCallback(IrcMessageType.PART, input);
     * 
     * gui.show();
     */
  }
}
