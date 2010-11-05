package tango.server.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * YahooWebService
 * @author amonden
 * @create date 2007/06/25
 */
public class YahooWebService {

	private static String appid;//アプリケーションID

	public YahooWebService(String appid) {
		this.appid = appid;
	}

	/**
	 * テスト実行用メイン
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		ArrayList<String>[] keitai = getWordCategory("赤尾に、無茶ぶりしすぎ。");
		// 検索結果を出力
		for(int i = 0; i < keitai[0].size(); i++){
			System.out.println("[読み方]" + keitai[0].get(i) + " [品詞]" + keitai[1].get(i));
		} 
	}

	/*引数に入れられたStringに対して品詞をStringで返す。*/

	public static ArrayList<String>[] getWordCategory(String s) throws Exception {

		ArrayList<String>[] keitaiList = new ArrayList[2];
		ArrayList<String> yomikataList = new ArrayList<String>();
		ArrayList<String> hinshiList = new ArrayList<String>();

		String appid ="uxxN62mxg65rgQUDyEAxmncDrcnpCSvc0a3YkCB3UgLAulAfrOA3j3lDadwwRU7yBFh9HpR4";
		YahooWebService yahoo = new YahooWebService(appid);

		/*日本語形態素解析*/

		List<Map> list = yahoo.parseJp(s);
		for(Map row : list){
			yomikataList.add((String)(row.get("reading")));
			hinshiList.add((String)(row.get("pos")));
		}

		keitaiList[0] = yomikataList;
		keitaiList[1] = hinshiList;

		return keitaiList;
	}

	/**
	 * 日本語形態素解析
	 * @param centense 解析対象文字
	 * @return 検索結果
	 * @throws Exception 
	 */
	public List<Map> parseJp(String centense) throws Exception {
		String url = "http://api.jlp.yahoo.co.jp/MAService/V1/parse?appid=" + appid
		+ "&sentence=" + URLEncoder.encode(centense, "UTF-8") + "&results=ma";
		return queryByUri(url, "ResultSetma_resultword_list", "word");
	}

	/**
	 * URIをリクエストし検索結果を返す.
	 * @param url URI
	 * @param root_name ルート要素名
	 * @param item_name 検索結果要素名
	 * @return 検索結果
	 * @throws Exception
	 */

	private List<Map> queryByUri(String url, String root_name, String item_name) throws Exception {
		SAXParserFactory spfactory = SAXParserFactory.newInstance();
		SAXParser parser = spfactory.newSAXParser();
		XmlResponseSax sax = new XmlResponseSax(root_name, item_name);
		parser.parse(url, sax);
		return sax.getResultList();
	}

	/**
	 * 検索の結果を解析するパーサー
	 * @author amonden
	 * @create date 2007/06/25
	 */
	public class XmlResponseSax extends DefaultHandler {
		List<Map> list = new ArrayList<Map>();
		private String tag_path = "";
		private Map<String,String> map;
		private String itemKey;
		private String itemName;

		/**
		 * コンストラクタ
		 * @param root_name ルート要素名
		 * @param item_name 検索結果要素名
		 */
		public XmlResponseSax(String root_name, String item_name) {
			this.itemKey = root_name + item_name;
			this.itemName = item_name;
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (this.map != null) {
				String key = this.tag_path.substring(itemKey.length());
				this.map.put(key, new String(ch, start, length));
			}
		}

		/**
		 * 集計結果リストを返す
		 * @return
		 */
		public List<Map> getResultList() {
			return this.list;
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (itemName.equals(qName)) {
				this.map = new HashMap<String,String>();
			}
			this.tag_path += qName;
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (itemName.equals(qName)) {
				this.list.add(this.map);
				this.map = null;
			}
			this.tag_path = this.tag_path.substring(0, this.tag_path.length() - qName.length());
		}
	}
}