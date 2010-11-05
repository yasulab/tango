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
 * Define input form that defines word classes.
 */
public class WordClassBox extends Composite implements ClickListener{
	private Label label = new Label("★Word Class");
	private CheckBox enable = new CheckBox("使用する");
	private CheckBox noun = new CheckBox("名詞");
	private CheckBox verb = new CheckBox("動詞");
	private CheckBox adj = new CheckBox("形容詞");
	
	public WordClassBox(){
		VerticalPanel panel = new VerticalPanel();
		panel.add(label);
		panel.add(enable);
		panel.add(noun);
		panel.add(verb);
		panel.add(adj);
		
		/* Initialize */
		enable.addClickListener(this);
		enable.setChecked(false);
		noun.setEnabled(false);
		verb.setEnabled(false);
		adj.setEnabled(false);
		
		/* style sheet */
		label.setStyleName("WordClass-label");
		noun.setStyleName("WordClass-noun");
		verb.setStyleName("WordClass-verb");
		adj.setStyleName("WordClass-adj");
	    
		initWidget(panel);
	}
	
	public void onClick(Widget sender){
		if(sender == enable){
			if(enable.isChecked() == true){
				noun.setEnabled(true);
				verb.setEnabled(true);
				adj.setEnabled(true);	
			}else{
				noun.setEnabled(false);
				verb.setEnabled(false);
				adj.setEnabled(false);
			}
		}
	}
	
	public boolean isEnableChecked(){ return enable.isChecked(); }
	public boolean isNounChecked(){ return noun.isChecked(); }
	public boolean isVerbChecked(){ return verb.isChecked(); }
	public boolean isAdjChecked(){ return adj.isChecked(); }
}
