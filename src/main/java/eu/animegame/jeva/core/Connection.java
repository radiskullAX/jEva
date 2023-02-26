package eu.animegame.jeva.core;

import java.util.Properties;
import eu.animegame.jeva.core.exceptions.ConnectionException;

/**
 *
 * @author radiskull
 */
public interface Connection {

  public void setConfig(Properties config);

  public boolean connect() throws ConnectionException;

  public boolean disconnect() throws Exception;

  public String read() throws ConnectionException;

  public boolean write(String msg) throws Exception;

}
