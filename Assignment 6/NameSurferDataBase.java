import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the
	 * requested file does not exist or if an error occurs as the file is being
	 * read.
	 */
	public NameSurferDataBase(String filename) {
		try {
			BufferedReader rd = new BufferedReader(new FileReader(NAMES_DATA_FILE));
			
				while(true) {
					line = rd.readLine();
					if(line==null)
						break;
					list.add(line);
				} 
			
			rd.close();
		} catch (IOException ex) {
			
		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If
	 * the name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		name=convert(name);
		NameSurferEntry temp;
		for(int i=0;i<list.size();i++){
			temp=new NameSurferEntry(list.get(i));
			if(name.equals(temp.getName())){
				return temp;
			}
		}
		return null;
	}

	private String convert(String name){
		String fin ="";
		char[] temp=new char[name.length()];
		for(int i=0;i<name.length();i++){
			temp[i]=name.charAt(i);
		}
		char ini=temp[0];
		if(Character.isLowerCase(temp[0])){
			ini=(char)(ini+'A'-'a');
			fin=ini+"";
		}else{
			fin=temp[0]+"";
		}
		for(int i=1;i<temp.length;i++){
			char t=temp[i];
			if(Character.isLowerCase(t))
				fin=fin+t;
			else{
				ini=(char)(t+'a'-'A');
				fin=fin+ini;
			}
		}
		return fin;
	}
	NameSurferEntry entry;
	ArrayList<String> list = new ArrayList<String>();
	String line=" ";
}
