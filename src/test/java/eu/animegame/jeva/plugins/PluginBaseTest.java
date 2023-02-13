package eu.animegame.jeva.plugins;

import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;

public class PluginBaseTest<P extends IrcHandlerPlugin> {

  protected IrcHandler handler;

  protected P plugin;
}
