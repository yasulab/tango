package tango.client.rss.ui;

import java.util.Set;

import tango.client.Tango_ex;
import tango.client.messenger.view.MessengerView;
import tango.client.rss.NewsFields;
import tango.client.rss.RSSTangoMain;
import tango.client.rss.RankingFields;
import tango.client.ui.PickupTango;
import tango.client.ui.TangoPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * {@link TangoPanel}をRSS用に拡張したクラスです。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public class RSSPanel extends TangoPanel {
	private class RSSTangoPanelPopup extends PopupPanel {
		private Label cLabel;
		private ClickListener cListener;
		private Label rLabel;
		private Button saveBtn;
		private VerticalPanel vPanel;

		public RSSTangoPanelPopup() {
			super(true); // 範囲外をクリックすることでポップアップを消せるようにする
			RSSPickupTango rssTango = (RSSPickupTango) tango;
			vPanel = new VerticalPanel();
			rLabel = new Label("ランク：" + rssTango.getRank() + "位");
			cLabel = new Label("回数：" + rssTango.getCount() + "回");
			cListener = new ClickListener() {
				public void onClick(Widget sender) {
					if (isSaveWord() == false) {
						if (Tango_ex.saveWordTable.isFull()) {
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
				/*
				TangoPanel compare;
				for (int i = 0; i < Tango.saveWordTable.flexTable.getRowCount(); i++) {
					for (int j = 0; j < Tango.saveWordTable.flexTable.getCellCount(i); j++) {
						compare = (TangoPanel) Tango.saveWordTable.flexTable.getWidget(i, j);
						if (Tango.saveWordTable.flexTable.getWidget(i, j) != null
								&& compare.getPickupTango().getName().equals(
										tango.getName())) {
							saveBtn.setVisible(false);
						}
					}
				}
				 */
				if(Tango_ex.getSavedTangoList().contains(tango)){
					System.out.println("savedTangoListに同一の単語をハッケソ！");
					saveBtn.setVisible(false);
				}else{
					System.out.println("savedTangoListに同一の単語が見つかりませんでした。");
				}
				saveBtn.setText("この単語を保存する");
			} else {
				saveBtn.setText("この単語を削除する");
			}

			vPanel.add(rLabel);
			vPanel.add(cLabel);
			vPanel.add(saveBtn);
			setWidget(vPanel);
		}
	}

	private boolean savedFlag;
	private final PickupTango tango;
	private Button wordPanel;
	private int xPos;

	private int yPos;

	/* Default Constructor */
	public RSSPanel() {
		super("null");
		this.tango = new RSSPickupTango("new", 0, 0);
		this.wordPanel = new Button(tango.getName());
		this.savedFlag = false;

		this.wordPanel.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				// Create the new popup.
				final RSSTangoPanelPopup popup = new RSSTangoPanelPopup();
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
		this.wordPanel.addStyleName("RSSPanel");

		initWidget(wordPanel);
	}

	/* Main Constructor */
	/**
	 * <p>
	 * このコンストラクタは{@link #tangoPanel(Object)}で置き換えられました。
	 * </p>
	 * <p>
	 * RankingFieldsを引数としてRSSPanelを生成するときには、必ず{@link #tangoPanel(Object)}
	 * を使用してください。<br>
	 * {@link TangoPanel}との互換性を保つためにこのコンストラクタからのインスタンス生成は禁止されています。
	 * </p>
	 * 
	 * @deprecated
	 */
	public RSSPanel(RankingFields rankingFields) {
		super("null");
		this.tango = new RSSPickupTango(rankingFields);
		this.wordPanel = new Button(tango.getName());
		this.savedFlag = false;
		// this.newsCollection = rankingFields.getNewsFieldsCollection();

		this.wordPanel.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				showNewsPanel(((RSSPickupTango) tango)
						.getNewsFieldsCollection());

				// Create the new popup.
				final RSSTangoPanelPopup popup = new RSSTangoPanelPopup();
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

		/* style sheet */
		this.wordPanel.addStyleName("RSSPanel");

		this.initWidget(wordPanel);
	}

	/**
	 * <p>
	 * このコンストラクタは{@link #tangoPanel(Object)}で置き換えられました。
	 * </p>
	 * <p>
	 * RSSpickupTangoを引数としてRSSPanelを生成するときには、必ず{@link #tangoPanel(Object)}
	 * を使用してください。<br>
	 * {@link TangoPanel}との互換性を保つためにこのコンストラクタからのインスタンス生成は禁止されています。
	 * </p>
	 * 
	 * @deprecated
	 */
	public RSSPanel(RSSPickupTango pickupTango) {
		super("null");
		this.tango = pickupTango;
		this.wordPanel = new Button(tango.getName());
		this.savedFlag = false;
		// this.newsCollection = rankingFields.getNewsFieldsCollection();

		this.wordPanel.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				showNewsPanel(((RSSPickupTango) tango)
						.getNewsFieldsCollection());

				// Create the new popup.
				final RSSTangoPanelPopup popup = new RSSTangoPanelPopup();
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

		/* style sheet */
		this.wordPanel.addStyleName("RSSPanel");

		this.initWidget(wordPanel);
	}

	public void deleteWord() {
		this.removeFromParent();
		Tango_ex.saveWordTable.infillSpaces();


		// for share tango!
		MessengerView.getListener().onRefreshShareTangoList(Tango_ex.getSavedTangoList());
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
		((RSSPanel) clonedPanel).savedFlag = true;
		Tango_ex.saveWordPanel(clonedPanel);

		// for share tango!
		if(MessengerView.getShareWindowOpenFlag()==true){
			MessengerView.getListener().onRefreshShareTangoList(Tango_ex.getSavedTangoList());
		}
	}

	// この関数を呼び出したword panelをsaveWordTableの(x,y)座標に保存する
	public void saveWord(int row, int col) {
		Tango_ex.saveWordPanel(row, col, this);
	}

	/* Word Panelの表示名を変更する */
	public void setText(String titleLabel) {
		wordPanel.setText(titleLabel);
	}

	private void showNewsPanel(Set<NewsFields> newsCollection) {
		RSSTangoMain.clearNewsPanel();
		if (newsCollection != null)
			for (NewsFields newsFields : newsCollection) {
				String html = new String();

				html += "\n\t<p class=\"RSSTitle\">"
					+ "<a class=\"RSSLink\" href=\"" + newsFields.getLink()
					+ "\">" + newsFields.getTitle() + "</a>" + "</p>";
				html += "\n\t<p class=\"RSSCategory\">" + "" + "</p>";
				html += "\n\t<span class=\"RSSChannelTitle\">"
					+ newsFields.getChannelTitle() + "</span>";
				html += "\n\t<p class=\"RSSDate\">" + newsFields.getDate()
				+ "</p>\n";

				HTMLPanel htmlPanelHeader = new HTMLPanel(html);
				htmlPanelHeader.setStyleName("RSSHtmlPanelHeader");

				html = new String();
				html += "\n\t<div class=\"RSSDescription\">"
					+ newsFields.getDescription() + "\n\t</div>\n";

				HTMLPanel htmlPanelBody = new HTMLPanel(html);
				htmlPanelBody.setStyleName("RSSHtmlPanelBody");

				FlowPanel htmlPanel = new FlowPanel();
				htmlPanel.add(htmlPanelHeader);
				htmlPanel.add(htmlPanelBody);
				htmlPanel.setStyleName("RSSHtmlPanel");

				RSSTangoMain.addToNewsPanel(htmlPanel);
			}
	}
}
