/**
 * 
 */
package tango.client.word;

import java.util.ArrayList;

import tango.client.ui.ClassBox;
import tango.client.ui.LevelBox;
import tango.client.ui.PickupTango;
import tango.client.ui.PickupTangoResultSet;
import tango.client.ui.RelationalBox;
import tango.client.ui.TangoPanelTable;
import tango.client.ui.TangoTableBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa
 * This class is a main panel of "Word Tango!".
 */
public class WordTangoMain extends Composite{
	DockPanel mainPanel;
	HorizontalPanel settingPanel;
	ScrollPanel settingScrollPanel;
	RelationalBox relationalWordBox;
	public Button tangoButton;
	HorizontalPanel settingHorizontalPanel;
	TangoPanelTable wordPanelTable;
	LevelBox wordLevelBox;
	ClassBox wordClassBox;
	TangoTableBox wordTableBox;
	CheckBox debugBox;
	public String dbPath;

	public ArrayList<PickupTango> wordDictionary = new ArrayList<PickupTango>();
	PickupTangoResultSet resultSet = new PickupTangoResultSet();
	ArrayList<PickupTango> receivePickupWords = new ArrayList<PickupTango>();
	String errorMessage;

	public void getDbPath(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert("doGet onError!");
					// Code omitted for clarity
				}

