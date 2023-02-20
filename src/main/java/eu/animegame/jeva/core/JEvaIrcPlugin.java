package eu.animegame.jeva.core;

/**
 * @author radiskull
 */
public interface JEvaIrcPlugin {

  public default void initialize(JEvaIrcClient jEvaClient) {};

  public default void connect(JEvaIrcClient jEvaClient) {};

  public default void disconnect(JEvaIrcClient jEvaClient) {};

  public default void shutdown(JEvaIrcClient jEvaClient) {};
}
