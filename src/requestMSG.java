import java.io.Serializable;

public class requestMSG implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int identifier;
	String hostname;
	String DestNodeName;
	int clientPort;
	int serverPort;
	
	public String getDestNodeName() {
		return DestNodeName;
	}

	public void setDestNodeName(String destNodeName) {
		DestNodeName = destNodeName;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		requestMSG msg = (requestMSG) o;

		if (identifier != msg.identifier)
			return false;
		
		if (clientPort != msg.clientPort)
			return false;

		if (serverPort != msg.serverPort)
			return false;


		if (hostname != null ? !hostname.equals(msg.hostname) : msg.hostname != null)
			return false;
		
		if (DestNodeName != null ? !DestNodeName.equals(msg.DestNodeName) : msg.DestNodeName != null)
			return false;

		return true;

	}

	public int hashCode() {

		return identifier;

	}

	public String toString() {

		return "Identifier = " + getIdentifier() + " ; hostname = " + getHostname() + " ; Client Port = " + clientPort + "; Destination Node = " + getDestNodeName() +";Server Port = "+serverPort+"\n";

	}

	public requestMSG(int id, String hostname, int clientport, int serverport,String DestNodeName) {
		this.identifier = id;
		this.hostname = hostname;
		this.clientPort = clientport;
		this.DestNodeName = DestNodeName;
		this.serverPort = serverport;
	}

}
