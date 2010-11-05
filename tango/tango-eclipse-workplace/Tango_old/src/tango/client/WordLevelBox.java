/**
 * 
 */
package tango.client;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa
 * Define input form that defines word levels.
 */
public class WordLevelBox extends Composite implements ClickListener{
	private Label label = new Label("★Word Level");
	private CheckBox enable = new CheckBox("使用する");
	private CheckBox easy = new CheckBox("やさしい(50000語)");
	private CheckBox normal = new CheckBox("ふつう(30000語)");
	private CheckBox hard = new CheckBox("むずかしい(10000語)");
	
	public WordLevelBox(){
		VerticalPanel panel = new VerticalPanel();
		panel.add(label);
		panel.add(enable);
		panel.add(easy);
		panel.add(normal);
		panel.add(hard);
		
		enable.addClickListener(this);
		enable.setChecked(false);
		easy.setEnabled(false);
		normal.setEnabled(false);
		hard.setEnabled(false);
		
		/* style sheet */
		label.setStyleName("WordLevel-label");
		easy.setStyleName("WordLevel-easy");
		normal.setStyleName("WordLevel-normal");
		hard.setStyleName("WordLevel-hard");
	    
		initWidget(panel);
	}
	
	public void onClick(Widget sender){
		if(sender == enable){
			if(enable.isChecked() == true){
				easy.setEnabled(true);
				normal.setEnabled(true);
				hard.setEnabled(true);	
			}else{
				easy.setEnabled(false);
				normal.setEnabled(false);
				hard.setEnabled(false);
			}
		}
	}
	
	public boolean isEnableChecked(){ return enable.isChecked(); }
	public boolean isEasyChecked(){ return easy.isChecked(); }
	public boolean isNormalChecked(){ return normal.isChecked(); }
	public boolean isHardChecked(){ return hard.isChecked(); }
	
	
	
}
