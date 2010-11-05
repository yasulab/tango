
/**
 * 
 */
package tango.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa
 * Define input form that defines the row and column number at WordPanelTable class.
 * Called by Tango class.
 */
public class WordTableBox extends Composite{
	VerticalPanel mainPanel = new VerticalPanel();
	HorizontalPanel tablePanel = new HorizontalPanel();
	private Label titleLabel = new Label("★Word Table");
	private TextBox rowBox = new TextBox();
	private Label timesLabel = new Label("×");
	private TextBox colBox = new TextBox();
	
	public WordTableBox(final WordPanelTable wordPanelTable){
		mainPanel.add(titleLabel);
		mainPanel.add(tablePanel);
		mainPanel.setCellHorizontalAlignment(tablePanel, HorizontalPanel.ALIGN_CENTER);
		tablePanel.add(rowBox);
		tablePanel.add(timesLabel);
		tablePanel.add(colBox);
		
		/* row text box */
		rowBox.setText("1");
		rowBox.setEnabled(true);
		rowBox.setWidth("20px");
		rowBox.addChangeListener( new ChangeListener(){ 
			public void onChange( Widget sender ){
				checkText(wordPanelTable);
			}
		});

		/* column text box */
		colBox.setText("1");
		colBox.setEnabled(true);
		colBox.setWidth("20px");
		colBox.addChangeListener( new ChangeListener(){ 
			public void onChange( Widget sender ){
				checkText(wordPanelTable);
			}
		});
		
		/* set style sheet */
		titleLabel.setStyleName("WordTable-label");
		rowBox.setStyleName("WordTable-rowBox");
		colBox.setStyleName("WordTable-colBox");
		
		initWidget(mainPanel);
		this.setStyleName("WordTable");
	}
	
	/* Read number at row box and column box. */
	public void checkText(WordPanelTable wordPanelTable){
		try{
			int row = Integer.parseInt(rowBox.getText());
			int col = Integer.parseInt(colBox.getText());
			if(row > 0 && col > 0 ){
				wordPanelTable.changeReflect(row, col);
			}else Window.alert("0以下の値が入力されています。\n１以上の整数を入力してください。");
		} catch (NumberFormatException e) {
			// 読み捨てる
			// Window.alert("数字以外の文字列が入力されています。\n１以上の整数を入力してください。");
		} catch (ArrayIndexOutOfBoundsException e) {
			// Window.alert("数字以外の文字列が入力されています。\n１以上の整数を入力してください。");
		}
	}
}
