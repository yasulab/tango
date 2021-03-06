package tango.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author yasukawa Call WordPanel classes, and show them using FlexTable
 *         widget.
 */
public class TangoPanelTable extends Composite {
	FlexTable flexTable;
	VerticalPanel panel = new VerticalPanel();
	int randomNumber;
	private final int SAVE_MAX_COL = 3;
	private final int SAVE_MAX_ROW = 10;

	/* DefaultのConstructor */
	public TangoPanelTable() {
		HorizontalPanel buttons = new HorizontalPanel();
		panel.add(buttons);
		panel.setCellHorizontalAlignment(buttons, VerticalPanel.ALIGN_CENTER);
		flexTable = new FlexTable();
		flexTable.setSize("100%", "100%");
		panel.add(flexTable);
		flexTable.addStyleName("WordPanelTable-table");
		flexTable.addStyleName("WordPanelTable-panel");

		/* セルがクリックされた時の振る舞い */
		flexTable.addTableListener(new TableListener() {
			public void onCellClicked(SourcesTableEvents sender, int row,
					int cell) {
				System.out.println("row=" + row + ", col=" + cell);
			}
		});

		flexTable.setWidget(0, 0, new TangoPanel());
		initWidget(panel);
	}

	public TangoPanelTable(String str) {
		HorizontalPanel buttons = new HorizontalPanel();
		panel.add(buttons);
		panel.setCellHorizontalAlignment(buttons, VerticalPanel.ALIGN_CENTER);
		flexTable = new FlexTable();
		flexTable.setSize("100%", "100%");
		panel.add(flexTable);
		flexTable.addStyleName("WordPanelTable-table");
		flexTable.addStyleName("WordPanelTable-panel");

		/* セルがクリックされた時の振る舞い */
		flexTable.addTableListener(new TableListener() {
			public void onCellClicked(SourcesTableEvents sender, int row,
					int cell) {
				System.out.println("row=" + row + ", col=" + cell);
			}
		});
	}

	/* flexTableの指定した箇所にWord Panelを保存する */
	public void addWordPanel(int row, int col, TangoPanel wpanel) {
		System.out.println("func:saveWordPanel");
		System.out.println("row=" + row + ", col=" + col);
		flexTable.setWidget(row, col, wpanel);
	}

	/* saveWordTableの最上段の右端にWord Panelを保存する */
	public void addWordPanel(TangoPanel wpanel) {
		int row, col;
		boolean searchFlag = false;
		row = col = 0;
		// 削除されたセルの位置をサーチする
		for (row = 0; row < SAVE_MAX_ROW; row++) {
			for (col = 0; col < SAVE_MAX_COL; col++) {
				/* セルが空白ではない場合 */
				if (this.flexTable.isCellPresent(row, col) == false
						|| this.flexTable.getHTML(row, col).equals("") == true) {
					searchFlag = true;
					break;
				}
			}
			if (searchFlag == true)
				break;
		}

		System.out.println("row=" + row + ", col=" + col);
		this.flexTable.setWidget(row, col, wpanel);
	}

