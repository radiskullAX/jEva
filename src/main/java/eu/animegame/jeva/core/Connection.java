package eu.animegame.jeva.core;

import java.util.Properties;
import eu.animegame.jeva.core.exceptions.ConnectException;

/**
 *
 * @author radiskull
 */
public interface Connection {

  public void setConfig(Properties config);

  public boolean connect() throws ConnectException, Exception;

  public boolean disconnect() throws Exception;

  public String read() throws ConnectException, Exception;

  public boolean write(String msg) throws Exception;

}
