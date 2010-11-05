package tango.server.canvas;

import java.util.ArrayList;

import tango.client.canvas.CanvasService;
import tango.server.api.YahooWebService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CanvasServiceImpl extends RemoteServiceServlet implements CanvasService {
	private static final long serialVersionUID = 1L;
	String appid;
	public String canvasService(String msg) throws Exception {
		ArrayList<String>[] keitai = YahooWebService.getWordCategory(msg);
		// 検索結果を出力
		for(int i = 0; i < keitai[0].size(); i++){
			System.out.println("[読み方]" + keitai[0].get(i) + " [品詞]" + keitai[1].get(i));
		} 
		return msg;
	}
}
