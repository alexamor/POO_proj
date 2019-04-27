package program;

import utils.Initializer;
import utils.XML;

public class Main 
{

	static XML xml = new XML();
	static String xml_path = "D:\\JavaStuff\\POO_ze\\src\\utils\\XML.xml";
	
	public static void main (String [] args)
	{
			
		try {
			
			Initializer init = XML.LoadXML(xml_path);
			System.out.println(init.toString());			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
