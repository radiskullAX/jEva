package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.rmi.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import eu.animegame.jeva.core.exceptions.ConnectException;

/**
 *
 * @author radiskull
 */
class SocketConnectionTest {

  private static final String SERVER = "localhost";

  private static final String PORT = "6667";

  private static final Charset CHARSET = StandardCharsets.UTF_8;

  private static final int PORT_NUMERIC = 6667;

  private static final String MESSAGE = "test message";

  private SocketConnection spyConnection = spy(new SocketConnection());

  private Socket socket = mock(Socket.class);

  private BufferedWriter writer = mock(BufferedWriter.class);

  private BufferedReader reader = mock(BufferedReader.class);

  @BeforeEach
  public void init() {
    Properties config = new Properties();
    config.put(IrcHandler.PROP_SERVER, SERVER);
    config.put(IrcHandler.PROP_PORT, PORT);

    spyConnection.setConfig(config);
  }

  @Test
  public void testConnectSuccessful() throws Exception {
    try {
      CountDownLatch countDownLatch = new CountDownLatch(1);

      TestServer server = new TestServer(countDownLatch);
      Thread t = new Thread(server);
      t.start();

      Thread.sleep(10);

      var connected = spyConnection.connect();
      var messageSend = spyConnection.write(MESSAGE);

      countDownLatch.await();

      assertEquals(true, connected);
      assertEquals(true, messageSend);
    } finally {
      spyConnection.disconnect();
    }
  }

  @ParameterizedTest()
  @MethodSource("socketExceptionClassProvider")
  public void testConnectSocketCreationFails(Class<Exception> trigger, Class<Exception> expected) throws Exception {
    doThrow(trigger).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);

    assertThrows(expected, () -> spyConnection.connect());
    verify(spyConnection).disconnect();
  }

  @ParameterizedTest()
  @MethodSource("ioExceptionClassProvider")
  public void testConnectWriterCreationFails(Class<Exception> trigger, Class<Exception> expected) throws Exception {
    doReturn(socket).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);
    doThrow(trigger).when(spyConnection).createWriter(socket, CHARSET);

    assertThrows(expected, () -> spyConnection.connect());
    verify(spyConnection).disconnect();
  }

  @ParameterizedTest()
  @MethodSource("ioExceptionClassProvider")
  public void testConnectReaderCreationFails(Class<Exception> trigger, Class<Exception> expected) throws Exception {
    doReturn(socket).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);
    doReturn(writer).when(spyConnection).createWriter(socket, CHARSET);
    doThrow(trigger).when(spyConnection).createReader(socket, CHARSET);

    assertThrows(expected, () -> spyConnection.connect());
    verify(spyConnection).disconnect();
  }

  @Test
  public void testDisconnectCloseAllResources() throws Exception {
    doReturn(socket).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);
    doReturn(reader).when(spyConnection).createReader(socket, CHARSET);
    doReturn(writer).when(spyConnection).createWriter(socket, CHARSET);

    spyConnection.connect();
    spyConnection.disconnect();

    verify(socket).close();
    verify(reader).close();
    verify(writer).close();
  }

  @Test
  public void testDisconnectCloseOpenedResources() throws Exception {
    doReturn(socket).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);
    doReturn(writer).when(spyConnection).createWriter(socket, CHARSET);
    doThrow(IOException.class).when(spyConnection).createReader(socket, CHARSET);

    try {
      spyConnection.connect();
    } catch (Exception e) {
      verify(socket).close();
      verify(writer).close();
      verify(reader, never()).close();
    }
    verify(spyConnection).disconnect();
  }

  @Test
  public void testDisconnectFails() throws Exception {
    doReturn(socket).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);
    doReturn(reader).when(spyConnection).createReader(socket, CHARSET);
    doReturn(writer).when(spyConnection).createWriter(socket, CHARSET);
    doThrow(IOException.class).when(socket).close();

    spyConnection.connect();
    assertThrows(Exception.class, () -> spyConnection.disconnect());
  }

  @Test
  public void testReadSuccessful() throws Exception {
    doReturn(socket).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);
    doReturn(reader).when(spyConnection).createReader(socket, CHARSET);
    doReturn(writer).when(spyConnection).createWriter(socket, CHARSET);
    doReturn(MESSAGE).when(reader).readLine();

    spyConnection.connect();
    String result = spyConnection.read();

    assertEquals(MESSAGE, result);
  }

  @ParameterizedTest()
  @MethodSource("readExceptionClassProvider")
  public void testReadFails(Class<Exception> trigger, Class<Exception> expected) throws Exception {
    doReturn(socket).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);
    doReturn(reader).when(spyConnection).createReader(socket, CHARSET);
    doReturn(writer).when(spyConnection).createWriter(socket, CHARSET);
    doThrow(trigger).when(reader).readLine();

    spyConnection.connect();
    assertThrows(expected, () -> spyConnection.read());
    verify(spyConnection).disconnect();
  }

  @Test
  public void testWriteSuccessful() throws Exception {
    doReturn(socket).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);
    doReturn(reader).when(spyConnection).createReader(socket, CHARSET);
    doReturn(writer).when(spyConnection).createWriter(socket, CHARSET);

    spyConnection.connect();
    Boolean result = spyConnection.write(MESSAGE);

    assertEquals(true, result);
    verify(writer).write(MESSAGE);
    verify(writer).newLine();
    verify(writer).flush();
  }

  @Test
  public void testWriteFails() throws Exception {
    doReturn(socket).when(spyConnection).createSocket(SERVER, PORT_NUMERIC);
    doReturn(reader).when(spyConnection).createReader(socket, CHARSET);
    doReturn(writer).when(spyConnection).createWriter(socket, CHARSET);
    doThrow(IOException.class).when(writer).write(MESSAGE);;

    spyConnection.connect();
    assertThrows(Exception.class, () -> spyConnection.write(MESSAGE));
  }

  static Stream<Arguments> socketExceptionClassProvider() {
    return Stream.of(arguments(UnknownHostException.class, ConnectException.class),
        arguments(IOException.class, ConnectException.class), arguments(NullPointerException.class, Exception.class));
  }

  static Stream<Arguments> ioExceptionClassProvider() {
    return Stream.of(arguments(UnsupportedEncodingException.class, Exception.class),
        arguments(IOException.class, ConnectException.class), arguments(NullPointerException.class, Exception.class));
  }

  static Stream<Arguments> readExceptionClassProvider() {
    return Stream.of(arguments(IOException.class, ConnectException.class),
        arguments(NullPointerException.class, Exception.class));
  }

  private class TestServer implements Runnable {

    private final CountDownLatch latch;

    public TestServer(CountDownLatch latch) {
      this.latch = latch;
    }

    @Override
    public void run() {
      try (ServerSocket socket = new ServerSocket(PORT_NUMERIC);
          Socket client = socket.accept();
          BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
          BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {

        in.readLine();

      } catch (IOException e) {
        e.printStackTrace();
      }
      latch.countDown();
    }
  }
}
