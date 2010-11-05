package tango.server;
/*
 * We must define Default Constructor of the Object
 * that we will send from Server to Client.
 */

import java.util.ArrayList;

import tango.client.PickupWord;
import tango.client.PickupWordResultSet;
import tango.client.PickupWordService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class PickupWordServiceImpl extends RemoteServiceServlet implements PickupWordService {
	private static final long serialVersionUID = 1L;
	
	public ArrayList<PickupWord> pickupWordService(PickupWordResultSet resultSet){
		setTestDataOnWeb(resultSet);
		return resultSet.pickupWords;
	}
	
	public void setTestDataOnWeb(PickupWordResultSet pwrSet){
		/* PickupWord(int id, String name, String howToRead, String description) */
		/* 実はIDはいらない・・・？ */
		///*
		pwrSet.pickupWords.add(new PickupWord(0, "上尾市", "あげおし", "埼玉県の市。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(1, "揚げ滓", "あげかす", "飛行機を上に向ける、かじのとり方。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(2, "上げ下げ", "あげさげ" , "上げることと、下げること。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(3, "上げ潮", "あげしお", "潮が満ちること。みちしお。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(4, "揚げ笊", "あげざる", "ゆでたそばをすくい上げる、ざる。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(5, "上げ膳", "あげぜん", "客に、食事の膳を出すこと。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(6, "上げ底", "あげぞこ", "中身を多く見せるため、そこを上げた容器。", "名詞")	);
		pwrSet.pickupWords.add(new PickupWord(7, "揚げ立て", "あげたて", "油で揚げたばかりであること。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(8, "揚げ出し", "あげだし", "片栗粉をまぶし、油であげたもの。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(9, "挙げ玉", "あげたま", "揚げ物をして、油の中に残ったかす。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(10, "揚げ茄子", "あげなす", "＊＊＊", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(11, "揚げ鍋", "あげなべ", "揚げ物に使うなべ。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(12, "上げ幅", "あげはば", "元の位置から、上がった位置までの幅。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(13, "揚げ浜", "あげはま", "機械などで、海水をくみ上げる塩田。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(14, "揚げ浜", "あげはま", "アゲハマ囲碁で、相手から取った石。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(15, "上げ蓋", "あげぶた", "取り外しができる床板。", "名詞"));
		pwrSet.pickupWords.add(new PickupWord(15, "揚げ―", "あげパン", "油であげたパン。", "名詞"));
		//*/
	}

	
}
