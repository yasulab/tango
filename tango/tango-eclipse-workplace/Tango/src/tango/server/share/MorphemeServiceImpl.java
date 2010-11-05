package tango.server.share;

import java.util.ArrayList;

import tango.server.api.YahooWebService;
import tango.client.share.MorphemeService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author yasukawa
 * 文字列を形態素解析し、その結果をArrayList<String>として返す。
 */
public class MorphemeServiceImpl extends RemoteServiceServlet implements MorphemeService {
	private static final long serialVersionUID = 1L;
	String appid;
	public String morphemeService(String msg) throws Exception {
		ArrayList<String>[] keitai = YahooWebService.getWordCategory(msg);
		// 検索結果を出力
		for(int i = 0; i < keitai[0].size(); i++){
			System.out.println("[読み方]" + keitai[0].get(i) + " [品詞]" + keitai[1].get(i));
		} 
		return msg;
	}
}
