/**
 * 
 */

package tango.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

/**
 * @author yasukawa
 * This class shows a footer panel at the bottom of page.
 */
public class FooterPanel extends Composite {
	String sentence1 = "(C) 2008 Yohei Yasukawa, Tomoki Fukushima and all \"tango!\" peoject members all rights deserve to be reserved.";
	private Label footerLabel = new Label(sentence1);
	
	public FooterPanel(){
		footerLabel.setHorizontalAlignment(Label.ALIGN_RIGHT);
		/* style sheet */
		footerLabel.setStyleName("Footer-description");
	    
		initWidget(footerLabel);
	}
}