package Entity;

public class ProxyConfig {
	private  String  Site;
	private  String  IpXpath;
	private  String  PortXpath;
	private  String  AnonymityXpath;
	private  String  ProtocolXpath;
	
	private  boolean  PageEnable;
	private  int           PageStart;
	private  int           PageEnd;
	
	public String getSite() {
		return Site;
	}
	public void setSite(String site) {
		Site = site;
	}
	public String getIpXpath() {
		return IpXpath;
	}
	public void setIpXpath(String ipXpath) {
		IpXpath = ipXpath;
	}
	public String getPortXpath() {
		return PortXpath;
	}
	public void setPortXpath(String portXpath) {
		PortXpath = portXpath;
	}
	public String getAnonymityXpath() {
		return AnonymityXpath;
	}
	public void setAnonymityXpath(String anonymityXpath) {
		AnonymityXpath = anonymityXpath;
	}
	public String getProtocolXpath() {
		return ProtocolXpath;
	}
	public void setProtocolXpath(String protocolXpath) {
		ProtocolXpath = protocolXpath;
	}
	public boolean isPageEnable() {
		return PageEnable;
	}
	public void setPageEnable(boolean pageEnable) {
		PageEnable = pageEnable;
	}
	public int getPageStart() {
		return PageStart;
	}
	public void setPageStart(int pageStart) {
		PageStart = pageStart;
	}
	public int getPageEnd() {
		return PageEnd;
	}
	public void setPageEnd(int pageEnd) {
		PageEnd = pageEnd;
	}
}
