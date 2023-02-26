package eu.animegame.jeva.plugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcBaseEvent;
import eu.animegame.jeva.core.IrcEventAcceptor;
import eu.animegame.jeva.core.JEvaIrcEngine;
import eu.animegame.jeva.core.JEvaIrcPlugin;
import eu.animegame.jeva.irc.CommandCode;
import eu.animegame.jeva.irc.commands.Join;

/**
 *
 * @author radiskull
 */
public class AutoJoinPlugin implements JEvaIrcPlugin {

  public static final String PROP_CHANNELS = "jeva.irc.plugin.autojoin.channels";

  private static final Logger LOG = LoggerFactory.getLogger(AutoJoinPlugin.class);

  private List<String> channels;

  public AutoJoinPlugin() {
    channels = new ArrayList<>();
  }

  public void addChannel(String channel) {
    if (channel != null && !channel.isBlank()) {
      var stripChan = channel.strip();
      if (!channels.contains(stripChan)) {
        LOG.trace("add channel: [channel: {}]", stripChan);
        channels.add(stripChan);
      }
    }
  }

  public void addChannel(String channel, String password) {
    if ((channel != null && password != null) && (!channel.isBlank() && !password.isBlank())) {
      var combine = channel.strip() + " " + password.strip();
      if (!channels.contains(combine)) {
        LOG.trace("add channel with password: [channel: {}]", combine);
        channels.add(combine);
      }
    }
  }

  public void removeChannel(String channel) {
    LOG.trace("remove channel: [channel: {}]", channel);
    channels.remove(channel);
  }

  public List<String> getChannels() {
    return channels.stream()
        .map(this::removePassword) //
        .collect(Collectors.toList());
  }

  private String removePassword(String channel) {
    var index = channel.indexOf(" ");
    if (index > 0) {
      return channel.substring(0, index);
    }
    return channel;
  }

  @Override
  public void initialize(JEvaIrcEngine jEvaIrcEngine) {
    LOG.info("read config: [property: {}]", PROP_CHANNELS);
    var channels = jEvaIrcEngine.getConfig().getProperty(PROP_CHANNELS, "");
    Arrays.stream(channels.split("\\s*,\\s*"))
        .map(String::strip) //
        .map(c -> c.replaceAll("\\s+", " ")) //
        .forEach(this::addChannel);
  }

  @IrcEventAcceptor(command = CommandCode.RPL_WELCOME)
  public void sendJoin(IrcBaseEvent event, JEvaIrcEngine jEvaIrcEngine) {
    var openChannels = new ArrayList<String>();
    var securedChannels = new ArrayList<String>();
    var passwords = new ArrayList<String>();

    LOG.info("join channels: [numberOfChannels: {}]", channels.size());
    for (String channel : channels) {
      if (channel.contains(" ")) {
        var content = channel.split(" ");
        securedChannels.add(content[0]);
        passwords.add(content[1]);
      } else {
        openChannels.add(channel);
      }
    }
    if (!openChannels.isEmpty()) {
      LOG.debug("send JOIN command: [channels: {}]", openChannels);
      jEvaIrcEngine.sendCommand(new Join(openChannels));
    }
    if (!securedChannels.isEmpty()) {
      LOG.debug("send JOIN command: [securedChannels: {}]", securedChannels);
      jEvaIrcEngine.sendCommand(new Join(securedChannels, passwords));
    }
  }
}
