package Entity;

public class ProxyHost {
	private String  ip;
	private int       port;
	//国内还是国外
	private boolean  position;
	private int type;
	private int  protocol;
	
	//constructor
	public  ProxyHost()
	{
		
	}
	
	public  ProxyHost(String ip,int port )
	{
		this.ip=ip;
		this.port=port;
	}
	
	public ProxyHost(String ip,int port ,boolean pos,int type,int protocol)
	{
		this.ip=ip;
		this.port=port;
		this.position=pos;
		this.setType(type);
		this.setProtocol(protocol);
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public boolean isPosition() {
		return position;
	}
	public void setPosition(boolean position) {
		this.position = position;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getProtocol() {
		return protocol;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
}
