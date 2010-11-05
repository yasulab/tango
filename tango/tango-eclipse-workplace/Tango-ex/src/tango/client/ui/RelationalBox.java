/**
 * 
 */
package tango.client.ui;

import tango.client.word.WordTangoMain;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa
 *
 */
public class RelationalBox extends Composite{
	VerticalPanel mainPanel = new VerticalPanel();
	private Label label = new Label("★Relational Word");
	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox("使用する");
	
	public RelationalBox(final WordTangoMain wordTangoMain){
		mainPanel.add(label);
		mainPanel.add(checkBox);
		mainPanel.add(textBox);
		checkBox.setChecked(false);
		checkBox.addClickListener(new ClickListener(){
			public void onClick(Widget sender){
				if(sender == checkBox){
					textBox.setEnabled(checkBox.isChecked());
				}
			}
		});
		textBox.setText("亜");
		textBox.setEnabled(checkBox.isChecked());
		textBox.addKeyboardListener(new KeyboardListener(){
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				if((int)keyCode == 13){	/* Enter key pressed */
					System.out.println("enter key pressed.");
					wordTangoMain.tangoButton.click();
				}else System.out.println("int keyCode=" + (int)keyCode + ", int modifiers=" + modifiers);
			}

			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub	
			}
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub				
			}
		});
		
		// set style sheet
		label.setStyleName("RelationalWord-label");
		checkBox.setStyleName("RelationalWord-checkBox");
		textBox.setStyleName("RelationalWord-textBox");
		
		initWidget(mainPanel);
		this.setStyleName("relativeWordBox");
	}
	
	public boolean isEnableChecked(){ return checkBox.isChecked(); }
	public void setText(String text){ textBox.setText(text); }
	public String getText(){ return textBox.getText(); }
}
