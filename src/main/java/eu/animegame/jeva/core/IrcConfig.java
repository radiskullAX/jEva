package eu.animegame.jeva.core;

import java.util.Properties;
import eu.animegame.jeva.core.exceptions.MissingParameterException;

public class IrcConfig {

  public final static String PROP_NICK = "jeva.irc.nick";

  public final static String PROP_PASSWORD = "jeva.irc.password";

  public final static String PROP_REAL_NAME = "jeva.irc.realName";

  public final static String PROP_SERVER = "jeva.irc.server";

  public final static String PROP_PORT = "jeva.irc.port";

  public final static String PROP_MODE = "jeva.irc.mode";

  private final Properties properties;

  public IrcConfig() {
    this(new String[0]);
  }

  public IrcConfig(String[] args) {
    // TODO: parse command args
    this.properties = new Properties();
  }

  public Properties getProperties() {
    return properties;
  }

  public boolean isPresent(String parameter) {
    var value = properties.getProperty(parameter, "");
    return !value.isBlank();
  }

  public boolean isPresent(String... parameters) {
    for (String param : parameters) {
      var value = properties.getProperty(param, "");
      if (value.isBlank()) {
        return false;
      }
    }
    return true;
  }

  public void verifyParameter(String param) throws MissingParameterException {
    if (!isPresent(param)) {
      throw new MissingParameterException("Parameter '" + param + "' is not set or empty in config.");
    }
  }

  public void verifyParameters(String... params) throws MissingParameterException {
    for (String parameter : params) {
      if (!isPresent(parameter)) {
        throw new MissingParameterException("Parameter '" + parameter + "' is not set or empty in config.");
      }
    }
  }
}
