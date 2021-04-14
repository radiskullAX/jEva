package eu.animegame.jeva.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * This test exists only to satisfy code coverage.
 *
 * @author radiskull
 */
class ExceptionTest {

  private static final String MESSAGE = "This is a test!";

  private static final Exception EXCEPTION = new Exception();

  @ParameterizedTest
  @MethodSource("exceptionProvider")
  public void testExceptionWithEmptyConstructor(Class<JEvaException> exception)
      throws NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

    Constructor<JEvaException> constructor = exception.getConstructor();
    Exception e = constructor.newInstance();

    assertNull(e.getMessage());
    assertNull(e.getCause());
  }

  @ParameterizedTest
  @MethodSource("exceptionProvider")
  public void testExceptionWithMessage(Class<JEvaException> exception) throws NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

    Constructor<JEvaException> constructor = exception.getConstructor(String.class);
    Exception e = constructor.newInstance(MESSAGE);

    assertEquals(MESSAGE, e.getMessage());
  }

  @ParameterizedTest
  @MethodSource("exceptionProvider")
  public void testExceptionWithException(Class<JEvaException> exception)
      throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {

    Constructor<JEvaException> constructor = exception.getConstructor(Throwable.class);
    Exception e = constructor.newInstance(EXCEPTION);

    assertEquals(EXCEPTION, e.getCause());
  }

  @ParameterizedTest
  @MethodSource("exceptionProvider")
  public void testExceptionWithMessageAndException(Class<JEvaException> exception)
      throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {

    Constructor<JEvaException> constructor = exception.getConstructor(String.class, Throwable.class);
    Exception e = constructor.newInstance(MESSAGE, EXCEPTION);

    assertEquals(MESSAGE, e.getMessage());
    assertEquals(EXCEPTION, e.getCause());
  }

  @ParameterizedTest
  @MethodSource("exceptionProvider")
  public void testExceptionWithAll(Class<JEvaException> exception) throws NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

    Constructor<JEvaException> constructor =
        exception.getConstructor(String.class, Throwable.class, boolean.class, boolean.class);
    Exception e = constructor.newInstance(MESSAGE, EXCEPTION, false, false);

    assertEquals(MESSAGE, e.getMessage());
    assertEquals(EXCEPTION, e.getCause());
  }

  static Stream<Class<? extends JEvaException>> exceptionProvider() {
    return Stream.of(JEvaException.class, ConnectException.class, LifecycleException.class,
        UnknownFormatException.class);
  }
}
