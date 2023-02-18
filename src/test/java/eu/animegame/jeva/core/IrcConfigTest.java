package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.exceptions.MissingParameterException;

class IrcConfigTest {

  private static final String PARAM_1 = "param1";

  private static final String PARAM_2 = "param2";

  private static final String PARAM_VALUE = "value";

  private IrcConfig config;

  public IrcConfigTest() {
    config = new IrcConfig();
  }

  @Test
  void getProperties() {
    assertNotNull(config.getProperties());
  }

  @Test
  void isPresentReturnsTrue() {
    getConfigProps().put(PARAM_1, PARAM_VALUE);

    assertTrue(config.isPresent(PARAM_1));
  }

  @Test
  void isPresentForMultipleReturnsTrue() {
    getConfigProps().put(PARAM_1, PARAM_VALUE);
    getConfigProps().put(PARAM_2, PARAM_VALUE);

    assertTrue(config.isPresent(PARAM_1, PARAM_2));
  }

  @Test
  void isPresentReturnsFalse() {
    getConfigProps().put(PARAM_2, "");

    assertFalse(config.isPresent(PARAM_1));
    assertFalse(config.isPresent(PARAM_2));
  }

  @Test
  void isPresentForMultipleReturnsFalse() {
    config.getProperties().put(PARAM_1, PARAM_VALUE);
    config.getProperties().put(PARAM_2, "");

    assertFalse(config.isPresent(PARAM_1, PARAM_2));
  }

  @Test
  void verifyParameter() {
    getConfigProps().put(PARAM_1, PARAM_VALUE);

    assertDoesNotThrow(() -> config.verifyParameter(PARAM_1));
  }

  @Test
  void verifyParameterThrowsException() {
    assertThrows(MissingParameterException.class, () -> config.verifyParameter(PARAM_1));
  }

  @Test
  void verifyParameterWithBlankValueThrowsException() {
    getConfigProps().put(PARAM_1, "");

    assertThrows(MissingParameterException.class, () -> config.verifyParameter(PARAM_1));
  }

  @Test
  void verifyParameters() {
    getConfigProps().put(PARAM_1, PARAM_VALUE);
    getConfigProps().put(PARAM_2, PARAM_VALUE);

    assertDoesNotThrow(() -> config.verifyParameters(PARAM_1, PARAM_2));
  }

  @Test
  void verifyParametersThrowsException() {
    getConfigProps().put(PARAM_1, PARAM_VALUE);

    assertThrows(MissingParameterException.class, () -> config.verifyParameters(PARAM_1, PARAM_2));
  }

  @Test
  void verifyParametersWithBlankValueThrowsException() {
    getConfigProps().put(PARAM_1, PARAM_VALUE);
    getConfigProps().put(PARAM_2, "");

    assertThrows(MissingParameterException.class, () -> config.verifyParameters(PARAM_1, PARAM_2));
  }

  private Properties getConfigProps() {
    return config.getProperties();
  }
}
