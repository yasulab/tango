package tango.client.rss;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <p>
 * RSSニュースを保持するクラスです。
 * </p>
 * <p>
 * RSSFieldsFactoryクラスに渡されるRSSのURLから、RSSが保持するニュースが全て生成されます。<br>
 * ひとつひとつのニュース毎にRSSFieldsクラスが生成され、RankingFieldsクラスがその管理を行います。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public class RSSFields implements NewsFields, IsSerializable {
	private String[] categories;
	private String channelLink;
	private String channelTitle;
	private String dcDate;
	private String description;
	private String excludedTitle;
	private String link;
	private String pubDate;
	private String title;
	
	/**
	 * このクラスのデフォルトコンストラクタです。
	 */
	public RSSFields(){
		super();
	}

	/**
	 * <p>
	 * このクラスのコンストラクタです。
	 * </p>
	 * 
	 * @param title
	 *            ニュースのタイトル
	 * @param link
	 *            ニュースのURL
	 * @param categories
	 *            ニュースのカテゴリ
	 * @param pubDate
	 *            ニュースの更新日時（RSS形式の場合）
	 * @param dcDate
	 *            ニュースの更新日時（RDF形式の場合）
	 * @param description
	 *            ニュースに関する記述
	 * @param channelLink
	 *            チャンネルのURL
	 * @param channelTitle
	 *            チャンネルのタイトル
	 */
	public RSSFields(String title, String link, String[] categories,
			String pubDate, String dcDate, String description,
			String channelLink, String channelTitle) {
		this.title = title;
		this.link = link;
		this.categories = categories;
		this.pubDate = pubDate;
		this.dcDate = dcDate;
		this.description = description;
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
		if (categories == null) {
			String[] strArray = { "" };
			categories = strArray;
		}
		return categories;
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
	 * .propertiesで指定したチャンネルのタイトルを返します。
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
	 * <p>
	 * ニュースの形式がRSSかRDFかによって、使用するフィールド変数が変わります。<br>
	 * また、それぞれyyyy/mm/dd hh:mm:ssの形式に変換されます。
	 * </p>
	 * 
	 * @return ニュースの更新日時
	 */
	public String getDate() {
		if (pubDate != null) {
			return convertPubDate(pubDate);
		} else if (dcDate != null)
			return convertDcDate(dcDate);
		else {
			pubDate = "";
		}
		return pubDate;
	}

	/**
	 * <p>
	 * ニュースに関する記述を返します。
	 * </p>
	 * 
	 * @return ニュースに関する記述
	 */
	public String getDescription() {
		if (description == null)
			description = "";
		return description;
	}

	/**
	 * <p>
	 * .propertiesで指定したキーワードを除外したタイトルを返します。
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
		if (link == null)
			link = "";
		return link;
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
	 * .propertiesで指定したキーワードを除外したタイトルをセットします。
	 * </p>
	 * 
	 * @param title
	 *            ニュースのタイトル
	 */
	public void setExcludedTitle(String title) {
		this.excludedTitle = title;
	}

	/**
	 * <p>
	 * RDF形式で取得した更新日時をyyyy/mm/dd hh:mm:ssの形式に変換します。
	 * </p>
	 * 
	 * @param dcDate
	 *            RDF形式の更新日時
	 * @return ニュースの更新日時
	 */
	private String convertDcDate(String dcDate) {
		if (dcDate.length() != 0) {
			dcDate = dcDate.substring(0, 19);
			dcDate = dcDate.replaceAll("-", "/");
			dcDate = dcDate.replaceAll("T", " ");
			System.out.println("dcDate");
		}
		return dcDate;
	}

	/**
	 * <p>
	 * RSS形式の更新日時の月日表示を英字から数字に変換します。
	 * </p>
	 * 
	 * @param month
	 *            英字の月日表示
	 * @return 数字の月日表示
	 */
	private String convertMonth(String month) {
		if (month.equals("Jan"))
			return "1";
		else if (month.equals("Feb"))
			return "2";
		else if (month.equals("Mar"))
			return "3";
		else if (month.equals("Apr"))
			return "4";
		else if (month.equals("May"))
			return "5";
		else if (month.equals("Jun"))
			return "6";
		else if (month.equals("Jul"))
			return "7";
		else if (month.equals("Aug"))
			return "8";
		else if (month.equals("Sep"))
			return "9";
		else if (month.equals("Oct"))
			return "10";
		else if (month.equals("Nov"))
			return "11";
		else
			return "12";
	}

	/**
	 * <p>
	 * RSS形式で取得した更新日時をyyyy/mm/dd hh:mm:ssの形式に変換します。
	 * </p>
	 * 
	 * @param pubDate
	 *            RSS形式の更新日時
	 * @return ニュースの更新日時
	 */
	private String convertPubDate(String pubDate) {
		if (pubDate.length() != 0) {
			// pubDate = pubDate.substring(5);
			String[] str = pubDate.split(" ");
			pubDate = str[3] + "/" + convertMonth(str[2]) + "/" + str[1] + " "
					+ str[4];
		}
		return pubDate;
	}
}
