package com.mamezou.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * Example - AP01-02 
 */
public class Gwt12FeatureExample implements EntryPoint {

	public static final String DEFAULT_URL = "http://googlewebtoolkit.blogspot.com/";
	public static final int STATUS_OK = 200;

	private TextBox url;
	private Button doGetButton;
	private Button clearButton;
	private HTML contents;

	void doGetContents(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable e) {
					Window.alert(e.getMessage());
				}
				public void onResponseReceived(Request request,
						Response response) {
					if(response.getStatusCode()==STATUS_OK)
						contents.setHTML(response.getText());
					else
						Window.alert("Error has occurred / STATUS:" + response.getStatusText());
				}
			});
		} catch (RequestException e) {
			Window.alert(e.getMessage());
		}
	}
	void clear() {
		contents.setHTML(null);
		url.setText(DEFAULT_URL);
	}
	public void onModuleLoad() {
		contents = new HTML();
		url = new TextBox();
		url.setWidth("400");
		clear();
		doGetButton = new Button("DO GET", new ClickListener() {
			public void onClick(Widget sender) {
				doGetContents(url.getText());
			}
		});
		clearButton = new Button("CLEAR", new ClickListener() {
			public void onClick(Widget sender) {
				clear();
			}
		});
		TreeItem demo = new TreeItem("Demo");
		demo.addItem(url);
		demo.addItem(doGetButton);
		demo.addItem(clearButton);
		demo.addItem(contents);
		Tree tree = new Tree();
		tree.addItem(demo);
		RootPanel.get().add(tree);
	}
}
