package tango.client.rss;

import java.util.Properties;
import java.util.Set;

import tango.server.rss.NewsFieldsFactory;
import tango.server.rss.RankingFieldsFactory;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * <p>
 * ニュースデータとランキングデータを保持するクラスです。
 * </p>
 * <p>
 * 外部的には、生成されたランキングデータを提供します。<br>
 * 内部的には、このクラスからプロパティを読み込み、{@link NewsFieldsFactory}クラスを呼び出してニュースを取得し、
 * {@link RankingFieldsFactory}クラスを呼び出してランキングを生成します。
 * </p>
 * 
 * @author Ukit Grey
 * 
 */
@RemoteServiceRelativePath("newsRanking")
public interface NewsRanking extends RemoteService {

	/**
	 * <p>
	 * {@link RankingFields}クラスのコレクションを返します。
	 * </p>
	 * 
	 * @return {@link RankingFields}クラスのコレクション
	 * 
	 */
	public Set<RankingFields> getRankingFieldsCollection();

	/**
	 * <p>
	 * デフォルトのプロパティを使ってこのインスタンスを初期化します。
	 * </p>
	 * <p>
	 * デフォルトでは、JavaVMのルートディレクトリからの相対パスproperties\rss.propertiesにあるファイルを利用します。<br>
	 * 引数でプロパティを使用しない設定を行った場合は、Yahoo!トピックスを全て取得します。
	 * </p>
	 * <p>
	 * 一度インスタンスを作成した後に、ニュースの再取得を行う場合に使用します。<br>
	 * また、GWTなどデフォルトコンストラクタでしかインスタンスを生成できない際に使用します。
	 * </p>
	 * 
	 * @since 1.2
	 */
	public void initialize();

	/**
	 * <p>
	 * 引数に指定されたpathを使ってこのインスタンスを初期化します。
	 * </p>
	 * <p>
	 * pathの末尾が.propertiesであればpathにあるプロパティを使い{@link #initialize(Properties)}
	 * によって初期化を行います。<br>
	 * pathがそれ以外の文字列であればpathをURLとして扱いプロパティファイルを生成し、
	 * {@link #initialize(Properties)}によって初期化を行います。
	 * </p>
	 * 
	 * @param path
	 *            末尾が.propertiesであればプロパティのパス、それ以外であればURL
	 * @since 1.2
	 */
	public Set<RankingFields> initialize(String path);
}