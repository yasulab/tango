
/**
 * 
 */
package tango.client.canvas;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa
 * Define input form that defines the row and column number at WordPanelTable class.
 * Called by Tango class.
 */
public class CanvasTableBox extends Composite{
	VerticalPanel mainPanel = new VerticalPanel();
	HorizontalPanel tablePanel = new HorizontalPanel();
	private Label titleLabel = new Label("★Word Table");
	private TextBox rowBox = new TextBox();
	private Label timesLabel = new Label("×");
	private TextBox colBox = new TextBox();
	
	public CanvasTableBox(final CanvasTangoMain canvasTangoMain, final CanvasPanelTable canvasPanelTable){
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
				checkText(canvasPanelTable);
			}
		});
		rowBox.addKeyboardListener(new KeyboardListener(){
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				if((int)keyCode == 13){	/* Enter key pressed */
					System.out.println("enter key pressed.");
					if(checkText(canvasPanelTable)) canvasTangoMain.tangoButton.click();
				}else System.out.println("int keyCode=" + (int)keyCode + ", int modifiers=" + modifiers);
			}

			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub	
			}
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub				
			}
		});

		/* column text box */
		colBox.setText("1");
		colBox.setEnabled(true);
		colBox.setWidth("20px");
		colBox.addChangeListener( new ChangeListener(){ 
			public void onChange( Widget sender ){
				checkText(canvasPanelTable);
			}
		});
		colBox.addKeyboardListener(new KeyboardListener(){
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				if((int)keyCode == 13){	/* Enter key pressed */
					System.out.println("enter key pressed.");
					if(checkText(canvasPanelTable)) canvasTangoMain.tangoButton.click();
				}else System.out.println("int keyCode=" + (int)keyCode + ", int modifiers=" + modifiers);
			}

			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub	
			}
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub				
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
	public boolean checkText(CanvasPanelTable canvasPanelTable){
		try{
			int row = Integer.parseInt(rowBox.getText());
			int col = Integer.parseInt(colBox.getText());
			if(row > 0 && col > 0 ){
				canvasPanelTable.changeReflect(row, col);
				return true;
			}else{
				Window.alert("0以下の値が入力されています。\n１以上の整数を入力してください。");
			}
			// 読み捨てる
		} catch (NumberFormatException e) {
			// Window.alert("数字以外の文字列が入力されています。\n１以上の整数を入力してください。");
		} catch (ArrayIndexOutOfBoundsException e) {
			// Window.alert("数字以外の文字列が入力されています。\n１以上の整数を入力してください。");
		}
		return false;
	}

	public int getTableSize(){
		int row, col;
		row = Integer.parseInt(rowBox.getText());
		col = Integer.parseInt(colBox.getText());
		return row * col;
	}
}
