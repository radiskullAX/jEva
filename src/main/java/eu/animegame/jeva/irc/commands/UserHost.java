package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class UserHost implements IrcCommand {

  public static final String COMMAND = "USERHOST";

  private final String nick;

  public UserHost(String nick) {
    this.nick = nick;
  }

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