	/* 行列(row×col)のflex tableを再生成する */
	public void changeReflect(int row, int col) {
		panel.remove(flexTable);
		flexTable = new FlexTable();
		panel.add(flexTable);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				this.flexTable.setWidget(i, j, new TangoPanel());
			}
		}
		flexTable.setSize("100%", "100%");
	}

	/* flex tableの中身を消去する */
	public void clearWordPanel() {
		this.flexTable.clear();
	}

	public ArrayList<PickupTango> deleteRepeats(ArrayList<PickupTango> tangoList) {
		for (int i = 0; i < tangoList.size() - 1; i++) {
			for (int j = i + 1; j < tangoList.size(); j++) {
				if (tangoList.get(i).getName().equals(
						tangoList.get(j).getName())) {
					tangoList.remove(j);
					j--;
				}
			}
		}

		return tangoList;
	}

	public FlexTable getFlexTable() {
		return flexTable;
	}

	/* 空白セルを左詰めする。 */
	public void infillSpaces() {
		int delRow, delCol;
		int endRow, endCol;
		boolean searchFlag;
		boolean endFlag;

		searchFlag = endFlag = false;
		delRow = delCol = 0;
		endRow = endCol = 0;

		// 削除されたセルの位置をサーチする
		for (int i = 0; i < SAVE_MAX_ROW; i++) {
			for (int j = 0; j < SAVE_MAX_COL; j++) {
				/* セルが空白だった場合 */
				if (this.flexTable.getHTML(i, j).equals("") == true) {
					delRow = i;
					delCol = j;
					searchFlag = true;
					break;
				}
			}
			if (searchFlag == true) {
				break;
			}
		}

		System.out.println("DeletedCell=(" + delRow + ", " + delCol + ")");

		searchFlag = false;
		// 最後尾のセルの位置をサーチする
		for (int i = delRow; i < SAVE_MAX_ROW; i++) {
			for (int j = 0; j < SAVE_MAX_COL; j++) {
				/* 削除した文字から走査する */
				if (i == delRow && j < delCol)
					j = delCol;

				/* セルが空白な場合 */
				if (this.flexTable.isCellPresent(i, j) == false) {
					if (j == 0) {
						if (i == 0) {
							endRow = 0;
							endCol = 0;
						} else {
							endRow = i - 1;
							endCol = SAVE_MAX_COL - 1;
						}
					} else {
						endRow = i;
						endCol = j - 1;
					}
					searchFlag = true;
					break;
				}
				/* テーブルが埋め尽くされている場合 */
				else if (i == SAVE_MAX_ROW - 1 && j == SAVE_MAX_COL - 1) {
					endRow = i;
					endCol = j;
					searchFlag = true;
					break;
				}
			}
			if (searchFlag == true) {
				break;
			}
		}

		System.out.println("EndCell=(" + endRow + ", " + endCol + ")");

		// この時点で(row,col)==削除されたセル

		// 左詰めする
		for (int i = delRow; i < SAVE_MAX_ROW; i++) {
			for (int j = 0; j < SAVE_MAX_COL; j++) {
				/* 削除した文字から走査する */
				if (i == delRow && j < delCol)
					j = delCol;
				/* flextableの最後まで走査したらループを脱出 */
				if (i == endRow && j == endCol) {
					endFlag = true;
					break;
				}

				if (j != SAVE_MAX_COL - 1) {
					this.flexTable.setWidget(i, j, this.flexTable.getWidget(i,
							j + 1));
					System.out
							.println("Moved cell from:(" + i + ", " + j + ")");
				} else /* if (i != SAVE_MAX_ROW-1) */{
					this.flexTable.setWidget(i, j, this.flexTable.getWidget(
							i + 1, 0));
				}
			}
			if (endFlag == true) {
				break;
			}
		}

		// 最後尾のWidgetを削除する。
		this.flexTable.removeCell(endRow, endCol);
		System.out.println(this.flexTable.isCellPresent(1, 1));

	}

	public boolean isFull() {
		return this.flexTable.isCellPresent(SAVE_MAX_ROW - 1, SAVE_MAX_COL - 1);
	}

	/* tango!ボタンを押したときに、必要に応じて単語をシャッフルする */
	public void shuffle(ArrayList<PickupTango> pickupTangoList) {
		int row = flexTable.getRowCount();	// 行数
        int col = flexTable.getCellCount(row-1); // 列数
        int listSize = pickupTangoList.size();
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
        			flexTable.setWidget(i, j, TangoPanel.tangoPanel(pickupTangoList.get(randomNumber)));
        			// System.out.println("i="+i+", j="+j);
        		}
        	}
        
        /* 行数×列数  <= 取ってきた単語数の方 */
        /* 取ってきた単語が既にランダムなので、そのまま表示 */
        }else{
        	// Window.alert("row="+row +", col="+col + ", listSize=" + listSize + "\n row * col <= listSize");
        	for(int i=0; i<row ; i++){
        		for(int j=0; j<col ; j++){
        			flexTable.setWidget(i, j, TangoPanel.tangoPanel(pickupTangoList.get(count)));
        			// System.out.println("i="+i+", j="+j);
        			count++;
        		}
        	}
        }
	}
	
	/**
	 * 呼び出したTangoPanelTableに格納されている各セルの要素を
	 * ArrayList<PickupTango>の形式として返す。
	 * また, セルが一つも存在しない場合は空のArrayList<PickupTango>を返す。
	 * @return ArrayList<PickupTango>
	 */
	public ArrayList<PickupTango> toArray() {
		ArrayList<PickupTango> pickupTangoList = new ArrayList<PickupTango>();
		boolean escapeFlag = false;

		if (this.flexTable.isCellPresent(0, 0)==false || this.flexTable.getHTML(0, 0).equals("") == true) {
			return pickupTangoList;
		}

		for (int i = 0; i < SAVE_MAX_ROW; i++) {
			for (int j = 0; j < SAVE_MAX_COL; j++) {
				if (this.flexTable.isCellPresent(i, j)) {
					pickupTangoList.add(((TangoPanel) this.flexTable.getWidget(i, j))
							.getPickupTango());
				} else {
					escapeFlag = true;
					break;
				}
			}
			if (escapeFlag)
				break;
		}
		return pickupTangoList;
	}
}