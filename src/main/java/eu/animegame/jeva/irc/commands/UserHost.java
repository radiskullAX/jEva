package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record UserHost(String nick) implements IrcCommand {

  public static final String COMMAND = "USERHOST";

  public UserHost(String[] nicks) {
    this(Arrays.stream(nicks).collect(joining(" ")));
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick);
    return command.toString();
  }
}
