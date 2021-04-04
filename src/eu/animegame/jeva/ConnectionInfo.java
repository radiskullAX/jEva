package eu.animegame.jeva;

/**
 * @author radiskull
 */
public class ConnectionInfo {

	protected String passwort;
	protected String nickName;
	protected String realName;
	protected String ip;
	protected int port;
	protected int mode;

	public ConnectionInfo(String nick, String ip, int port) {
		this(nick, 0, nick, ip, port, "");
	}

	public ConnectionInfo(String nick, String ip, int port, String passwort) {
		this(nick, 0, nick, ip, port, passwort);
	}

	public ConnectionInfo(String nick, int mode, String realname, String ip, int port, String passwort) {
		this.nickName = nick;
		this.realName = realname;
		this.mode = mode;
		this.ip = ip;
		this.port = port;
		this.passwort = passwort;
	}

	public String getIrcName() {
		return nickName;
	}

	public String getRealName() {
		return realName;
	}

	public String getIpAdresse() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getPasswort() {
		return passwort;
	}

	public int getMode() {
		return mode;
	}

	/**
	 * @param passwort
	 *            the passwort to set
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setIrcName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @param realname
	 *            the realname to set
	 */
	public void setRealname(String realname) {
		this.realName = realname;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIpAdresse(String ip) {
		this.ip = ip;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}
}
