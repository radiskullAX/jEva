package eu.animegame.jeva.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.exceptions.ConnectionException;

/**
 *
 * @author radiskull
 */
public class SocketConnection implements Connection {

  private static Logger LOG = LoggerFactory.getLogger(SocketConnection.class);

  private final Charset charset;

  private Properties config;

  private Socket socket;

  private BufferedWriter writer;

  private BufferedReader reader;

  public SocketConnection() {
    this(StandardCharsets.UTF_8);
  }

  public SocketConnection(Charset charset) {
    this.charset = charset;
  }

  @Override
  public void setConfig(Properties config) {
    this.config = config;
  }

  @Override
  public boolean connect() throws ConnectionException {
    try {
      var address = config.getProperty(IrcConfig.PROP_SERVER);
      var port = Integer.parseInt(config.getProperty(IrcConfig.PROP_PORT));
      LOG.debug("Create socket for address \"{}:{}\"", address, port);

      socket = createSocket(address, port);
      writer = createWriter(socket, charset);
      reader = createReader(socket, charset);
      LOG.debug("Local port: {}", socket.getLocalPort());

      return true;
    } catch (Exception e) {
      disconnect();
      throw new ConnectionException(e);
    }
  }

  Socket createSocket(String address, int port) throws UnknownHostException, IOException {
    return new Socket(address, port);
  }

  BufferedWriter createWriter(Socket socket, Charset charset) throws UnsupportedEncodingException, IOException {
    return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), charset));
  }

  BufferedReader createReader(Socket socket, Charset charset) throws UnsupportedEncodingException, IOException {
    return new BufferedReader(new InputStreamReader(socket.getInputStream(), charset));
  }

  @Override
  public boolean disconnect() throws ConnectionException {
    try {
      LOG.debug("Closing Socket.");
      if (reader != null) {
        reader.close();
      }
      if (writer != null) {
        writer.close();
      }
      if (socket != null) {
        socket.close();
      }
      return true;
    } catch (Exception e) {
      throw new ConnectionException(e);
    }
  }

  @Override
  public String read() throws ConnectionException {
    var input = "";
    try {
      input = reader.readLine();
      LOG.trace("<INPUT> {}", input);
      return input;
    } catch (Exception e) {
      disconnect();
      throw new ConnectionException(e);
    }
  }

  @Override
  public boolean write(String msg) throws Exception {
    try {
      writer.write(msg);
      LOG.trace("<OUTPUT> {}", msg);
      writer.newLine();
      writer.flush();
      return true;
    } catch (Exception e) {
      throw e;
    }
  }
}
