package tango.server.rss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import tango.client.rss.NewsFields;

/**
 * <p>
 * ニュースの形式に合わせてそれぞれのファクトリクラスを呼び出すためのabstractファクトリクラスです。
 * </p>
 * <p>
 * また、各ファクトリクラスに共通する処理はこのクラス内で記述されています。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public abstract class NewsFieldsFactory {

	/**
	 * <p>
	 * このクラスの実質的なコンストラクタです。
	 * </p>
	 * <p>
	 * パラメータとして渡されるnewsTypeによりニュースの形式を判別し、各々のファクトリクラスのコンストラクタを呼び出します。
	 * </p>
	 * 
	 * @param useProxy
	 *            プロキシを利用するかどうか
	 * @param newsType
	 *            ニュースの形式
	 * @param channelTitles
	 *            チャンネルのタイトルのコレクション
	 * @param channelLinks
	 *            チャンネルのURLのコレクション
	 * @param useThisChannel
	 *            このチャンネルを利用するかどうかのコレクション
	 * @param excludesKeyword
	 *            除外するキーワードのコレクション
	 * @return 指定されたニュース形式の初期化されたインスタンス
	 */
	public static NewsFieldsFactory newInstance(boolean useProxy,
			String newsType, List<String> channelTitles,
			List<String> channelLinks, List<Boolean> useThisChannel,
			List<String> excludesKeyword) {

		NewsFieldsFactory factory;
		if (newsType.equals("rss"))
			factory = new RSSFieldsFactory();
		else if (newsType.equals("topics"))
			factory = new TopicFieldsFactory();
		else if (newsType.equals("help")) {
			System.out.println("help!");
			factory = null;
		} else
			factory = null;

		if (factory != null) {
			factory.usesProxy = useProxy;
			factory.channelTitles = channelTitles;
			factory.channelLinks = channelLinks;
			factory.usesThisChannel = useThisChannel;
			factory.excludeKeywords = excludesKeyword;

			factory.protocols = new ArrayList<String>();

			factory.excludeHTTPFromChannelLinks();
		}
		return factory;
	}

	private List<String> channelLinks;
	private List<String> channelTitles;
	private List<String> excludeKeywords;
	private List<String> protocols;
	private boolean usesProxy;
	private List<Boolean> usesThisChannel;

	protected NewsFieldsFactory() {

	}

	/**
	 * <p>
	 * チャンネルのリンクにhttp://やその他のプロトコル部分が含まれている場合には、それを除去します。
	 * </p>
	 * <p>
	 * このとき除去したプロトコル部分は、チャンネルのURLと分けてフィールドに保持しています。<br>
	 * これは、プロキシ利用の際様々なプロトコルに応じて処理を変更するためです。
	 * </p>
	 * 
	 */
	private void excludeHTTPFromChannelLinks() {
		int i = 0;
		String[] tmp = new String[2];
		for (String channelLink : channelLinks) {
			tmp = channelLink.split("://");
			if (tmp.length == 1) {
				channelLinks.set(i, tmp[0]);
				protocols.add("");
			} else {
				if (tmp[0].equals("ttp"))
					tmp[0] = "http";
				protocols.add(i, tmp[0]);
				channelLinks.set(i, tmp[1]);
			}
			i++;
		}
	}

	/**
	 * <p>
	 * ニュースのタイトル中に除外キーワードが含まれていればそれを除外します。
	 * </p>
	 * <p>
	 * このメソッドはサブファクトリクラスから呼び出されます。渡されたニュースとその位置情報からそのニュースに設定された除外キーワードを読み出し、
	 * 除外します。
	 * </p>
	 * <p>
	 * 全てのニュースに定型文が追加されているような場合、除外キーワードを設定することでランキングの正確性を増すことができます。
	 * </p>
	 * 
	 * @param collection
	 *            あるチャンネルURLから取得したニュース全てのコレクション
	 * @param index
	 *            チャンネルURLの位置を表すインデックス（excludeKeywordのコレクションの位置と等しい）
	 */
	protected void excludeKeyword(List<NewsFields> collection, int index) {
		try {
			for (NewsFields fields : collection)
				fields.setExcludedTitle(fields.getTitle().replaceAll(
						excludeKeywords.get(index), ""));
		} catch (PatternSyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * チャンネルのURLのコレクションを返します。
	 * </p>
	 * 
	 * @return チャンネルのURLのコレクション
	 */
	protected List<String> getChannelLinks() {
		return channelLinks;
	}

	/**
	 * <p>
	 * チャンネルのタイトルのコレクションを返します。
	 * </p>
	 * 
	 * @return チャンネルのタイトルのコレクション
	 */
	protected List<String> getChannelTitles() {
		return channelTitles;
	}

	/**
	 * <p>
	 * チャンネルの除外キーワードのコレクションを返します。
	 * </p>
	 * 
	 * @return チャンネルの除外キーワードのコレクション
	 */
	protected List<String> getExcludesKeywords() {
		return excludeKeywords;
	}

	/**
	 * <p>
	 * チャンネルのURLから生成したプロトコルのコレクションを返します。
	 * </p>
	 * 
	 * @return プロトコルのコレクション
	 */
	protected List<String> getProtocols() {
		return protocols;
	}

	/**
	 * <p>
	 * チャンネルを使うかどうかのコレクションを返します。
	 * </p>
	 * 
	 * @return チャンネルを使うかどうかのコレクション
	 */
	protected List<Boolean> getUseThisChannels() {
		return usesThisChannel;
	}

	/**
	 * <p>
	 * 持っているチャンネルURLのコレクションから、全てのニュースを取得します。
	 * </p>
	 * 
	 * @return 全てのニュースのコレクション
	 * @throws IOException
	 */
	protected abstract List<NewsFields> newAllNewsFieldsCollection()
			throws IOException;

	/**
	 * <p>
	 * あるチャンネルURLから、そのチャンネル内の全てのニュースを取得します。
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
	protected abstract List<NewsFields> newNewsFieldsCollection(
			String channelLink, String protocol, String channelTitle)
			throws IOException;

	/**
	 * プロキシを使うかどうかを返します。
	 * 
	 * @return プロキシを使うなら正、使わないなら負
	 */
	protected boolean usesProxy() {
		return usesProxy;
	}
}