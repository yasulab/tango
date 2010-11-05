/**
 * TIPS：
 * ・日本語がソースコードに入っていると、通信時にError(?) -> Ex. no serializeable data
 */
package tango.client.ui;


import java.util.ArrayList;



import com.google.gwt.user.client.rpc.IsSerializable;

public class PickupTangoResultSet implements IsSerializable {
	public String debugMassage;
	public boolean debugMode = false;		// for debugging
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
	public ArrayList<PickupTango> pickupWords = new ArrayList<PickupTango>();
	public int wordTableSize;
	public String dbPath;

	public void setAllData(LevelBox wlb, ClassBox wcb, RelationalBox rwb, TangoTableBox wtb){
		setWordLevel(wlb);
		setWordClass(wcb);
		setRelativeWord(rwb);
		setTableSize(wtb);
	}

	public void setWordLevel(LevelBox wlb){
		if(wordLevelEnableFlag = wlb.isEnableChecked()){
			wordLevelEasyFlag = wlb.isEasyChecked();
			wordLevelNormalFlag = wlb.isNormalChecked();
			wordLevelHardFlag = wlb.isHardChecked();
		}else{
			wordLevelEasyFlag = false;
			wordLevelNormalFlag = false;
			wordLevelHardFlag = false;
		}
	}

	public void setWordClass(ClassBox wcb){
		if(wordClassEnableFlag = wcb.isEnableChecked()){
			wordClassNounFlag = wcb.isNounChecked();
			wordClassVerbFlag = wcb.isVerbChecked();
			wordClassAdjFlag = wcb.isAdjChecked();
		}else{
			wordClassNounFlag = false;
			wordClassVerbFlag = false;
			wordClassAdjFlag = false;
		}
	}

	public void setRelativeWord(RelationalBox rwb){
		if(relationalWordEnableFlag = rwb.isEnableChecked()){
			relationalWord = rwb.getText();
			if(rwb.getText().equals(null))	relationalWord = null;
		}else{
			relationalWord = null;
		}
	}

	public void setTableSize(TangoTableBox wtb){
		wordTableSize = wtb.getTableSize();
	}

	public void debugModeOn(){ debugMode = true; }
	public void debugModeOff(){ debugMode = false; }

	public String showRelativeWord(){ return relationalWord; }
	public boolean showRelativeWordFlag(){ return relationalWordEnableFlag; }
}