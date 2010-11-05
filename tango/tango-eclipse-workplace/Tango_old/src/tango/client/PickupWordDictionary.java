package tango.client;
import java.util.*;

import com.google.gwt.user.client.Window;


public class PickupWordDictionary{
	public ArrayList<PickupWord> wordDictionary=new ArrayList<PickupWord>();
	double[][] rank = new double[100][2];
	int[] link = new int[100];
	// ReadWordDictionary rwd;

	
	public PickupWordDictionary(ArrayList<PickupWord> wordDictionary){
		this.wordDictionary=wordDictionary;
	}
	
	public ArrayList<PickupWord> randomReadWordDictinary(boolean[] levelFlags, boolean[] classFlags){
		ArrayList<PickupWord> pickupWords=new ArrayList<PickupWord>();
		int indexNumber = 0;
		int missCounter = 0;
		String wordClass="";
		int enable = 0; int easy = 1; int normal = 1; int hard = 2;
		int noun = 1; int verb = 2; int adj = 3;
		for(int i=0;i<50;){
		indexNumber = (int)(Math.random()*wordDictionary.size());
		int wordLength=((PickupWord)wordDictionary.get(indexNumber)).getWordLevel();
		wordClass = ((PickupWord)wordDictionary.get(indexNumber)).getCategory();
		/*
		 * if((wordLevel) && (wordClass))
		 * ((やさしいフラグ＆＆やさしい条件）｜｜（ふつうフラグ＆＆ふつう条件）｜｜（むずかしいフラグ＆＆むずかしい条件））
		 * ＆＆
		 * （（形容詞フラグ＆＆wordClass.equals(形容詞)) || (名詞フラグ＆＆品詞＝名詞)｜｜（動詞フラグ＆＆品詞＝動詞））
		 */
		if((((levelFlags[enable]==true&&
				((levelFlags[easy]==true && (0 < wordLength && wordLength <= 4))
				||(levelFlags[normal]==true && (5 <= wordLength && wordLength <= 6))
				||(levelFlags[hard]==true && 7 <= wordLength ))))
				|| levelFlags[enable]==false)
		  &&
		  ((classFlags[enable]==true &&
				((classFlags[adj]==true && wordClass.equals("形容詞"))
				||(classFlags[noun]==true && wordClass.equals("名詞"))
				||(classFlags[verb]==true && wordClass.equals("動詞"))))
				||classFlags[enable]==false
		  )
		){
			pickupWords.add(new PickupWord(((PickupWord)wordDictionary.get(indexNumber)).getWordLevel(),
					((PickupWord)wordDictionary.get(indexNumber)).getHowToRead(),
					((PickupWord)wordDictionary.get(indexNumber)).getName(),
					((PickupWord)wordDictionary.get(indexNumber)).getDescription(),
					((PickupWord)wordDictionary.get(indexNumber)).getDescription())			
			);
			i++;
		}
		else{
			missCounter++;
			if(missCounter>1000){
				i=50;
			}
		}
		}
		return pickupWords;
	}
	
	public ArrayList<PickupWord> relationalWordSearch(String word){
		ArrayList<PickupWord> pickupWords=new ArrayList<PickupWord>();
		String str;
		double concordanceRate;
		int counter=0;
		for (int i = 0; i < wordDictionary.size(); i++){
			if ((str=((PickupWord)wordDictionary.get(i)).getName()).indexOf(word) != -1 && counter<rank.length){
				concordanceRate = (double)word.length() / str.length() * 1000;
//				rank[counter][0] = -concordanceRate;
				rank[counter][1] = i;
				counter++;
//				System.out.println("picked relativeWord Rate="+rank[counter-1][0]);
//				System.out.println("picked relativeWord Id="+rank[counter-1][1]);
			}
		}
		int[][] test = new int[counter][2];
		int[] shuffle=new int[counter];
		for(int i=0;i<counter;i++)
		{
			test[i][1]=-1;
		}
		int index=0;
		int missCounter=0;
		for (int i = 0; i < counter;){/*配列長をresize*/
			index=(int)((double)Math.random()*counter);
			System.out.println("index="+index);
			if(test[index][1]==-1){
				test[index][1]=(int)rank[i][1];
				System.out.println(test[index][1]);
				i++;
			}
			else{
				missCounter++;
				if(missCounter>500)
					i=counter+1;
			}
			
			

			//			test[i][0] = (int)rank[i][0];
//			test[i][1] = (int)rank[i][1];
		}
		//setLink(test,0,test.length-1);
		for (int i = 0; i < counter; i++){
			if(test[i][1]!=-1){
			pickupWords.add(new PickupWord(((PickupWord)wordDictionary.get(test[i][1])).getWordLevel(),
					((PickupWord)wordDictionary.get(test[i][1])).getHowToRead(),
					((PickupWord)wordDictionary.get(test[i][1])).getName(),
					((PickupWord)wordDictionary.get(test[i][1])).getDescription(),
					((PickupWord)wordDictionary.get(test[i][1])).getCategory()
					));
		}
		}
		return pickupWords;
	}
	
	void setLink(int[][] test, int left, int right){
		int p = test[(left + right) / 2][0];
		int i = left;
		int j = right;

		for (; ; i++, j--){
			while (test[i][0] < p){
				i++;
			}
			while (test[j][0] > p){
				j--;
			}
			if (i >= j){
				break;
			}
			int temp[] ={ test[i][0], test[i][1] };
			test[i][0] = test[j][0];
			test[i][1] = test[j][1];
			test[j][0] = temp[0];
			test[j][1] = temp[1];
		}
		i--;
		if (left < i){
			setLink(test, left, i);
		}
		j++;
		if (right > j){
			setLink(test, j, right);
		}
	}
}
