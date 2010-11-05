package tango.client.ui;

/* 
 * We must define Default Constructor to communication Server.
 * Fetching data from PickupWordDictionary are insert this class.
 * This class is used for WordPanel Class to display.
 */

import com.google.gwt.user.client.rpc.IsSerializable;

public class PickupTango implements IsSerializable {
	private int wordLevel;
	private String name;
	private String howToRead;
	private String description;
	private String category;
	public PickupTango(){
		this.wordLevel = -1;
		this.howToRead = "default howToRead";
		this.name = "default name";
		this.description = "default description";
		this.category="unknown";
	}
	
	public PickupTango(int wordLevel, String howToRead, String name, String description, String category){
		this.wordLevel = wordLevel;
		this.howToRead = howToRead;
		this.name = name;
		this.description = description;
		this.category = category;
	}
	
	public int getWordLevel(){return wordLevel;}
	public String getHowToRead(){return howToRead;}
	public String getName(){return name;};
	public String getDescription(){return description;}
	public String getCategory(){return category;}
}
