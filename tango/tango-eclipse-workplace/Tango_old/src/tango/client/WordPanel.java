package tango.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa
 * Define contents of each panel that is appeared after pushing "Tango!" button. 
 * Mainly called by WordPanelTable.
 */
public class WordPanel extends Composite {
	private Button wordPanel;
	private final String howToRead;
	private final String description;
	private int xPos;
	private int yPos;
	
	/* Default Constructor */
	public WordPanel(){
		this.wordPanel = new Button("NEW");
		this.howToRead = "にゅー";
		this.description = "新しい";
		
		wordPanel.addClickListener(new ClickListener() {
		      public void onClick(Widget sender) {
		        // Create the new popup.
		        final WordPanelPopup popup = new WordPanelPopup();
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
		
		initWidget(wordPanel);
	}
	
	/* Main Constructor */
	public WordPanel(PickupWord word){
		this.wordPanel = new Button(word.name);
		this.howToRead = word.howToRead;
		this.description = word.description;
		
		wordPanel.addClickListener(new ClickListener() {
		      public void onClick(Widget sender) {
		        // Create the new popup.
		        final WordPanelPopup popup = new WordPanelPopup();
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
		
		initWidget(wordPanel);
	}
	
	private class WordPanelPopup extends PopupPanel{
		private VerticalPanel vPanel = new VerticalPanel();
		private Label hLabel = new Label("読み方：" + howToRead);
		private Label dLabel = new Label("説明：" + description);
		public WordPanelPopup(){
			super(true);
			vPanel.add(hLabel);
			vPanel.add(dLabel);
			setWidget(vPanel);
		}
	}

	public void setText(String label){
		wordPanel.setText(label);
	}
}
