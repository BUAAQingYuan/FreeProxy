package Entity;

public class ProxyHost {
	private String  ip;
	private int       port;
	//国内或国外
	private boolean  foreign;
	private int type;
	private int  protocol;
	private String  position;
	
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
		this.foreign=pos;
		this.type=type;
		this.protocol=protocol;
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

	public boolean isForeign() {
		return foreign;
	}

	public void setForeign(boolean foreign) {
		this.foreign = foreign;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
