package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
  void isPresentReturnsTrue() {
    config.put(PARAM_1, PARAM_VALUE);

    assertTrue(config.isPresent(PARAM_1));
  }

  @Test
  void isPresentForMultipleReturnsTrue() {
    config.put(PARAM_1, PARAM_VALUE);
    config.put(PARAM_2, PARAM_VALUE);

    assertTrue(config.isPresent(PARAM_1, PARAM_2));
  }

  @Test
  void isPresentReturnsFalse() {
    config.put(PARAM_2, "");

    assertFalse(config.isPresent(PARAM_1));
    assertFalse(config.isPresent(PARAM_2));
  }

  @Test
  void isPresentForMultipleReturnsFalse() {
    config.put(PARAM_1, PARAM_VALUE);
    config.put(PARAM_2, "");

    assertFalse(config.isPresent(PARAM_1, PARAM_2));
  }

  @Test
  void verifyParameter() {
    config.put(PARAM_1, PARAM_VALUE);

    assertDoesNotThrow(() -> config.verifyParameter(PARAM_1));
  }

  @Test
  void verifyParameterThrowsException() {
    assertThrows(MissingParameterException.class, () -> config.verifyParameter(PARAM_1));
  }

  @Test
  void verifyParameterWithBlankValueThrowsException() {
    config.put(PARAM_1, "");

    assertThrows(MissingParameterException.class, () -> config.verifyParameter(PARAM_1));
  }

  @Test
  void verifyParameters() {
    config.put(PARAM_1, PARAM_VALUE);
    config.put(PARAM_2, PARAM_VALUE);

    assertDoesNotThrow(() -> config.verifyParameters(PARAM_1, PARAM_2));
  }

  @Test
  void verifyParametersThrowsException() {
    config.put(PARAM_1, PARAM_VALUE);

    assertThrows(MissingParameterException.class, () -> config.verifyParameters(PARAM_1, PARAM_2));
  }

  @Test
  void verifyParametersWithBlankValueThrowsException() {
    config.put(PARAM_1, PARAM_VALUE);
    config.put(PARAM_2, "");

    assertThrows(MissingParameterException.class, () -> config.verifyParameters(PARAM_1, PARAM_2));
  }
}
