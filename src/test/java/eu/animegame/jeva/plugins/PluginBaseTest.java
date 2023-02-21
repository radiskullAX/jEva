package eu.animegame.jeva.plugins;

import eu.animegame.jeva.core.JEvaIrcClient;
import eu.animegame.jeva.core.JEvaIrcPlugin;

// TODO: Unify tests so they all look similar in structure
/**
 *
 * @author radiskull
 */
public class PluginBaseTest<P extends JEvaIrcPlugin> {

  protected JEvaIrcClient jEvaClient;

  protected P plugin;
}
