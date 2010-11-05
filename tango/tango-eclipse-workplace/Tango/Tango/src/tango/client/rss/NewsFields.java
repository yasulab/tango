package tango.client.rss;

/**
 * <p>
 * RSSなどから取得されたニュースを保持するクラスです。
 * </p>
 * <p>
 * RSSニュースを保持するRSSFieldsやYahoo!トピックスのニュースを保持するTopicFieldsのラッパークラスとして利用します。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public interface NewsFields {
	// private String title;
	// private String excludedTitle;
	// private String link;
	// private String[] category;
	// private String date;
	// private String description;
	// private String channelLink;
	// private String channelTitle;

	/**
	 * <p>
	 * ニュースのカテゴリ名を返します。
	 * </p>
	 * 
	 * @return ニュースのカテゴリ名
	 */
	String[] getCategories();

	/**
	 * <p>
	 * チャンネルのURLを返します。
	 * </p>
	 * 
	 * @return チャンネルのURL
	 */
	String getChannelLink();

	/**
	 * <p>
	 * .propertiesで指定したチャンネルのタイトルを返します。
	 * </p>
	 * 
	 * @return チャンネルのタイトル
	 */
	String getChannelTitle();

	/**
	 * <p>
	 * ニュースの更新日時を返します。
	 * </p>
	 * 
	 * @return ニュースの更新日時
	 */
	String getDate();

	/**
	 * <p>
	 * ニュースに関する記述を返します。
	 * </p>
	 * 
	 * @return ニュースに関する記述
	 */
	String getDescription();

	/**
	 * <p>
	 * .propertiesで指定したキーワードを除外したタイトルを返します。
	 * </p>
	 * 
	 * @return 除外キーワードを含まないタイトル
	 */
	String getExcludedTitle();

	/**
	 * <p>
	 * ニュースのリンクを返します。
	 * </p>
	 * 
	 * @return ニュースのリンク
	 */
	String getLink();

	/**
	 * <p>
	 * ニュースのタイトルを返します。
	 * </p>
	 * 
	 * @return ニュースのタイトル
	 */
	String getTitle();

	/**
	 * <p>
	 * .propertiesで指定したキーワードを除外したタイトルをセットします。
	 * </p>
	 * 
	 * @param title
	 *            ニュースのタイトル
	 */
	void setExcludedTitle(String title);
}