				public void onResponseReceived(Request request, Response response) {
					//Window.alert("HTTP Request is on Success!");
					String fileData=response.getText();
					dbPath = fileData;
				}
			});
		} catch (RequestException e) {
			Window.alert(e.getMessage());
		}
	}


	public WordTangoMain(){
		/* Initialize */
		getDbPath("./db/path.txt");
		mainPanel = new DockPanel();
		settingPanel = new HorizontalPanel();
		settingScrollPanel  = new ScrollPanel();
		relationalWordBox = new RelationalBox(this);;
		tangoButton = new Button("tango!");
		settingHorizontalPanel = new HorizontalPanel();
		wordPanelTable = new TangoPanelTable();

		wordLevelBox = new LevelBox();
		wordClassBox = new ClassBox();
		wordTableBox = new TangoTableBox(wordPanelTable);
		debugBox = new CheckBox("debug mode");


		/* Click Action on "Tango!" button */
		tangoButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				/* Initialize */
				resultSet.debugMode = debugBox.isChecked();
				resultSet.dbPath = dbPath;
				errorMessage = "errorMessage default";
				resultSet.pickupWords.clear();
				resultSet.setAllData(wordLevelBox, wordClassBox, relationalWordBox, wordTableBox);

				if(resultSet.debugMode==true){
					setTestDataOnLocal(resultSet);
					showPickupWords(resultSet.pickupWords);
				}else{

					// プロキシオブジェクトを取得
					PickupWordServiceAsync proxy = (PickupWordServiceAsync) GWT.create(PickupWordService.class);

					// サービスへのエントリポイントを設定
					ServiceDefTarget entrypoint = (ServiceDefTarget) proxy;
					String path = GWT.getModuleBaseURL();
					entrypoint.setServiceEntryPoint(path+"pickupWordService");

					// PickupWordServiceの反応を定義
					AsyncCallback<Object> callback = new AsyncCallback<Object>() {
						@SuppressWarnings("unchecked")	// 警告を投げる

						/* 単語の取得に成功した場合 */
						public void onSuccess(Object returnMsg){
							ArrayList<PickupTango> pickupWords = (ArrayList<PickupTango>) returnMsg;
							showPickupWords(pickupWords);

							/*** debug mode ***/
							if(resultSet.debugMode == true){
								Window.alert(pickupWords.get(0).getDescription());
							}
							/******************/
						}

						/* 単語の取得に失敗した場合 */
						public void onFailure(Throwable caught) {
							Window.alert("[onFailure] 'caught.getMessage()' is \n" + caught.getMessage() + "\ndbPath="+dbPath);
						}
					};

					// サービスの呼び出し
					proxy.pickupWordService(resultSet, callback);
				}
			}
		});

		/* Simple tango! Panel Layout */
		/* Word Panel Table Layout */
		mainPanel.add(settingPanel, DockPanel.NORTH);
		mainPanel.add(wordPanelTable, DockPanel.CENTER);
		mainPanel.setCellHeight(wordPanelTable,"0px");
		mainPanel.setCellHorizontalAlignment(wordPanelTable, DockPanel.ALIGN_CENTER);
		mainPanel.setCellVerticalAlignment(wordPanelTable, DockPanel.ALIGN_TOP);
		settingPanel.add(tangoButton);
		settingPanel.add(settingScrollPanel);
		settingPanel.setCellVerticalAlignment(tangoButton, HorizontalPanel.ALIGN_MIDDLE);
		settingPanel.setCellVerticalAlignment(settingScrollPanel, HorizontalPanel.ALIGN_MIDDLE);
		settingScrollPanel.setAlwaysShowScrollBars(false); // make Scroll Bar visible?, or not.
		settingScrollPanel.setPixelSize(600, 120);
		settingScrollPanel.add(settingHorizontalPanel);
		settingHorizontalPanel.add(wordLevelBox);
		settingHorizontalPanel.add(wordClassBox);
		settingHorizontalPanel.add(relationalWordBox);
		settingHorizontalPanel.add(wordTableBox);
		settingHorizontalPanel.add(debugBox);

		/* Setting Style Sheet */
		settingPanel.setStyleName("SettingPanel");
		tangoButton.setStyleName("TangoButton");
		settingScrollPanel.setStyleName("SettingScrollPanel");
		settingHorizontalPanel.setStyleName("SettingHorizontalPanel");


		initWidget(mainPanel);
	}

	private void showPickupWords(ArrayList<PickupTango> pickupWords) {
		if(pickupWords.get(0).getName() == "SQLException"){
			errorMessage = pickupWords.get(0).getDescription()
			+ "\n" + pickupWords.get(0).getClass();
			Window.alert(errorMessage);
		}else if(pickupWords.get(0).getName() == "Exception"){
			errorMessage = pickupWords.get(0).getDescription();
			Window.alert(errorMessage);
		}else if(pickupWords.size() > 0){
			wordPanelTable.shuffle(pickupWords);
		}else Window.alert("取得できる単語がありません。");
	}

	public void setTestDataOnLocal(PickupTangoResultSet pwrSet){
		pwrSet.pickupWords.clear();
		int select;
		for(int i=0; i<17; i++){
			select = (int)(17*Math.random());
			switch(select){
			case 0:	pwrSet.pickupWords.add(new PickupTango(i, "あげおし", "上尾市", "埼玉県の市。", "名詞"));
			break;
			case 1: pwrSet.pickupWords.add(new PickupTango(i, "あげかす", "揚げ滓", "飛行機を上に向ける、かじのとり方。", "名詞"));
			break;
			case 2: pwrSet.pickupWords.add(new PickupTango(i, "あげさげ" , "上げ下げ", "上げることと、下げること。", "名詞"));
			break;
			case 3: pwrSet.pickupWords.add(new PickupTango(i, "あげしお", "上げ潮", "潮が満ちること。みちしお。", "名詞"));
			break;
			case 4: pwrSet.pickupWords.add(new PickupTango(i, "あげざる", "揚げ笊", "ゆでたそばをすくい上げる、ざる。", "名詞"));
			break;		
			case 5: pwrSet.pickupWords.add(new PickupTango(i, "あげぜん", "上げ膳", "客に、食事の膳を出すこと。", "名詞"));
			break;
			case 6: pwrSet.pickupWords.add(new PickupTango(i, "あげぞこ", "上げ底", "中身を多く見せるため、そこを上げた容器。", "名詞")	);
			break;
			case 7: pwrSet.pickupWords.add(new PickupTango(i, "あげたて", "揚げ立て", "油で揚げたばかりであること。", "名詞"));
			break;
			case 8: pwrSet.pickupWords.add(new PickupTango(i, "あげだし", "揚げ出し", "片栗粉をまぶし、油であげたもの。", "名詞"));
			break;
			case 9: pwrSet.pickupWords.add(new PickupTango(i, "あげたま", "挙げ玉", "揚げ物をして、油の中に残ったかす。", "名詞"));
			break;
			case 10: pwrSet.pickupWords.add(new PickupTango(i, "あげなす", "揚げ茄子", "＊＊＊", "名詞"));
			break;
			case 11: pwrSet.pickupWords.add(new PickupTango(i, "あげなべ", "揚げ鍋", "揚げ物に使うなべ。", "名詞"));
			break;
			case 12: pwrSet.pickupWords.add(new PickupTango(i, "あげはば", "上げ幅", "元の位置から、上がった位置までの幅。", "名詞"));
			break;
			case 13: pwrSet.pickupWords.add(new PickupTango(i, "あげはま", "揚げ浜", "機械などで、海水をくみ上げる塩田。", "名詞"));
			break;
			case 14: pwrSet.pickupWords.add(new PickupTango(i, "あげはま", "揚げ浜", "アゲハマ囲碁で、相手から取った石。", "名詞"));
			break;
			case 15: pwrSet.pickupWords.add(new PickupTango(i, "あげぶた", "上げ蓋", "取り外しができる床板。", "名詞"));
			break;
			case 16: pwrSet.pickupWords.add(new PickupTango(i, "あげパン", "揚げ―", "油であげたパン。", "名詞"));
			break;
			}
		}
	}
}
