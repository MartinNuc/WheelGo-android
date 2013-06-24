package cz.nuc.wheelgo.reportingsystem;

import java.util.Date;

public class Duration {

	public static String DateToString(Date dur)
	{
		String ret = new String();
		long hours = dur.getTime()/1000/3600;

		if (hours>28*24)
			ret = "D�le ne� m�s�c";
		else if (hours>14*24)
			ret = "1 m�s�c";
		else if (hours>7*24)
			ret = "14 dn�";
		else if (hours>12)
			ret = "1 t�den";
		else if (hours>6)
			ret = "12 hodin";
		else if (hours>3)
			ret = "6 hodin";
		else
			ret = "3 hodin";
 
		return ret;
		
	}
}
