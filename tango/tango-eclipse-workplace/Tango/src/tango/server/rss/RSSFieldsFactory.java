package tango.server.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tango.client.rss.NewsFields;
import tango.client.rss.RSSFields;

/**
 * <p>
 * RSS形式のニュースチャンネルからニュースを生成するクラスです。
 * </p>
 * <p>
 * ニュースにはRSS1.0（RDF形式）とRSS2.0（RSS形式）が混在しているため、それぞれによって処理が分かれています。<br>
 * 現時点では未実装ですが、将来的にはATOMフィードも対応する予定です。
 * </p>
 * <p>
 * ニュースの内容は、{@link RSSFields}クラスとして保持されます。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public class RSSFieldsFactory extends NewsFieldsFactory {
	private static final String PROXY_ADDRESS = "www-proxy.waseda.jp";
	private static final int PROXY_PORT = 8080;

	protected RSSFieldsFactory() {

	}

	/**
	 * <p>
	 * RSSフィードに接続し、InputStreamを取得します。
	 * </p>
	 * 
	 * @param directUrl
	 *            プロトコル部分を含むURL
	 * @return URLを開いて得られたInputStream
	 * @throws IOException
	 */
	private InputStream accessRSSFeeds(String directUrl) throws IOException {

		URL Url;
		if (usesProxy())
			Url = new URL("http", PROXY_ADDRESS, PROXY_PORT, directUrl);
		else
			Url = new URL(directUrl);

		URLConnection cn = Url.openConnection();
		InputStream is = cn.getInputStream();

		return is;
	}

	/**
	 * <p>
	 * 与えられたチャンネルURLとプロトコルから、それらを結合したURLを生成します。
	 * </p>
	 * <p>
	 * .propertiesファイルではプロトコルをつけるかは強要していないので、プロトコルがついていない場合があります。<br>
	 * そういった場合はこのメソッドでhttpプロトコルを追加します。
	 * </p>
	 * 
	 * @param protocol
	 *            チャンネルURLから生成したプロトコル
	 * @param link
	 *            チャンネルURL
	 * @return プロトコル付きのURL
	 */
	private String getDirectUrl(String protocol, String link) {
		if (protocol == null)
			protocol = "http";
		if (protocol == "")
			protocol = "http";

		String directUrl = new String(protocol + "://" + link);

		return directUrl;
	}

	/**
	 * <p>
	 * RDF形式のRSSファイルをXMLパーサでパースします。
	 * </p>
	 * 
	 * @param root
	 *            rdf要素。子ノードにitemを持つ
	 * @param channelLink
	 *            チャンネルのURL
	 * @param channelTitle
	 *            チャンネルのタイトル
	 * @return ニュースのコレクション
	 */
	private List<NewsFields> newRDFNewsFieldsCollection(Element root,
			String channelLink, String channelTitle) {
		List<NewsFields> collection = new ArrayList<NewsFields>();

		// list = root.getElementsByTagName("channel");
		// root = (Element) list.item(0);
		NodeList list = root.getElementsByTagName("item");

		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			// 各フィールドを抽出
			NodeList tList = element.getElementsByTagName("title");
			NodeList lList = element.getElementsByTagName("link");
			NodeList cList = element.getElementsByTagName("category");
			NodeList dList = element.getElementsByTagName("dc:Date");
			NodeList desList = element.getElementsByTagName("description");
			// 各Resultノードの最初のノードを取得
			Element tElement = (Element) tList.item(0);
			Element lElement = (Element) lList.item(0);
			Element[] cElement = new Element[cList.getLength()];
			Element dElement = (Element) dList.item(0);
			Element desElement = (Element) desList.item(0);
			// 各フィールドの値を代入する変数
			String title = null;
			String link = null;
			String[] categories = null;
			if (cList.getLength() > 0)
				categories = new String[cList.getLength()];
			String dcDate = null;
			String description = null;
			// 子Elementを持つノードのみ値を取得
			if (tElement != null)
				title = tElement.getFirstChild().getNodeValue();
			if (lElement != null)
				link = lElement.getFirstChild().getNodeValue();
			for (int j = 0; j < cList.getLength(); j++) {
				cElement[j] = (Element) cList.item(j);
				if (cElement[j] != null)
					categories[j] = cElement[j].getFirstChild().getNodeValue();
			}
			if (dElement != null)
				dcDate = dElement.getFirstChild().getNodeValue();
			if (desElement != null)
				description = desElement.getFirstChild().getNodeValue();

			collection.add(new RSSFields(title, link, categories, null, dcDate,
					description, channelLink, channelTitle));
		}
		return collection;
	}

	/**
	 * <p>
	 * RSS形式のRSSファイルをXMLパーサでパースします。
	 * </p>
	 * 
	 * @param root
	 *            rss要素。子ノードにchannelを持つ
	 * @param channelLink
	 *            チャンネルのURL
	 * @param channelTitle
	 *            チャンネルのタイトル
	 * @return ニュースのコレクション
	 */
	private List<NewsFields> newRSSNewsFieldsCollection(Element root,
			String channelLink, String channelTitle) {
		List<NewsFields> collection = new ArrayList<NewsFields>();

		NodeList list = root.getElementsByTagName("channel");
		root = (Element) list.item(0);
		list = root.getElementsByTagName("item");

		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			// 各フィールドを抽出
			NodeList tList = element.getElementsByTagName("title");
			NodeList lList = element.getElementsByTagName("link");
			NodeList cList = element.getElementsByTagName("category");
			NodeList dList = element.getElementsByTagName("pubDate");
			NodeList desList = element.getElementsByTagName("description");
			// 各Resultノードの最初のノードを取得
			Element tElement = (Element) tList.item(0);
			Element lElement = (Element) lList.item(0);
			Element[] cElement = new Element[cList.getLength()];
			Element dElement = (Element) dList.item(0);
			Element desElement = (Element) desList.item(0);
			// 各フィールドの値を代入する変数
			String title = null;
			String link = null;
			String[] categories = null;
			if (cList.getLength() > 0)
				categories = new String[cList.getLength()];
			String pubDate = null;
			String description = null;
			// 子Elementを持つノードのみ値を取得
			if (tElement != null)
				title = tElement.getFirstChild().getNodeValue();
			if (lElement != null)
				link = lElement.getFirstChild().getNodeValue();
			for (int j = 0; j < cList.getLength(); j++) {
				cElement[j] = (Element) cList.item(j);
				if (cElement[j] != null)
					categories[j] = cElement[j].getFirstChild().getNodeValue();
			}
			if (dElement != null)
				pubDate = dElement.getFirstChild().getNodeValue();
			if (desElement != null)
				description = desElement.getFirstChild().getNodeValue();

			collection.add(new RSSFields(title, link, categories, pubDate,
					null, description, channelLink, channelTitle));
		}
		return collection;
	}

	/**
	 * <p>
	 * 持っているチャンネルURLのコレクションから、全てのニュースを取得します。
	 * </p>
	 * 
	 * @return 全てのニュースのコレクション
	 * @throws IOException
	 */
	@Override
	protected List<NewsFields> newAllNewsFieldsCollection() throws IOException {
		
		List<NewsFields> collection = new ArrayList<NewsFields>();

		for (int i = 0; i < getChannelLinks().size(); i++)
			if (getUseThisChannels().get(i)) {
				collection.addAll(newNewsFieldsCollection(getChannelLinks()
						.get(i), getProtocols().get(i), getChannelTitles().get(
						i)));
				excludeKeyword(collection, i);
			}
		return collection;
	}

	/**
	 * <p>
	 * あるチャンネルURLから、そのチャンネル内の全てのニュースを取得します。
	 * </p>
	 * <p>
	 * このメソッドでは、URLを開いて得られたxmlファイルををXMLパーサにかけて要素を取り出します。<br>
	 * 先頭のツリーがrssかrdfかによってRSSのバージョンが判別できるため、そのElementをさらにパースします。
	 * </p>
	 * 
	 * @param channelLink
	 *            チャンネルのURL
	 * @param protocol
	 *            チャンネルのURLから生成されたプロトコル
	 * @param channelTitle
	 *            チャンネルのタイトル
	 * @return あるチャンネルの全てのニュースを含むコレクション
	 * @throws IOException
	 */
	@Override
	protected List<NewsFields> newNewsFieldsCollection(String channelLink,
			String protocol, String channelTitle) throws IOException {

		channelLink = getDirectUrl(protocol, channelLink);

		InputStream is = accessRSSFeeds(channelLink);

		List<NewsFields> newCollection = null;

		try {
			// XMLパーサにより検索結果を解析
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document d = builder.parse(is);
			// ルートノード
			Element root = d.getDocumentElement();

			String rssType = root.getTagName();

			if (rssType.equals("rss")) {
				newCollection = newRSSNewsFieldsCollection(root, channelLink,
						channelTitle);
			} else if (rssType.equals("rdf:RDF")) {
				newCollection = newRDFNewsFieldsCollection(root, channelLink,
						channelTitle);
			} else {
				newCollection = null;
			}

		} catch (IOException e) {
			System.out.println(e);
		} catch (ParserConfigurationException e) {
			System.out.println(e);
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return newCollection;
	}
}