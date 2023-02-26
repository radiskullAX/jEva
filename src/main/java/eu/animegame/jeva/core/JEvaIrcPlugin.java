package eu.animegame.jeva.core;

/**
 * @author radiskull
 */
public interface JEvaIrcPlugin {

  public default void initialize(JEvaIrcEngine jEvaIrcEngine) {};

  public default void connect(JEvaIrcEngine jEvaIrcEngine) {};

  public default void disconnect(JEvaIrcEngine jEvaIrcEngine) {};

  public default void shutdown(JEvaIrcEngine jEvaIrcEngine) {};
}
