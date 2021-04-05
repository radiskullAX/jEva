package eu.animegame.jeva.interfaces;

/**
 *
 * @author radiskull
 */
public interface Connection {

	public boolean connect() throws Exception;

	public boolean disconnect() throws Exception;

	public boolean isConnected();

	public boolean isClosed();

	public Object read() throws Exception;

	public boolean write(String msg);

	public void setUrl(String url);

	public void setPort(int port);
}
