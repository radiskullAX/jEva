package eu.animegame.jeva;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.animegame.jeva.interfaces.Connection;

/**
 * @author radiskull
 */
public class SocketConnection implements Connection {

	protected static Logger LOG = LoggerFactory.getLogger(SocketConnection.class);
	protected static final String CHARSET = "UTF-8";
	protected Socket socket;
	protected BufferedWriter writer;
	protected BufferedReader reader;
	protected String ipAdresse;
	protected int port;

	public SocketConnection() {
		this("", 0);
	}

	public SocketConnection(String ipAdresse, int port) {
		this.ipAdresse = ipAdresse;
		this.port = port;
	}

	@Override
	public String read() throws IOException, SocketException {
		String input = "";
		try {
			input = reader.readLine();
		} catch (SocketException se) {
			LOG.warn(se.getMessage());
			disconnect();
			throw se;
		} catch (IOException ioe) {
			LOG.warn(ioe.getMessage());
			disconnect();
			throw ioe;
		}
		LOG.trace("INPUT :< {}", input);
		if (input == null) {
			LOG.debug("Input is null, something is not right so closing the connection.");
			disconnect();
		}
		return input;
	}

	@Override
	public boolean connect() {
		try {
			LOG.debug("Create Socket for adresse \"{}:{}\"", ipAdresse, port);
			socket = new Socket(ipAdresse, port);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET));
			LOG.debug("LocalPort: {}", socket.getLocalPort());
			return true;
		} catch (UnknownHostException uhe) {
			LOG.warn(uhe.getMessage());
		} catch (IOException ioe) {
			LOG.warn(ioe.getMessage());
		}
		return false;
	}

	@Override
	public boolean disconnect() {
		try {
			LOG.debug("Closing Socket.");
			reader.close();
			writer.close();
			socket.close();
			return true;
		} catch (IOException ioe) {
			LOG.warn(ioe.getMessage());
		}
		return false;
	}

	@Override
	public boolean isConnected() {
		return socket.isConnected();
	}

	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}

	@Override
	public boolean write(String output) {
		try {
			if (!isClosed()) {
				LOG.trace("OUTPUT :> {}", output);
				writer.write(output);
				writer.newLine();
				writer.flush();
				return true;
			}
		} catch (IOException ioe) {
			LOG.warn(ioe.getMessage());
		}
		return false;
	}

	@Override
	public void setUrl(String url) {
		if (url != null && !url.isEmpty()) {
			ipAdresse = url;
		}
	}

	@Override
	public void setPort(int port) {
		if (port > 0) {
			this.port = port;
		}
	}
}
