package tango.client.canvas;

import tango.client.ui.PickupTango;
import tango.client.ui.TangoPanel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa
 * Define contents of each panel that is appeared after pushing "Tango!" button. 
 * Mainly called by WordPanelTable.
 */
public class CanvasPanel extends TangoPanel {
	private Button wordPanel;
	private final String name;
	final String howToRead;
	final String description;
	private int xPos;
	private int yPos;

	/* Default Constructor */
	public CanvasPanel(){
		this.name = "NEW";
		this.wordPanel = new Button(this.name);
		this.howToRead = "にゅー";
		this.description = "新しい";
		
		/* setting */
		this.setPixelSize(50, 50);
		this.setStyleName("CanvasPanel");
		
		this.wordPanel.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				// Create the new popup.
				final CanvasPanelPopup popup = new CanvasPanelPopup();
				popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
					public void setPosition(int offsetWidth, int offsetHeight) {
						xPos = wordPanel.getAbsoluteLeft() + wordPanel.getOffsetWidth();
						yPos = wordPanel.getAbsoluteTop() + wordPanel.getOffsetHeight()/2;
						popup.setPopupPosition(xPos, yPos);
					}
				});
			}
		});

		//style sheet
		wordPanel.addStyleName("WordPanel");

		//super.initWidget(wordPanel);
	}

	/* Main Constructor */
	public CanvasPanel(PickupTango word){
		this.name = word.getName();
		this.wordPanel = new Button(this.name);
		this.howToRead = word.getHowToRead();
		this.description = word.getDescription();

		
		/* setting */
		this.setPixelSize(50, 50);
		this.setStyleName("CanvasPanel");
		

		this.wordPanel.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				// Create the new popup.
				final CanvasPanelPopup popup = new CanvasPanelPopup();
				popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
					public void setPosition(int offsetWidth, int offsetHeight) {
						xPos = wordPanel.getAbsoluteLeft() + wordPanel.getOffsetWidth();
						yPos = wordPanel.getAbsoluteTop() + wordPanel.getOffsetHeight()/2;
						popup.setPopupPosition(xPos, yPos);
					}
				});
			}
		});

		/* style sheet */
		wordPanel.addStyleName("WordPanel");

		super.initWidget(wordPanel);
	}

	private class CanvasPanelPopup extends PopupPanel{
		private VerticalPanel vPanel;
		private Label hLabel;
		private Label dLabel;

		public CanvasPanelPopup(){
			super(true);	// 範囲外をクリックすることでポップアップを消せるようにする
			vPanel = new VerticalPanel();
			hLabel = new Label("読み方：" + howToRead);
			dLabel = new Label("説明：" + description);
			vPanel.add(hLabel);
			vPanel.add(dLabel);
			setWidget(vPanel);
		}
	}
}
