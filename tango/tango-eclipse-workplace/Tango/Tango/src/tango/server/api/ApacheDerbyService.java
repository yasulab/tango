package tango.server.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tango.client.ui.PickupTango;
import tango.client.ui.PickupTangoResultSet;

public class ApacheDerbyService {

	public static PickupTangoResultSet sendQuery(PickupTangoResultSet resultSet){
		String url;
		String dbName;
		String query;
		int pickupTimes;
		ResultSet rs;
		final String PROTOCOL =
			"jdbc:derby:WebContent/WEB-INF/word_DB;create=true"; 
		// "jdbc:derby:/usr/local/apache-tomcat-5.5.20/webapps/slicr/slicr;"; 

		// url = "jdbc:derby:word_DB;create=true";
		url = resultSet.dbPath;
		System.out.println(url);
		dbName = "worddictionary";
		query = "SELECT * FROM " + dbName;
		query = makeQuery(query, resultSet);
		query += " ORDER BY RANDOM()";
		System.out.println(query);

		try {
			// Initialize function 
			// Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();

			// pick up words as many times as wordTableSize
			pickupTimes = resultSet.wordTableSize;
			System.out.println("wordTableSize="+resultSet.wordTableSize);
			resultSet.debugMassage = "wordTableSize=" + Integer.toString(resultSet.wordTableSize) + "\n";

			// read words from the database and set pickupWords into resultSet.pickupWords to send Client
			System.out.println("now start reading from database");
			resultSet.debugMassage += "now start reading from database\n";

			// draw data into "ResultSet rs" from database at random
			stmt.setMaxRows(pickupTimes);
			rs = stmt.executeQuery(query);

			while(rs.next()==true){
				System.out.println("ID=" +rs.getInt(1) + ", Length=" + rs.getInt(2) +
						", HowToRead="+rs.getString(3)+ ", Name="+rs.getString(4) + 
						", Dscr="+rs.getString(5) +", Class="+rs.getString(6));
				resultSet.debugMassage += "ID=" +Integer.toString(rs.getInt(1)) + ", Length=" + Integer.toString(rs.getInt(2)) +
				", HowToRead="+rs.getString(3)+ ", Name="+rs.getString(4) + 
				", Dscr="+rs.getString(5) +", Class="+rs.getString(6) + "\n";

				// PickupWord(int length, String howToRead, String name, String description) 
				// NOTE: ID is only used in database, not in resultSet.pickupWords.
				resultSet.pickupWords.add(new PickupTango(rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			System.out.println("now end reading from database");
			resultSet.debugMassage += "now end reading from database\n";
			conn.close();

		} catch (SQLException e) {
			resultSet.pickupWords.add(0, new PickupTango(-1, "SQLerr", "SQLException", e.getMessage(), e.getNextException().getMessage()));
			System.err.println(e.getMessage());
			System.err.println(e.getNextException().getMessage());
			resultSet.debugMassage += e.getMessage() + "\n";
		} catch (Exception e) {
			resultSet.pickupWords.add(0, new PickupTango(-1, "Exception error", "Exception error", e.getMessage(), e.getMessage()));
			System.err.println(e.getMessage());
			resultSet.debugMassage += e.getMessage() + "\n";
		}

		if(resultSet.debugMode == true){
			setTestDataOnWeb(resultSet);
			resultSet.pickupWords.add(0, new PickupTango(-1, "debug message", "debug message", 
					resultSet.debugMassage, resultSet.debugMassage));
		}
		return resultSet;
	}

	private static String makeQuery(String query2, PickupTangoResultSet resultSet) {
		query2 += " WHERE ";

		if(resultSet.wordLevelEnableFlag) {
			if(resultSet.wordLevelEasyFlag) {
				if(resultSet.wordLevelNormalFlag) {
					if(!resultSet.wordLevelHardFlag) {query2 += "(length <= 6) AND ";}
				} 
				else if(resultSet.wordLevelHardFlag) {query2 += "(length <= 3 OR length >= 7) AND ";}
				else {query2 += "(length <= 3) AND ";}
			} else if(resultSet.wordLevelNormalFlag) {
				if(resultSet.wordLevelHardFlag) {query2 += "(length >= 4) AND ";}
				else {query2 += "(length >= 4 AND length <= 6) AND ";}
			} else if(resultSet.wordLevelHardFlag) {query2 += "(length >= 7) AND";}
		}

		if(resultSet.wordClassEnableFlag) {
			if(resultSet.wordClassNounFlag) {
				if(resultSet.wordClassVerbFlag) {
					if(resultSet.wordClassAdjFlag) {query2 += "(class = '名詞' OR class = '動詞' OR class = '形容詞') AND　";
					}else {query2 += "(class = '名詞' OR class = '動詞') AND "; 
					} 
				} else if(resultSet.wordClassAdjFlag){query2 += "(class = '名詞' OR class = '形容詞') AND ";
				} else{query2 += "(class = '名詞') AND ";
				} 
			} else if(resultSet.wordClassVerbFlag){
				if(resultSet.wordClassAdjFlag) {query2 += "(class = '動詞' OR class = '形容詞') AND ";
				} else {query2 += "（class = '動詞') AND ";
				}
			} else if(resultSet.wordClassAdjFlag) {query2 += "(class = '形容詞') AND ";
			}
		}

		if(resultSet.relationalWordEnableFlag){
			if(!resultSet.relationalWord.equals(null)){
				query2 += "name LIKE '%" + resultSet.relationalWord + "%'";
			}
		}

		if(query2.endsWith("WHERE ")){
			query2 = query2.substring(0, query2.length() - 6);
		} else if(query2.endsWith("AND ")) {
			query2 = query2.substring(0, query2.length() - 4);
		} 

		return query2;
	}
	
	/* set TestData on resultSet.pickupWords intentionally for debug */
	private static void setTestDataOnWeb(PickupTangoResultSet pwrSet){
		pwrSet.pickupWords.clear();
		int select;
		for(int i=0; i<17; i++){
			select = (int)(17*Math.random());
			switch(select){case 0:	pwrSet.pickupWords.add(new PickupTango(i, "あげおし", "上尾市", "埼玉県の市。", "名詞"));
			break;
			case 1: pwrSet.pickupWords.add(new PickupTango(i, "あげかす", "揚げ滓", "飛行機を上に向ける、かじのとり方。", "名詞"));
			break;
			case 2: pwrSet.pickupWords.add(new PickupTango(i, "あげさげ" , "上げ下げ", "上げることと、下げること。", "名詞"));
			break;
			case 3: pwrSet.pickupWords.add(new PickupTango(i, "あげしお", "上げ潮", "潮が満ちること。みちしお。", "名詞"));
			break;
			case 4: pwrSet.pickupWords.add(new PickupTango(i, "あげざる", "揚げ笊", "ゆでたそばをすくい上げる、ざる。", "名詞"));
			break;		
			case 5: pwrSet.pickupWords.add(new PickupTango(i, "あげぜん", "上げ膳", "客に、食事の膳を出すこと。", "名詞"));
			break;
			case 6: pwrSet.pickupWords.add(new PickupTango(i, "あげぞこ", "上げ底", "中身を多く見せるため、そこを上げた容器。", "名詞")	);
			break;
			case 7: pwrSet.pickupWords.add(new PickupTango(i, "あげたて", "揚げ立て", "油で揚げたばかりであること。", "名詞"));
			break;
			case 8: pwrSet.pickupWords.add(new PickupTango(i, "あげだし", "揚げ出し", "片栗粉をまぶし、油であげたもの。", "名詞"));
			break;
			case 9: pwrSet.pickupWords.add(new PickupTango(i, "あげたま", "挙げ玉", "揚げ物をして、油の中に残ったかす。", "名詞"));
			break;
			case 10: pwrSet.pickupWords.add(new PickupTango(i, "あげなす", "揚げ茄子", "＊＊＊", "名詞"));
			break;
			case 11: pwrSet.pickupWords.add(new PickupTango(i, "あげなべ", "揚げ鍋", "揚げ物に使うなべ。", "名詞"));
			break;
			case 12: pwrSet.pickupWords.add(new PickupTango(i, "あげはば", "上げ幅", "元の位置から、上がった位置までの幅。", "名詞"));
			break;
			case 13: pwrSet.pickupWords.add(new PickupTango(i, "あげはま", "揚げ浜", "機械などで、海水をくみ上げる塩田。", "名詞"));
			break;
			case 14: pwrSet.pickupWords.add(new PickupTango(i, "あげはま", "揚げ浜", "アゲハマ囲碁で、相手から取った石。", "名詞"));
			break;
			case 15: pwrSet.pickupWords.add(new PickupTango(i, "あげぶた", "上げ蓋", "取り外しができる床板。", "名詞"));
			break;
			case 16: pwrSet.pickupWords.add(new PickupTango(i, "あげパン", "揚げ―", "油であげたパン。", "名詞"));
			break;
			}
		}
	}

}
