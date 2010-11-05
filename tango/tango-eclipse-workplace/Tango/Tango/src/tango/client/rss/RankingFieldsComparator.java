package tango.client.rss;

import java.util.Comparator;

/**
 * <p>
 * RankingFieldsクラスをTreeSetに挿入する際の比較規則を規定します。
 * </p>
 * <p>
 * 比較規則は頻出回数が多いほうが大きく、等しい場合はキーワードの文字数順、さらに等しい場合はキーワードの辞書順になります。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public class RankingFieldsComparator implements Comparator<RankingFields> {

	/**
	 * <p>
	 * 順序付けのために 2 つの引数を比較します。
	 * </p>
	 * 
	 * @param rf1
	 *            比較対象の最初のRankingFieldsクラス
	 * @param rf2
	 *            比較対象の2番目のRankingFieldsクラス
	 * @return 規定された順序付けにより、最初の引数が2番目の引数より小さい場合は負の整数、両方が等しい場合は0、
	 *         最初の引数が2番目の引数より大きい場合は正の整数
	 */
	public int compare(RankingFields rf1, RankingFields rf2) {
		if (rf1.getCount() == rf2.getCount())
			if (rf2.getKeyword().length() == rf1.getKeyword().length())
				return rf1.getKeyword().compareTo(rf2.getKeyword());
			else
				return rf2.getKeyword().length() - rf1.getKeyword().length();
		else
			return rf2.getCount() - rf1.getCount();
	}

}