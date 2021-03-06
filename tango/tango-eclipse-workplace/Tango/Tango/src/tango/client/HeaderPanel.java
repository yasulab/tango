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
 * This class shows a header panel at the top of page.
 */
public class HeaderPanel extends Composite {
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private Image logo = new Image("./img/tango_logo.gif");
	private VerticalPanel descriptionPanel = new VerticalPanel();
	String sentence1 = "tango!は辞書からランダムに選ばれた単語をひたすら表示していくツールです。";
	String sentence2 = "思考に行き詰ったとき、なにか考えもしないようなひらめきが欲しいとき、";
	String sentence3 = "地球の裏側からやってくるような言葉のカケラ達があなたの創造力をかき立てます。";
	private Label line1 = new Label(sentence1);
	private Label line2 = new Label(sentence2);
	private Label line3 = new Label(sentence3);
	
	public HeaderPanel(){
		mainPanel.add(logo);
		mainPanel.add(descriptionPanel);
		mainPanel.setCellHorizontalAlignment(logo, HorizontalPanel.ALIGN_CENTER);
		descriptionPanel.add(line1);
		descriptionPanel.add(line2);
		descriptionPanel.add(line3);
		
		/* style sheet */
		logo.setStyleName("Header-logo");
		line1.setStyleName("Header-description");
		line2.setStyleName("Header-description");
		line3.setStyleName("Header-description");
	    
		initWidget(mainPanel);
	}
}