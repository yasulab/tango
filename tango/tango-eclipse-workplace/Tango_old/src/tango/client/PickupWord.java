package tango.client;

/* 
 * We must define Default Constructor to communication Server.
 * Fetching data from PickupWordDictionary are insert this class.
 * This class is used for WordPanel Class to display.
 */

import com.google.gwt.user.client.rpc.IsSerializable;

public class PickupWord implements IsSerializable {
	public int wordLevel;
	public String name;
	public String howToRead;
	public String description;
	public String category;
	public PickupWord(){
		this.wordLevel = -1;
		this.howToRead = "default howToRead";
		this.name = "default name";
		this.description = "default description";
		this.category="unknown";
	}
	
	public PickupWord(int wordLevel, String howToRead, String name, String description, String category){
		this.wordLevel = wordLevel;
		this.howToRead = howToRead;
		this.name = name;
		this.description = description;
		this.category=category;
	}
	
	public int getWordLevel(){return wordLevel;}
	public String getHowToRead(){return howToRead;}
	public String getName(){return name;};
	public String getDescription(){return description;}
	public String getCategory(){return category;}
}
