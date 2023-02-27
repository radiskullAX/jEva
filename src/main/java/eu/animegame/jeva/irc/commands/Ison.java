package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Ison(String nick) implements IrcCommand {

  public static final String COMMAND = "ISON";

  public Ison(String[] nicks) {
    this(Arrays.stream(nicks).collect(joining(" ")));
  }

  public Ison(java.util.List<String> nicks) {
    this(nicks.stream().collect(joining(" ")));
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick);
    return command.toString();
  }
}
