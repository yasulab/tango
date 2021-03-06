package tango.client.messenger.model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author yasukawa
 * メソッドをコンタクトのリストに提供する。
 */
public class ContactList {
	
	private Contact me;
	private List contacts = new ArrayList();
	
	public ContactList( String name ){
		this.me = new Contact( name );
	}	
	
	public Contact getMe(){
		return me;
	}	
	
	// 新しいコンタクトをコンタクトリストに挿入する
	public void addContact( Contact contact){
		contacts.add( contact );
	}	
	
	// 与えられたコンタクトをコンタクトリストから削除する
	public void removeContact( Contact contact ){
		for( Iterator it = contacts.iterator(); it.hasNext(); ){
			Contact aContact = (Contact)it.next();
			if( aContact.getName().compareTo( contact.getName() ) == 0 )
				it.remove();
		}
	}
	
	// 指定した名前のコンタクトを返す
	public Contact getContact( String name ){
		for( Iterator it = contacts.iterator(); it.hasNext(); ){
			Contact aContact = (Contact)it.next();
			if( aContact.getName().compareTo( name ) == 0 )
				return aContact;
		}
		return null;
	}
	
	// このリストのコンタクト数を返す
	public int getContactCount(){
		return contacts.size();
	}	
	
	// 指定したインデックスのコンタクトを返す
	public Contact getContact( int index ){
		return (Contact) contacts.get(index);
	}

}
