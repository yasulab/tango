package tango.client.messenger.model;

import java.util.ArrayList;

import tango.client.ui.PickupTango;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author yasukawa
 * メッセージボディの属性を1つだけ持つ。
 * このクラスを継承することで、詳細な情報（タイムスタンプを加えるなど）を提供することができる。
 */
public class Message implements IsSerializable{
	private String message;	// メッセージのみを属性に持つ
	private ArrayList<PickupTango> savedTangoList;
	
	// defaultのコンストラクタ (Serializableにするため)
	public Message(){}
	
	public Message( String message ){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}

	public void setPickupTangoList(ArrayList<PickupTango> savedTangoList) {
		this.savedTangoList = savedTangoList;
	}

	public ArrayList<PickupTango> getPickupTangoList() {
		return savedTangoList;
	}
}