package tango.client;

import com.google.gwt.http.client.*;
import java.util.ArrayList;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.HTTPRequest;
import com.google.gwt.user.client.ResponseTextHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import tango.client.RelationalWordBox;

public class Tango implements EntryPoint ,WindowResizeListener {
	DockPanel dockPanel = new DockPanel();
	DockPanel mainPanel = new DockPanel();
	HorizontalPanel settingPanel = new HorizontalPanel();
	ScrollPanel settingScrollPanel = new ScrollPanel();
	RelationalWordBox relativeWordBox = new RelationalWordBox();
	Button tangoButton = new Button("tango!");
	HorizontalPanel settingHorizontalPanel = new HorizontalPanel();
	WordPanelTable wordPanelTable = new WordPanelTable();
	WordLevelBox wordLevelBox = new WordLevelBox();
	WordClassBox wordClassBox = new WordClassBox();
	WordTableBox wordTableBox = new WordTableBox(wordPanelTable);
	PickupWordResultSet resultSet = new PickupWordResultSet();
	ArrayList<PickupWord> receivePickupWords = new ArrayList<PickupWord>();
	
	public ArrayList<PickupWord> wordDictionary=new ArrayList<PickupWord>();
	PickupWordDictionary pwd = new PickupWordDictionary(wordDictionary);
	
	HeaderPanel headerPanel = new HeaderPanel();
	FooterPanel footerPanel = new FooterPanel();
	
	/*********** CHECK THIS OUT ****************/
	// initialize("./db/wordList.txt");
	public static final int STATUS_CODE_OK = 200;
	public void initialize(String url) {
	    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
	    try {
	      Request response = builder.sendRequest(null, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	        	Window.alert("doGet onError!");
	          //Code omitted for clarity
	        }

	        public void onResponseReceived(Request request, Response response) {
	        	// Window.alert("HTTP Request is on Success!");
	        	String fileData=response.getText();
	        	makeArrayList(fileData);
//	        	Window.alert(response.getText());
	        // Code omitted for clarity
	        }
	      });
	    } catch (RequestException e) {
	    	Window.alert(e.getMessage());
	      // Code omitted for clarity
	    }
	  }
	/******************************************/
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		/* initialize*/
		initialize("./db/wordList.txt");
		
		/* Display Components */
		RootPanel.get().add(dockPanel);
		dockPanel.add(headerPanel, DockPanel.NORTH);
		dockPanel.add(mainPanel, DockPanel.CENTER);
		dockPanel.add(footerPanel, DockPanel.SOUTH);
		dockPanel.setSize("100%", "100%");
		//RootPanel.get().add(headerPanel);
		//RootPanel.get().add(mainPanel);
		//RootPanel.get().add(footerPanel);
		/* Set size of components */
		/* (100%,100%) = horizontal and vertical center. */
		headerPanel.setSize("100%", "0%");
		mainPanel.setSize("100%",	"0%");	
		footerPanel.setSize("100%", "0%");
		
		/* Click Action on "Tango!" button */
		tangoButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				resultSet.pickupWords.clear();
				System.out.println(wordDictionary.get((int)(Math.random()*wordDictionary.size())).getName()+"  "+wordDictionary.get((int)(Math.random()*wordDictionary.size())).getCategory());
				resultSet.setAllData(wordLevelBox, wordClassBox, relativeWordBox);
				boolean[] levelFlags=new boolean[4];
				boolean[] classFlags=new boolean[4];
				levelFlags[0]=resultSet.wordLevelEnableFlag;
				levelFlags[1]=resultSet.wordLevelEasyFlag;
				levelFlags[2]=resultSet.wordLevelNormalFlag;
				levelFlags[3]=resultSet.wordLevelHardFlag;
				classFlags[0]=resultSet.wordClassEnableFlag;
				classFlags[1]=resultSet.wordClassNounFlag;
				classFlags[2]=resultSet.wordClassVerbFlag;
				classFlags[3]=resultSet.wordClassAdjFlag;
				if(!resultSet.relationalWordEnableFlag){
					resultSet.pickupWords=pwd.randomReadWordDictinary(levelFlags, classFlags);
				}
				else{
					String word = resultSet.relationalWord;
					resultSet.pickupWords=pwd.relationalWordSearch(word);
				}

				//				setTestDataOnLocal(resultSet);
				if(resultSet.pickupWords.size() > 0){
					wordPanelTable.shuffle(resultSet.pickupWords);
				}else Window.alert("取得できる単語がありません。");
			}
		});
		
	    /* Setting Panel Layout */
	    mainPanel.add(settingPanel, DockPanel.NORTH);
	    mainPanel.setCellHorizontalAlignment(settingPanel, DockPanel.ALIGN_CENTER);
	    settingPanel.add(tangoButton);
	    settingPanel.add(settingScrollPanel);
	    settingPanel.setCellVerticalAlignment(tangoButton, HorizontalPanel.ALIGN_MIDDLE);
	    settingPanel.setCellVerticalAlignment(settingScrollPanel, HorizontalPanel.ALIGN_MIDDLE);
	    settingScrollPanel.setAlwaysShowScrollBars(false); // make Scroll Bar visible?, or not.
	    settingScrollPanel.setPixelSize(600, 120);
	    settingScrollPanel.add(settingHorizontalPanel);
	    settingHorizontalPanel.add(wordLevelBox);
	    settingHorizontalPanel.add(wordClassBox);
	    settingHorizontalPanel.add(relativeWordBox);
	    settingHorizontalPanel.add(wordTableBox);
	 
		
		
		/* Word Table Panel Layout */
		mainPanel.add(wordPanelTable, DockPanel.CENTER);
		mainPanel.setCellHeight(wordPanelTable,"0px");
		mainPanel.setCellHorizontalAlignment(wordPanelTable, DockPanel.ALIGN_CENTER);
		mainPanel.setCellVerticalAlignment(wordPanelTable, DockPanel.ALIGN_TOP);
	    
	    /* Setting Style Sheet */
		mainPanel.setStyleName("MainPanel");
	    settingPanel.setStyleName("SettingPanel");
	    tangoButton.setStyleName("TangoButton");
	    settingScrollPanel.setStyleName("SettingScrollPanel");
	    settingHorizontalPanel.setStyleName("SettingHorizontalPanel");
	    
	    /* Resizing for matching window size */
	    Window.addWindowResizeListener(this);
	    onWindowResized(Window.getClientWidth(), Window.getClientHeight());
	}
	
	/* Resizing function to each panel */
	public void onWindowResized(int width, int height) {
		//width*ratio
		wordPanelTable.setPixelSize((int)(width*0.7)-2, (int) (height * 0.3)-66);
		settingPanel.setPixelSize((int)(width*0.7)-2, (int) (height * 0.1)-66);
	}	
	
	public void makeArrayList(String fileData){
		char c= 0;
		String[] dividedWord=new String[5];
		String lineString = "";
		int wordLevel = 0;
		int wordCounter = 0;
		try{
		while(true){
			lineString="";
			while((c=fileData.charAt(wordCounter))!='\n'){
				wordCounter++;
				lineString +=c;				
			}
			dividedWord=lineString.split(",");
			wordLevel=dividedWord[2].length();
			wordDictionary.add(new PickupWord(wordLevel,dividedWord[1],dividedWord[2],dividedWord[3],dividedWord[4]));
			wordCounter++;
		}
		}catch(Exception e){
	}
		System.out.println("wordDictionary maked!  size="+wordDictionary.size());
	}
