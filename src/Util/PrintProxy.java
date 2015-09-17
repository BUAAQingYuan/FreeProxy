package Util;

public class PrintProxy {
	public  static  String  printProxyType(int type)
	{
		String  Anonymity = null;
		switch(type)
		{
			case 1:
				Anonymity="Transparent";
				break;
			case 2:
				Anonymity="Anonymous";
				break;
			case 3:
				Anonymity="HighAnonymous";
				break;
			case 4:
				Anonymity="NoUseProxy";
				break;
			case 5:
				Anonymity="Unknown";
				break;
		}
		System.out.println("The Proxy IP's Anonymity is : "+Anonymity);
		return Anonymity;
	}
}
