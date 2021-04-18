package eu.animegame.jeva.core.lifecycle;

import eu.animegame.jeva.core.IrcHandler;

/**
 *
 * @author radiskull
 */
public interface LifecycleState {

  public void run(IrcHandler context);
}
