package eu.animegame.jeva.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author radiskull
 */
public class LifeCycle {

  private static final Logger LOG = LoggerFactory.getLogger(LifeCycle.class);

  private LifeCycleState startState;

  private LifeCycleState state;

  public LifeCycle(LifeCycleState startState) {
    this.startState = startState;
  }

  public void start() {
    setState(startState);
    do {
      state.run(this);
    } while (state != null);
  }

  public LifeCycleState getState() {
    return state;
  }

  public void setState(LifeCycleState state) {
    LOG.debug("state changing: [oldState: '{}', newState: '{}']", //
        this.state != null ? this.state.getClass().getSimpleName() : "", //
        state != null ? state.getClass().getSimpleName() : "");
    this.state = state;
  }
}
