package eu.animegame.jeva.core;

import java.util.Optional;

/**
 *
 * @author radiskull
 */
public class IrcBaseEvent {

  private final Optional<String> prefix;

  private final String command;

  private final String parameters;

  private final String source;

  public IrcBaseEvent(IrcBaseEvent event) {
    this(event.getPrefix(), event.getCommand(), event.getParameters(), event.getSource());
  }

  public IrcBaseEvent(String command, String parameters, String source) {
    this(Optional.empty(), command, parameters, source);
  }

  public IrcBaseEvent(String prefix, String command, String parameters, String source) {
    this(Optional.ofNullable(prefix), command, parameters, source);
  }

  private IrcBaseEvent(Optional<String> prefix, String command, String parameters, String source) {
    this.prefix = prefix;
    this.command = command;
    this.parameters = parameters;
    this.source = source;
  }

  public Optional<String> getPrefix() {
    return prefix;
  }

  public String getCommand() {
    return command;
  }

  public String getParameters() {
    return parameters;
  }

  public String getSource() {
    return source;
  }
}
