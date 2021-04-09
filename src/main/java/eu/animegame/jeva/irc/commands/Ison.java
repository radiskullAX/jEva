package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Ison implements IrcCommand {

  private final String nick;

  public Ison(String nick) {
    this.nick = nick;
  }

  public Ison(String[] nicks) {
    this(Arrays.stream(nicks).collect(joining(" ")));
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("ISON ").append(nick);
    return command.toString();
  }

}
