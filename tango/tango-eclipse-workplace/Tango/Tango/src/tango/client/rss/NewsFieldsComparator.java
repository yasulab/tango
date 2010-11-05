package tango.client.rss;

import java.util.Comparator;

/**
 * <p>
 * NewsFieldsインターフェースをTreeSetに挿入する際の比較規則を規定します。
 * </p>
 * <p>
 * 比較順序は更新日時が新しいほうが大きく、等しい場合にはタイトルの辞書順、さらに等しい場合はURLの辞書順になります。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
public class NewsFieldsComparator implements Comparator<NewsFields> {

	/**
	 * <p>
	 * 順序付けのために 2 つの引数を比較します。
	 * </p>
	 * 
	 * @param nf1
	 *            比較対象の最初のNewsFieldsクラス
	 * @param nf2
	 *            比較対象の2番目のNewsFieldsクラス
	 * @return 規定された順序付けにより、最初の引数が2番目の引数より小さい場合は負の整数、両方が等しい場合は0、
	 *         最初の引数が2番目の引数より大きい場合は正の整数
	 */
	public int compare(NewsFields nf1, NewsFields nf2) {
		if (nf2.getDate().equals(nf1.getDate()))
			if (nf1.getTitle().equals(nf2.getTitle()))
				if (nf1.getLink().equals(nf2.getLink()))
					return -1;
				else
					return nf1.getLink().compareTo(nf2.getLink());
			else
				return nf1.getTitle().compareTo(nf2.getTitle());
		else
			return nf2.getDate().compareTo(nf1.getDate());

	}
}
