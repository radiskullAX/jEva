package eu.animegame.jeva.core.lifecycle;

import eu.animegame.jeva.core.exceptions.LifeCycleException;

/**
 * 
 *
 * @author radiskull
 */
public interface LifeCycleObject {

  public void initialize() throws LifeCycleException;

  public void connect() throws LifeCycleException;

  public void read() throws LifeCycleException;

  public void disconnect() throws LifeCycleException;

  public void shutdown() throws LifeCycleException;
}
