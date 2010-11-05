/**
 * 
 */
package tango.client.canvas;

import java.util.ArrayList;

import tango.client.share.MorphemeService;
import tango.client.share.MorphemeServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author yasukawa
 * This class is a main panel of "Word Tango!".
 */
public class CanvasTangoMain extends Composite{
	HorizontalPanel settingPanel;
	Button tangoButton;
	ClickListener cListener;
	DockPanel mainPanel;
	CanvasPanelTable cpTable;
	CanvasTableBox ctBox;
	ArrayList<CanvasPanel> cPanels;
	TextBox tBox = new TextBox();
	int maxPanel;

	public CanvasTangoMain(){
		
		/* set click listener*/
		cListener = new ClickListener() {
			public void onClick(Widget sender) {
				// プロキシオブジェクトを取得
				CanvasServiceAsync proxy = (CanvasServiceAsync) GWT.create(CanvasService.class);
				// サービスへのエントリポイントを設定
				ServiceDefTarget entrypoint = (ServiceDefTarget) proxy;
				String path = GWT.getModuleBaseURL();
				entrypoint.setServiceEntryPoint(path+"canvasService");

				// echobackサービスの結果への反応を定義
				AsyncCallback callback = new AsyncCallback() {
					public void onSuccess(Object result) {
						// results.addItem((String) result);
					}

					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						// results.addItem(caught.getMessage());
					}
				};
				
				// サービスの呼び出し
				proxy.canvasService(tBox.getText(), callback);
			}
		};
		
		/* Initialize */
		settingPanel = new HorizontalPanel();
		tangoButton = new Button("tango!", cListener);
		maxPanel = 20;
		mainPanel = new DockPanel();
		cpTable = new CanvasPanelTable();
		cPanels = new ArrayList<CanvasPanel>();
		ctBox = new CanvasTableBox(this, cpTable);
		
		for(int i=0;i<maxPanel;i++) cPanels.add(new CanvasPanel());

		/* style sheet */
		mainPanel.setStyleName("CanvasTango-main");
		
		// UIの構築
		mainPanel.add(settingPanel, DockPanel.NORTH);
		mainPanel.add(cpTable, DockPanel.CENTER);
		settingPanel.add(tangoButton);
		settingPanel.add(ctBox);
		settingPanel.add(tBox);
		cpTable.addHorizontalPanel(new CanvasPanel());
		
		// Setting Layout
		mainPanel.setCellVerticalAlignment(settingPanel, HorizontalPanel.ALIGN_MIDDLE);
		tangoButton.setStyleName("TangoButton");


		initWidget(mainPanel);
	}
}
