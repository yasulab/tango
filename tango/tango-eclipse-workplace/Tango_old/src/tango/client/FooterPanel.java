/**
 * 
 */

package tango.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author yasukawa
 * This class shows a footer panel at the bottom of page.
 */
public class FooterPanel extends Composite {
	String sentence1 = "(C) 2008 b2-group Information Search Members all rights deserve to be researved.";
	private Label footerLabel = new Label(sentence1);
	
	public FooterPanel(){
		footerLabel.setHorizontalAlignment(Label.ALIGN_RIGHT);
		/* style sheet */
		footerLabel.setStyleName("Footer-description");
	    
		initWidget(footerLabel);
	}
}