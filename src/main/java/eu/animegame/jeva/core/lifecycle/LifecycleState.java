package eu.animegame.jeva.core.lifecycle;

import eu.animegame.jeva.core.JEvaIrcClient;

/**
 *
 * @author radiskull
 */
public interface LifecycleState {

  // TODO: rename context variable .. its not an interface but a concrete class passing through
  public void run(JEvaIrcClient context);
}
