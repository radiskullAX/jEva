package eu.animegame.jeva.core;

import java.util.Optional;

public class BasicIrcEvent {

  private final Optional<String> prefix;

  private final String command;

  private final String parameters;

  public BasicIrcEvent(BasicIrcEvent event) {
    this(event.getPrefix(), event.getCommand(), event.getParameters());
  }

  public BasicIrcEvent(String prefix, String command, String parameters) {
    this(Optional.ofNullable(prefix), command, parameters);
  }

  private BasicIrcEvent(Optional<String> prefix, String command, String parameters) {
    this.prefix = prefix;
    this.command = command;
    this.parameters = parameters;
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
    var source = new StringBuilder();
    prefix.ifPresent(pre -> source.append(":").append(pre).append(" "));
    source.append(command).append(" ").append(parameters);
    return source.toString();
  }
}
