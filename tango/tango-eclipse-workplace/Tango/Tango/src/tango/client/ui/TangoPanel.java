package tango.client.ui;

import java.util.ArrayList;

import tango.client.Tango;
import tango.client.messenger.view.MessengerView;
import tango.client.rss.RankingFields;
import tango.client.rss.ui.RSSPanel;
import tango.client.rss.ui.RSSPickupTango;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa Define contents of each panel that is appeared after pushing
 *         "Tango!" button. Mainly called by WordPanelTable.
 */
public class TangoPanel extends Composite {
	private class TangoPanelPopup extends PopupPanel {
		private ClickListener cListener;
		private Label dLabel;
		private Label hLabel;
		private Button saveBtn;
		private VerticalPanel vPanel;

		public TangoPanelPopup() {
			super(true); // 範囲外をクリックすることでポップアップを消せるようにする
			vPanel = new VerticalPanel();
			hLabel = new Label("読み方：" + tango.getHowToRead());
			dLabel = new Label("説明：" + tango.getDescription());
			cListener = new ClickListener() {
				public void onClick(Widget sender) {
					if (isSaveWord() == false) {
						if (Tango.saveWordTable.isFull()) {
							Window.alert("これ以上保存できません");
						} else {
							saveWord(); // この単語を保存し、テーブルから削除する。
						}
					} else {
						deleteWord();
					}
					setVisible(false); // 保存された単語のポップアップ表示を消す。
				}
			};
			saveBtn = new Button();
			saveBtn.addClickListener(cListener);

			if (isSaveWord() == false) {
				// savedTangoではなかったら, 保存できるかどうかを確認する
				ArrayList<PickupTango> tempList = Tango.getSavedTangoList();
				for(int i=0; i<tempList.size();i++ ){
					if(tempList.get(i).getName().equals(tango.getName())){
						System.out.println("savedTangoListに同一の単語をハッケソ！");
						saveBtn.setVisible(false);
						break;
					}
				}
				
				if(saveBtn.isVisible()==true){
						System.out.println("savedTangoListに同一の単語が見つかりませんでした。");					
				}
				//if(Tango.getSavedTangoList().contains(tango)){
				//	System.out.println("savedTangoListに同一の単語をハッケソ！");
				//	saveBtn.setVisible(false);
				//}else{
				//	System.out.println("savedTangoListに同一の単語が見つかりませんでした。");

				saveBtn.setText("この単語を保存する");
			} else {
				saveBtn.setText("この単語を削除する");
			}

			vPanel.add(hLabel);
			vPanel.add(dLabel);
			vPanel.add(saveBtn);
			setWidget(vPanel);
		}
	}

	/**
	 * <p>
	 * このクラスのファクトリーメソッドです。
	 * </p>
	 * <p>
	 * 引数のobjがどのクラスのインスタンスかによって呼び出すコンストラクタを変更します。<br>
	 * objが{@link PickupTango}であれば{@link #TangoPanel(PickupTango)}を、 objが
	 * {@link RSSPickupTango}であれば{@link #RSSPanel(RSSPickupTango)}を、 objが
	 * {@link RankingFields}であれば{@link #RSSPanel(RankingFields)}を呼び出します。<br>
	 * 
	 * このクラスを使用してインスタンスを生成することで、{@link RSSPanel}コンストラクタを呼び出すことなく{@link RSSPanel}
	 * を生成できます。
	 * </p>
	 * <p>
	 * RSSTangoを利用するために、このファクトリーメソッドでコンストラクタを生成することが推奨されています。
	 * </p>
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static TangoPanel tangoPanel(Object obj) {
		if (obj instanceof RankingFields)
			return new RSSPanel((RankingFields) obj);
		else if (obj instanceof RSSPickupTango)
			return new RSSPanel((RSSPickupTango) obj);
		else
			// if (obj instanceof PickupTango)
			return new TangoPanel((PickupTango) obj);

	}

	private boolean savedFlag;
	private final PickupTango tango;
	private Button wordPanel;

	private int xPos;

	private int yPos;

	/* Default Constructor */
	public TangoPanel() {
		this.tango = new PickupTango(0, "にゅー", "NEW", "新しい", "形容詞");
		this.wordPanel = new Button(tango.getName());
		this.savedFlag = false;

		wordPanel.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				// Create the new popup.
				final TangoPanelPopup popup = new TangoPanelPopup();
				popup
				.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
					public void setPosition(int offsetWidth,
							int offsetHeight) {
						xPos = wordPanel.getAbsoluteLeft()
						+ wordPanel.getOffsetWidth();
						yPos = wordPanel.getAbsoluteTop()
						+ wordPanel.getOffsetHeight() / 2;
						popup.setPopupPosition(xPos, yPos);
					}
				});
			}
		});

		// style sheet
		wordPanel.addStyleName("WordPanel");

		initWidget(wordPanel);
	}

	/* Main Constructor */
	/**
	 * <p>
	 * このコンストラクタは{@link #tangoPanel(Object)}で置き換えられました。
	 * </p>
	 * <p>
	 * PickupWordを引数としてTangoPanelを生成するときには、必ず{@link #tangoPanel(Object)}
	 * を使用してください。<br>
	 * {@link RSSPanel}との互換性を保つためにこのコンストラクタからのインスタンス生成は禁止されています。
	 * </p>
	 * 
	 * @deprecated
	 */
	public TangoPanel(PickupTango pickupWord) {
		tango = pickupWord;
		this.wordPanel = new Button(tango.getName());
		this.savedFlag = false;

		wordPanel.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				// Create the new popup.
				final TangoPanelPopup popup = new TangoPanelPopup();
				popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
					public void setPosition(int offsetWidth, int offsetHeight) {
						xPos = wordPanel.getAbsoluteLeft() + wordPanel.getOffsetWidth();
						yPos = wordPanel.getAbsoluteTop() + wordPanel.getOffsetHeight() / 2;
						popup.setPopupPosition(xPos, yPos);
					}
				});
			}
		});

		/* style sheet */
		wordPanel.addStyleName("WordPanel");

		initWidget(wordPanel);
	}

	public TangoPanel(String str) {
		this.tango = new PickupTango(0, "にゅー", "NEW", "新しい", "形容詞");
		this.wordPanel = new Button(tango.getName());
		this.savedFlag = false;
	}

	public void deleteWord() {
		this.removeFromParent();
		Tango.saveWordTable.infillSpaces();

		// for share tango!
		MessengerView.getListener().onRefreshShareTangoList(Tango.getSavedTangoList());
	}

	public PickupTango getPickupTango() {
		return tango;
	}

	public boolean isSaveWord() {
		return this.savedFlag;
	}

	/* この関数を呼び出したWord　PanelをsaveWordTableに保存する */
	public void saveWord() {
		TangoPanel clonedPanel = TangoPanel.tangoPanel(tango);
		clonedPanel.savedFlag = true;
		Tango.saveWordPanel(clonedPanel);

		// for share tango!
		// share tango!にサインインしていたらRefresh処理を施す
		if(MessengerView.getShareWindowOpenFlag()==true){
			System.out.println("refresh share tango window by saveWord()");
			MessengerView.getListener().onRefreshShareTangoList(Tango.getSavedTangoList());
		}
	}

	// この関数を呼び出したword panelをsaveWordTableの(x,y)座標に保存する
	public void saveWord(int row, int col) {
		Tango.saveWordPanel(row, col, this);
	}

	/* Word Panelの表示名を変更する */
	public void setText(String titleLabel) {
		wordPanel.setText(titleLabel);
	}
}