/**
 * TIPS：
 * ・日本語がソースコードに入っていると、通信時にError(?) -> Ex. no serializeable data
 */
package tango.client;


import java.util.ArrayList;
import com.google.gwt.user.client.rpc.IsSerializable;

public class PickupWordResultSet implements IsSerializable {
  public String testMassage;
  public String wordLevelFlags ="";		// for debug
  public String wordClassFlags ="";		// for debug
  public boolean wordLevelEnableFlag;
  public boolean wordLevelEasyFlag;
  public boolean wordLevelNormalFlag;
  public boolean wordLevelHardFlag;
  public boolean wordClassEnableFlag;
  public boolean wordClassNounFlag;
  public boolean wordClassVerbFlag;
  public boolean wordClassAdjFlag;
  public boolean relationalWordEnableFlag;
  public String relationalWord;
  public ArrayList<PickupWord> pickupWords = new ArrayList<PickupWord>();
  
  public void setAllData(WordLevelBox wlb, WordClassBox wcb, RelationalWordBox rwb){
	  setWordLevel(wlb);
	  setWordClass(wcb);
	  setRelativeWord(rwb);
  }
  
  public void setWordLevel(WordLevelBox box){
	  wordLevelFlags = "";
	  if(wordLevelEnableFlag = box.isEnableChecked())	wordLevelFlags = wordLevelFlags + "Enable:";
	  if(wordLevelEasyFlag = box.isEasyChecked())	wordLevelFlags = wordLevelFlags + "E";
	  if(wordLevelNormalFlag = box.isNormalChecked())	wordLevelFlags = wordLevelFlags + "N";
	  if(wordLevelHardFlag = box.isHardChecked())	wordLevelFlags = wordLevelFlags + "H";
  }
  
  public void setWordClass(WordClassBox box){
	  wordClassFlags = "";
	  if(wordClassEnableFlag = box.isEnableChecked()) wordClassFlags = wordClassFlags + "Enable:";
	  if(wordClassNounFlag = box.isNounChecked()) wordClassFlags = wordClassFlags + "N";
	  if(wordClassVerbFlag = box.isVerbChecked()) wordClassFlags = wordClassFlags + "V";
	  if(wordClassAdjFlag = box.isAdjChecked())  wordClassFlags = wordClassFlags + "A";
  }
  
  public void setRelativeWord(RelationalWordBox box){
	  relationalWord = box.getText();
	  relationalWordEnableFlag = box.isChecked();
  }
  
  public String showRelativeWord(){ return relationalWord; }
  public boolean showRelativeWordFlag(){ return relationalWordEnableFlag; }
  public String showWordLevel(){ return wordLevelFlags; }	// for debug
  public String showWordClass(){ return wordClassFlags; }	// for debug
}