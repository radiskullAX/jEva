package eu.animegame.jeva.core;

import java.util.Properties;
import eu.animegame.jeva.core.exceptions.MissingParameterException;

public class IrcConfig extends Properties {

  public static final String PROP_NICK = "jeva.irc.nick";

  public static final String PROP_PASSWORD = "jeva.irc.password";

  public static final String PROP_REAL_NAME = "jeva.irc.realName";

  public static final String PROP_SERVER = "jeva.irc.server";

  public static final String PROP_PORT = "jeva.irc.port";

  public static final String PROP_MODE = "jeva.irc.mode";

  private static final long serialVersionUID = 102970934064351453L;

  public IrcConfig() {
    super();
  }

  public IrcConfig(int capacity) {
    super(capacity);
  }

  public IrcConfig(Properties defaults) {
    super(defaults);
  }

  public boolean isPresent(String parameter) {
    var value = getProperty(parameter, "");
    return !value.isBlank();
  }

  public boolean isPresent(String... parameters) {
    for (String param : parameters) {
      var value = getProperty(param, "");
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
