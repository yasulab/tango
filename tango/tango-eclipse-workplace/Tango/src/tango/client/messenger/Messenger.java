package tango.client.messenger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author yasukawa
 *
 */

public class Messenger extends Composite{
	static DockPanel mainPanel;
	
	public Messenger() {
		mainPanel = new DockPanel();
		MessengerServiceClientImpl messengerService = 
			new MessengerServiceClientImpl( GWT.getModuleBaseURL() + "messenger" );
		mainPanel.add(messengerService.getView(), DockPanel.CENTER);
		this.initWidget(mainPanel);
	}
}