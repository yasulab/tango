package tango.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author yasukawa
 * Call WordPanel classes, and show them using FlexTable widget.
 */
public class WordPanelTable extends Composite {
    VerticalPanel panel = new VerticalPanel();
    FlexTable flexTable;
	int randomNumber;

    public WordPanelTable(){
        initWidget(panel);
        HorizontalPanel buttons = new HorizontalPanel();
        panel.add(buttons);
        panel.setCellHorizontalAlignment(buttons, VerticalPanel.ALIGN_CENTER);

        clear();
    }
    
    public void clear(){
        flexTable = new FlexTable();
        flexTable.setSize("100%", "100%");
        panel.add(flexTable);
        flexTable.addStyleName("WordPanelTable-table");
        flexTable.addStyleName("WordPanelTable-panel");
        flexTable.setWidget(0, 0, new WordPanel());
    }
    
    public void changeReflect(int row, int col){
        panel.remove(flexTable);
		flexTable = new FlexTable();
        panel.add(flexTable);
        for(int i=0; i<row ; i++){
        	for(int j=0; j<col ; j++){
        		flexTable.setWidget(i, j, new WordPanel());
        	}
        }
        flexTable.setSize("100%", "100%");
    }
    
    public void shuffle(ArrayList<PickupWord> pickupWords){
    	int row = flexTable.getRowCount();	// 行数
        int col = flexTable.getCellCount(row-1); // 列数
        int listSize = pickupWords.size();
        int count = 0;
		panel.remove(flexTable);
		flexTable = new FlexTable();
        panel.add(flexTable);
        
        /* 行数×列数 > 取ってきた単語数  */
        /* -> 必ず重なる単語があるので、取ってきた単語をもう一度シャッフルして表示する */
        if(row * col > listSize){
        	// Window.alert("row="+row +", col="+col + ", listSize=" + listSize + "\n row * col > listSize");
        	for(int i=0; i<row ; i++){
        		for(int j=0; j<col ; j++){
        			randomNumber = (int)(Math.random() * listSize);
        			flexTable.setWidget(i, j, new WordPanel(pickupWords.get(randomNumber)));
        			// System.out.println("i="+i+", j="+j);
        		}
        	}
        
        /* 行数×列数  <= 取ってきた単語数の方 */
        /* 取ってきた単語が既にランダムなので、そのまま表示 */
        }else{
        	// Window.alert("row="+row +", col="+col + ", listSize=" + listSize + "\n row * col <= listSize");
        	for(int i=0; i<row ; i++){
        		for(int j=0; j<col ; j++){
        			flexTable.setWidget(i, j, new WordPanel(pickupWords.get(count)));
        			// System.out.println("i="+i+", j="+j);
        			count++;
        		}
        	}
        }
        
        // flexTable.setWidget(0, 0, new WordPanel());
        flexTable.setSize("100%", "100%");
    }
}