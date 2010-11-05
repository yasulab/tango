package tango.client.canvas;

import java.util.ArrayList;

import tango.client.ui.PickupTango;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * @author yasukawa
 * Call WordPanel classes, and show them using FlexTable widget.
 */
public class CanvasPanelTable extends Composite {
	VerticalPanel panel = new VerticalPanel();
	FlexTable flexTable;
	int randomNumber;

	/* DefaultのConstructor */
	public CanvasPanelTable(){
		HorizontalPanel buttons = new HorizontalPanel();
		panel.add(buttons);
		panel.setCellHorizontalAlignment(buttons, VerticalPanel.ALIGN_CENTER);
		flexTable = new FlexTable();
		// flexTable.setSize("100%", "100%");
		panel.add(flexTable);
		flexTable.addStyleName("WordPanelTable-table");
		flexTable.addStyleName("WordPanelTable-panel");

		/* セルがクリックされた時の振る舞い */
		flexTable.addTableListener(new TableListener(){
			public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
				System.out.println("row="+row+", cell="+cell);
			}
		});

		flexTable.setWidget(0, 0, new CanvasPanel());
		initWidget(panel);
	}

	/* flex tableの中身を消去する */
	public void clearCanvasPanel(){
		this.flexTable.clear();
	}

	/* 行列(row×col)のflex tableを再生成する */
	public void changeReflect(int row, int col){
		this.flexTable.clear();
		this.flexTable.setWidget(0, 0, new CanvasPanel());
		for(int j=0; j<col-1 ; j++){
			this.addHorizontalPanel(new CanvasPanel());
		}

		this.setSize("100%", "100%");
	}
	/* saveWordTableの最上段の右端にWord Panelを保存する */
	public void addCanvasPanel(CanvasPanel wpanel){
		int row=0, col;
		infillSpaces(row);	//セルを左詰めする
		col = searchNullSpace(row);	// 右端のセルに保存
		flexTable.setWidget(row, col, wpanel);
	}

	/**/ 
	void addHorizontalPanel(CanvasPanel cPanel){
		this.addCanvas(makeLine("horizontal"));
		this.addCanvasPanel(cPanel);
	}

	void addVerticalPanel(CanvasPanel cPanel){
		this.addCanvas(makeLine("vertical"));
		this.addCanvasPanel(cPanel);
	}

	/*垂直線/水平線のcanvasを作成する関数*/
	private GWTCanvas makeLine(String lineName){
		GWTCanvas line = new GWTCanvas(50, 50);
		int x1, x2, y1, y2;

		/* Initialize */
		x1=y1=0;
		x2 = line.getCoordWidth();
		y2 = line.getCoordHeight();

		if(lineName.equals("vertical")){
			line.setLineWidth(1);    
			line.setStrokeStyle(Color.GREEN);
			line.beginPath();
			line.setTitle("vertical");
			line.moveTo(x2/2, y1);     
			line.lineTo(x2/2, y2);    
			line.closePath(); 
			line.stroke();
		}else if(lineName.equals("horizontal")){
			line.setLineWidth(1);    
			line.setStrokeStyle(Color.GREEN);
			line.beginPath();
			line.setTitle("horizontal");
			line.moveTo(x1, y2/2);     
			line.lineTo(x2, y2/2);    
			line.closePath(); 
			line.stroke();
		}else{
			line.setLineWidth(1);    
			line.setStrokeStyle(Color.GREEN);
			line.beginPath();
			line.setTitle("Unexpected Error");
			line.moveTo(x1, y1);     
			line.lineTo(x2, y2);    
			line.closePath(); 
			line.stroke();
		}
		return line;
	}

	/* 引数で与えられた行の空白セルを左詰めする */
	private void infillSpaces(int row){
		int col, tempCol;
		// int test;
		boolean infillFlag;
		for(col=0; this.flexTable.isCellPresent(row, col)==true; col++){
			infillFlag=false;
			// System.out.println("("+row+","+col+") isCellPresent");

			/* セルが空白ではない場合 */
			if(!(this.flexTable.getHTML(row, col).equals(""))){
				// System.out.println("("+row+","+col+") has some HTML");

				/* セルが空白だった場合 */
			}else{
				// System.out.println("("+row+","+col+") has no HTML");
				tempCol=col;
				/* 左詰めする */
				for(; this.flexTable.isCellPresent(row, tempCol+1)==true; tempCol++){
					infillFlag=true;
					// test = tempCol+1;
					// System.out.println("("+row+","+tempCol+") <-- "+"("+row+","+test+")" );
					this.flexTable.setWidget(row, tempCol, this.flexTable.getWidget(row, tempCol+1));
				}
				if(infillFlag==true){
					this.flexTable.removeCell(row, tempCol);
					col--;	// 左詰めしたので、もう一度同じ行からチェックする
				}
			}
		}
		// System.out.println("("+row+","+col+") isCellAbsent");
	}

	/* 引数で与えられた行において、空白セルを探し、その列を返す。*/
	private int searchNullSpace(int row){
		int col=0;
		while(this.flexTable.isCellPresent(row, col)==true&&!(this.flexTable.getHTML(row, col).equals(""))){
			// System.out.println("("+row+","+col+")==true");
			System.out.println(this.flexTable.getHTML(row, col));
			col++;
		}
		// System.out.println("("+row+","+col+")==false");
		return col;
	}

	/* flexTableの指定した箇所にWord Panelを保存する */
	public void addWordPanel(int row, int col, CanvasPanel wpanel){
		System.out.println("func:saveWordPanel");
		System.out.println("row="+row+", col="+col);
		flexTable.setWidget(row, col, wpanel);
	}

	/* tango!ボタンを押したときに、必要に応じて単語をシャッフルする */ 
	public void shuffle(ArrayList<PickupTango> pickupWords){
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
					flexTable.setWidget(i, j, new CanvasPanel(pickupWords.get(randomNumber)));
					// System.out.println("i="+i+", j="+j);
				}
			}

			/* 行数×列数  <= 取ってきた単語数の方 */
			/* 取ってきた単語が既にランダムなので、そのまま表示 */
		}else{
			// Window.alert("row="+row +", col="+col + ", listSize=" + listSize + "\n row * col <= listSize");
			for(int i=0; i<row ; i++){
				for(int j=0; j<col ; j++){
					flexTable.setWidget(i, j, new CanvasPanel(pickupWords.get(count)));
					// System.out.println("i="+i+", j="+j);
					count++;
				}
			}
		}

		flexTable.setSize("100%", "100%");
	}

	public void addCanvas(GWTCanvas canvas) {
		int row=0, col;
		infillSpaces(row);	//セルを左詰めする
		col = searchNullSpace(row);	// 右端のセルに保存
		this.flexTable.setWidget(row, col, canvas);
	}
}