package tango.client.share;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import tango.client.messenger.Messenger;
import tango.client.messenger.view.ChatWindowView;
import tango.client.messenger.view.ShareTangoWindowView;

/**
 * @author yasukawa
 * This class is a main panel of "Game Tango!".
 */
public class ShareTangoMain extends Composite{
	public static HorizontalPanel mainPanel;
	//public Messenger messenger = new Messenger();
			
	public ShareTangoMain(){
		/* Initialize */
		mainPanel = new HorizontalPanel();
		
		/* Share Tango! Layout*/
		mainPanel.add(new Messenger());

		/* style sheet */
		mainPanel.setStyleName("ShareTango-main");
		
		initWidget(mainPanel);
	}
	
	static public void setChatWindow(ChatWindowView cwView){
		mainPanel.add(cwView);
		System.out.println("setChatWindow has done!");
	}
	static public void setShareTangoWindow(ShareTangoWindowView stwView){
		mainPanel.add(stwView);
		System.out.println("setShareTangoWindow has done!");
	}
}