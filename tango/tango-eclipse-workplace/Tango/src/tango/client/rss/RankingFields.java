package tango.client.rss;

import java.util.HashSet;
import java.util.Set;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * <p>
 * ランキングの順位やキーワードなどを保持するクラスです。
 * </p>
 * <p>
 * このクラスには、あるひとつのキーワードに関する情報が保持されています。<br>
 * キーワードとは、全ニュース中に含まれる名詞のいずれかのことです。このクラスのフィールドには、
 * キーワードが全ニュース中のうちで出現した回数、キーワードが含まれるニュースの情報、 全キーワード中の順位が含まれます。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public class RankingFields implements IsSerializable{
	private int count;
	private String keyword;
	/**
	 * 要素がNewsFieldsのセット
	 */
	private Set<NewsFields> newsFieldsCollection;
	private int rank;

	public RankingFields(){}
	
	/**
	 * <p>
	 * このクラスのコンストラクタです。
	 * </p>
	 * 
	 * @param count
	 *            キーワードの頻出回数
	 * @param keyword
	 *            キーワード
	 */
	public RankingFields(int count, String keyword) {
		this.count = count;
		this.keyword = keyword;
//		this.newsFieldsCollection = new TreeSet<NewsFields>(
//				new NewsFieldsComparator());

		this.newsFieldsCollection = new HashSet<NewsFields>();
	}

	/**
	 * <p>
	 * このクラスが持つニュースのコレクションにニュースを追加します。
	 * </p>
	 * 
	 * @param news
	 *            このインスタンスのキーワードが含まれるニュース
	 */
	public void addNewsFields(NewsFields news) {
		newsFieldsCollection.add(news);
	}

	/**
	 * <p>
	 * 同じキーワードのRankingFieldsが存在しないかチェックし、存在した場合には統合します。
	 * </p>
	 * 
	 * @param collection
	 *            これまでのRankingFieldsのコレクション
	 * @return このクラス（呼び出し先でこのクラスをコレクションに追加するため）
	 */
	public RankingFields combineRankingFields(Set<RankingFields> collection) {
		boolean flag = false;
		RankingFields fields = null;

		for (RankingFields rankingFields1 : collection) {
			if (rankingFields1.getKeyword().equals(this.getKeyword())) {
				flag = true;
				fields = rankingFields1;
				this.count = rankingFields1.getCount() + this.getCount();
			}
		}

		if (flag)
			collection.remove(fields);

		return this;
	}

	/**
	 * <p>
	 * キーワードの頻出回数を返します。
	 * </p>
	 * 
	 * @return キーワードの頻出回数
	 */
	public int getCount() {
		return count;
	}

	/**
	 * <p>
	 * キーワードを返します。
	 * </p>
	 * 
	 * @return キーワード
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * <p>
	 * このキーワードが含まれるニュースのコレクションを返します。
	 * </p>
	 * 
	 * @return このキーワードが含まれるニュースのコレクション
	 */
	public Set<NewsFields> getNewsFieldsCollection() {
		return newsFieldsCollection;
	}

	/**
	 * <p>
	 * キーワードの順位を返します。
	 * </p>
	 * 
	 * @return キーワードの順位
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * <p>
	 * キーワードが数字のみで構成されたキーワードでないかチェックします。
	 * </p>
	 * <p>
	 * キーワードが数字だけで構成される場合が頻出するため、そのようなキーワードは除去されます。
	 * 
	 * @return 数字で構成されていれば正、それ以外なら負
	 */
	public boolean isNumberKeyword() {
		boolean matches = false;

		matches = keyword.matches("[0-9\\.,]+");
		if (!matches)
			matches = keyword.matches("[０-９]+");
		return matches;
	}

	/**
	 * <p>
	 * キーワードの順位をセットします。
	 * </p>
	 * 
	 * @param rank
	 *            キーワードの順位
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
}