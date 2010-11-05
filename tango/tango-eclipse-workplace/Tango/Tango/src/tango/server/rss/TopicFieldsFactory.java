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
import tango.client.rss.TopicFields;

/**
 * <p>
 * Yahooトピックス形式のニュースチャンネルからニュースを生成するクラスです。
 * </p>
 * <p>
 * ニュースの内容は、{@link TopicFields}クラスとして保持されます。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public class TopicFieldsFactory extends NewsFieldsFactory {
	private static final String A_ID = "2.v4Bx6xg678G80n3FTOkE7lNP.Yh1xNFGmJG5ErmadBYLAK9kaiTI8kClDTZA--";
	private static final String NEWS_ADDRESS = "news.yahooapis.jp/NewsWebService/V1/Topics";
	private static final String PROXY_ADDRESS = "www-proxy.waseda.jp";
	private static final int PROXY_PORT = 8080;

	protected TopicFieldsFactory() {

	}

	/**
	 * <p>
	 * Yahooトピックスに接続し、InputStreamを取得します。
	 * </p>
	 * 
	 * @param directUrl
	 *            プロトコル部分を含むURL
	 * @return URLを開いて得られたInputStream
	 * @throws IOException
	 */
	private InputStream accessNewsWebService(String directUrl)
			throws IOException {

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
	 * 与えられたトピックスのカテゴリから、プロトコル付きのURLを生成します。
	 * </p>
	 * 
	 * @param link
	 *            トピックスのカテゴリ
	 * @return プロトコル付きのURL
	 */
	private String getDirectURL(String link) {

		String directUrl = new String("http://" + NEWS_ADDRESS + "?appid="
				+ A_ID);
		if (!link.equals("all"))
			directUrl += "&category=" + link;

		return directUrl;
	}

	/**
	 * <p>
	 * Yahooトピックス形式のInputStreamをXMLパーサでパースします。
	 * </p>
	 * 
	 * @param root
	 *            ルートノード。子ノードにResultを持つ。
	 * @param channelLink
	 *            チャンネルのURL
	 * @param channelTitle
	 *            チャンネルのタイトル
	 * @return ニュースのコレクション
	 */
	private List<NewsFields> newTopicNewsFieldsCollection(Element root,
			String channelLink, String channelTitle) {
		List<NewsFields> collection = new ArrayList<NewsFields>();

		// Resultノードのみ集める
		NodeList list = root.getElementsByTagName("Result");

		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			// 各フィールドを抽出
			NodeList dList = element.getElementsByTagName("datetime");
			NodeList tList = element.getElementsByTagName("title");
			NodeList cList = element.getElementsByTagName("category");
			NodeList uList = element.getElementsByTagName("url");
			// 各Resultノードの最初のノードを取得
			Element dElement = (Element) dList.item(0);
			Element tElement = (Element) tList.item(0);
			Element cElement = (Element) cList.item(0);
			Element uElement = (Element) uList.item(0);
			// 各値を取得
			String datetime = dElement.getFirstChild().getNodeValue();
			String title;
			try {
				title = tElement.getFirstChild().getNodeValue();
			} catch (NullPointerException e) {
				title = "";
			}
			String category = cElement.getFirstChild().getNodeValue();
			String url = uElement.getFirstChild().getNodeValue();

			collection.add(new TopicFields(datetime, title, category, url,
					channelLink, channelTitle));
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
	 * 実際には、チャンネルURLにはトピックスのカテゴリが入っています。この文字列をURLに組み込むことで、各カテゴリのトピックスを取得できます。
	 * </p>
	 * 
	 * @param channelLink
	 *            トピックスのカテゴリ
	 * @param protocol
	 *            何も入っていない
	 * @param channelTitle
	 *            チャンネルのタイトル
	 * @return あるチャンネルの全てのニュースを含むコレクション
	 * @throws IOException
	 */
	@Override
	protected List<NewsFields> newNewsFieldsCollection(String channelLink,
			String protocol, String channelTitle) throws IOException {

		channelLink = getDirectURL(channelLink);
		InputStream is = accessNewsWebService(channelLink);

		List<NewsFields> newCollection = null;

		try {
			// XMLパーサにより検索結果を解析
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document d = builder.parse(is);
			// ルートノード
			Element root = d.getDocumentElement();

			newCollection = newTopicNewsFieldsCollection(root, channelLink,
					channelTitle);

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