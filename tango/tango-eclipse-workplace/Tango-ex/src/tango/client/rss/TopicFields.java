package tango.client.rss;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <p>
 * Yahooトピックスのニュースを保持するクラスです。
 * </p>
 * <p>
 * TopicFieldsFactoryクラスに渡されるトピックスのURLから、トピックスが保持するニュースが全て生成されます。<br>
 * ひとつひとつのニュース毎にTopicFieldsクラスが生成され、RankingFieldsクラスがその管理を行います。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public class TopicFields implements NewsFields, IsSerializable {
	private String category;
	private String channelLink;
	private String channelTitle;
	private String datetime;
	private String excludedTitle;
	private String title;
	private String url;
	
	/**
	 * このクラスのデフォルトコンストラクタです。
	 */
	public TopicFields(){
		super();
	}

	/**
	 * このクラスのコンストラクタです。
	 * 
	 * @param datetime
	 *            ニュースの更新日時
	 * @param title
	 *            ニュースのタイトル
	 * @param category
	 *            ニュースのカテゴリ
	 * @param url
	 *            ニュースのURL
	 * @param channelLink
	 *            チャンネルのURL
	 * @param channelTitle
	 *            チャンネルのタイトル
	 */
	public TopicFields(String datetime, String title, String category,
			String url, String channelLink, String channelTitle) {
		this.datetime = datetime;
		this.title = title;
		this.category = category;
		this.url = url;
		this.channelLink = channelLink;
		this.channelTitle = channelTitle;
	}

	/**
	 * <p>
	 * ニュースのカテゴリ名を返します。
	 * </p>
	 * 
	 * @return ニュースのカテゴリ名
	 */
	public String[] getCategories() {
		if (category == null)
			category = "";
		String[] strArray = { category };
		return strArray;
	}

	/**
	 * <p>
	 * チャンネルのURLを返します。
	 * </p>
	 * 
	 * @return チャンネルのURL
	 */
	public String getChannelLink() {
		return channelLink;
	}

	/**
	 * <p>
	 * チャンネルのタイトルを返します。
	 * </p>
	 * 
	 * @return チャンネルのタイトル
	 */
	public String getChannelTitle() {
		return channelTitle;
	}

	/**
	 * <p>
	 * ニュースの更新日時を返します。
	 * </p>
	 * 
	 * @return ニュースの更新日時
	 */
	public String getDate() {
		if (datetime == null)
			datetime = "";
		return convertDatetime(datetime);
	}

	/**
	 * <p>
	 * ニュースに関する記述を返します。
	 * </p>
	 * 
	 * @return ニュースに関する記述
	 */
	public String getDescription() {
		return "";
	}

	/**
	 * <p>
	 * キーワードを除外したタイトルを返します。
	 * </p>
	 * <p>
	 * 現時点での仕様ではトピックスの除外キーワードは使用できません。<br>
	 * 渡されたタイトルがそのまま返されます。
	 * </p>
	 * 
	 * @return 除外キーワードを含まないタイトル
	 */
	public String getExcludedTitle() {
		if (excludedTitle == null)
			excludedTitle = "";
		return excludedTitle;
	}

	/**
	 * <p>
	 * ニュースのリンクを返します。
	 * </p>
	 * 
	 * @return ニュースのリンク
	 */
	public String getLink() {
		if (url == null)
			url = "";
		return url;
	}

	/**
	 * <p>
	 * ニュースのタイトルを返します。
	 * </p>
	 * 
	 * @return ニュースのタイトル
	 */
	public String getTitle() {
		if (title == null)
			title = "";
		return title;
	}

	/**
	 * <p>
	 * たキーワードを除外したタイトルをセットします。
	 * </p>
	 * <p>
	 * 現時点での仕様ではトピックスの除外キーワードは使用できません。<br>
	 * 
	 * @param title
	 *            ニュースのタイトル
	 */
	public void setExcludedTitle(String title) {
		this.excludedTitle = title;
	}

	/**
	 * <p>
	 * Yahoo!トピックス形式で取得した更新日時をyyyy/mm/dd hh:mm:ssの形式に変換します。
	 * </p>
	 * <p>
	 * ※未実装
	 * </p>
	 * 
	 * @param datetime
	 *            Yahoo!トピックス形式の更新日時
	 * @return ニュースの更新日時
	 */
	private String convertDatetime(String datetime) {
		return datetime;
	}
}