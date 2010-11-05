/**
 * 
 */
package tango.client;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa
 *
 */
public class RelationalWordBox extends Composite implements ClickListener  {
	VerticalPanel mainPanel = new VerticalPanel();
	private Label label = new Label("★Relational Word");
	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox("使用する");
	
	public RelationalWordBox(){
		mainPanel.add(label);
		mainPanel.add(checkBox);
		mainPanel.add(textBox);
		checkBox.setChecked(false);
		checkBox.addClickListener(this);
		textBox.setText("亜");
		textBox.setEnabled(checkBox.isChecked());
		
		// set style sheet
		label.setStyleName("RelationalWord-label");
		checkBox.setStyleName("RelationalWord-checkBox");
		textBox.setStyleName("RelationalWord-textBox");
		
		initWidget(mainPanel);
		this.setStyleName("relativeWordBox");
	}
	
	public void onClick(Widget sender){
		if(sender == checkBox){
			textBox.setEnabled(checkBox.isChecked());
		}
	}
	
	public boolean isChecked(){ return checkBox.isChecked(); }
	public void setText(String text){ textBox.setText(text); }
	public String getText(){ return textBox.getText(); }	
}
