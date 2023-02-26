package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.Mockito.spy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.Tags;

/**
 * 
 *
 * @author radiskull
 */
@Tag(Tags.INTEGRATION)
@Tag(Tags.TIMED)
public class SocketConnectionIntegrationTest {

  private static final String SERVER = "localhost";

  private static final String PORT = "6667";

  private static final int PORT_NUMERIC = 6667;

  private static final String MESSAGE = "NOTICE test message";

  private SocketConnection spyConnection;

  public SocketConnectionIntegrationTest() {
    spyConnection = spy(new SocketConnection());
  }

  @Test
  public void testConnectSuccessful() throws Exception {
    Properties config = new Properties();
    config.put(IrcConfig.PROP_SERVER, SERVER);
    config.put(IrcConfig.PROP_PORT, PORT);
    spyConnection.setConfig(config);

    assertTimeoutPreemptively(Duration.ofSeconds(5), () ->
      {
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
      });
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
