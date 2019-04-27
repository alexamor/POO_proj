package program;

import utils.Initializer;
import utils.XML;

public class Main 
{

	static XML xml = new XML();
	//Atenção, ao mudar locais verificar se isto se mantém
	static String xml_path = System.getProperty("user.dir") + "\\\\src\\\\utils\\\\XML.xml";
	
	public static void main (String [] args)
	{
			
		try {
			
			Initializer init = XML.LoadXML(xml_path);
			//System.out.println(init.toString());			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