public void setTestDataOnLocal(PickupWordResultSet pwrSet){
		/* PickupWord(int id, String name, String howToRead, String description) */
		/* 実はIDはいらない・・・？ */
		///*
		// public PickupWord(int wordLevel, String howToRead, String name, String description, String category){
		pwrSet.pickupWords.clear();
		pwrSet.pickupWords.add(new PickupWord(0, "あげおし", "上尾市", "埼玉県の市。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(1, "あげかす", "揚げ滓", "飛行機を上に向ける、かじのとり方。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(2, "あげさげ" , "上げ下げ", "上げることと、下げること。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(3, "あげしお", "上げ潮", "潮が満ちること。みちしお。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(4, "あげざる", "揚げ笊", "ゆでたそばをすくい上げる、ざる。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(5, "あげぜん", "上げ膳", "客に、食事の膳を出すこと。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(6, "あげぞこ", "上げ底", "中身を多く見せるため、そこを上げた容器。", "名詞")	);
		pwrSet.pickupWords.add(new PickupWord(7, "あげたて", "揚げ立て", "油で揚げたばかりであること。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(8, "あげだし", "揚げ出し", "片栗粉をまぶし、油であげたもの。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(9, "あげたま", "挙げ玉", "揚げ物をして、油の中に残ったかす。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(10, "あげなす", "揚げ茄子", "＊＊＊", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(11, "あげなべ", "揚げ鍋", "揚げ物に使うなべ。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(12, "あげはば", "上げ幅", "元の位置から、上がった位置までの幅。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(13, "あげはま", "揚げ浜", "機械などで、海水をくみ上げる塩田。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(14, "あげはま", "揚げ浜", "アゲハマ囲碁で、相手から取った石。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(15, "あげぶた", "上げ蓋", "取り外しができる床板。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(15, "あげパン", "揚げ―", "油であげたパン。", "名詞"));
		//*/
	}
	
}

	