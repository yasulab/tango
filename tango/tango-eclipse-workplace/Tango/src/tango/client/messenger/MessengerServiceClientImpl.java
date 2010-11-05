package tango.client.messenger;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

import tango.client.Tango;
import tango.client.messenger.model.Contact;
import tango.client.messenger.model.ContactList;
import tango.client.messenger.model.Message;
import tango.client.messenger.view.MessengerView;
import tango.client.messenger.view.MessengerViewListener;
import tango.client.ui.PickupTango;
import tango.client.ui.TangoPanel;

public class MessengerServiceClientImpl implements MessengerViewListener{

	/**
	 * サインインしたときのコールバック
	 * @author yasukawa
	 */
	private class SignInCallback implements AsyncCallback{
		public void onFailure(Throwable throwable){ GWT.log("error sign in",throwable); }
		public void onSuccess(Object obj){
			System.out.println("SignInCallback in MessengerServiceClientImpl has started!");
			view.setContactList( contactList );

			// サインインしたらshareTangoWindowを設置する
			view.setShareTangoWindow();
			messengerService.getEvents( new GetEventsCallback() );
		}
	}

	/**
	 * Null時のCallback
	 */
	private class EmptyCallback implements AsyncCallback{
		public void onFailure(Throwable throwable){ GWT.log("error",throwable); }
		public void onSuccess(Object obj){}
	}	

	/**
	 * Eventを取得した時のCallback
	 * @author yasukawa
	 */
	private class GetEventsCallback implements AsyncCallback{
		public void onFailure(Throwable throwable){ GWT.log("error get events",throwable); }
		public void onSuccess(Object obj){
			ArrayList events = (ArrayList)obj;
			for( int i=0; i< events.size(); ++i ){
				Object event = events.get(i);	// Event取得
				handleEvent( event );			// Event処理
			}
			messengerService.getEvents( this );
		}
	}

	private MessengerServiceAsync messengerService;
	private ContactList contactList;
	private MessengerView view = new MessengerView( this );

	/* EntryPointのservice URIを宣言する */
	public MessengerServiceClientImpl( String URL ){
		messengerService = (MessengerServiceAsync) GWT.create( MessengerService.class );
		ServiceDefTarget endpoint = (ServiceDefTarget) messengerService;
		endpoint.setServiceEntryPoint( URL );		
	}

	public MessengerView getView(){
		return view;
	}

	/* サインイン・サインアウト・メッセージ送信のInterfaceの実装 
	 * サーバからの通知に従い、該当した関数が呼び出される。
	 * その後、再びサーバーとの通信を始める。
	 */
	// サインインしたときに呼び出される（最初）
	public void onSignIn( String name, ArrayList<PickupTango> pickupTangoList ){
		System.out.println("onSignIn() in MessengerServiceClientImpl has started!");
		contactList = new ContactList( name );
		messengerService.signIn( name, pickupTangoList, new SignInCallback() );
	}
	public void onSignOut(){
		System.out.println("onSignOut() in MessengerServiceClientImpl has started!");
		messengerService.signOut( new EmptyCallback() );
	}	
	public void onSendMessage( Contact toContact, Message message ){
		System.out.println("onSendMessage() in MessengerServiceClientImpl has started!");
		messengerService.sendMessage( toContact, message, new EmptyCallback() );
	}
	public void onSendSavedTango(PickupTango pickupTango) {
		System.out.println("onSendSavedTango() in MessengerServiceClientImpl has started!");
		messengerService.sendSavedTango(pickupTango, new EmptyCallback());
	}  
	public void onSendSavedTangoList(ArrayList<PickupTango> pickupTangoList) {
		System.out.println("onSendSavedTangoList() in MessengerServiceClientImpl has started!");
		messengerService.sendSavedTangoList(pickupTangoList, new EmptyCallback());
	}
	public void onRefreshShareTangoList(ArrayList<PickupTango> pickupTangoList){
		System.out.println("onRefreshShareTangoList() in MessengerServiceClientImpl has started!");
		messengerService.refreshShareTangoList(pickupTangoList, new EmptyCallback());
	}


	/*Eventの取り扱い機構*/
	protected void handleEvent( Object event ){
		// RefreshShareTangoEventの場合(最も呼び出される頻度が高い)
		if( event instanceof RefreshShareTangoEvent){
			System.out.println("ShareTangoRefreshEvent in MessengerServiceClientImpl has started!");
			RefreshShareTangoEvent refreshEvent = (RefreshShareTangoEvent)event;
			ArrayList<ArrayList<PickupTango>> allTangoList = refreshEvent.shareTangoList;
			ArrayList<PickupTango> myTangoList = Tango.getSavedTangoList();
			//System.out.println(allTangoList.get(0).toString());
			//System.out.println(myTangoList.toString());
			//this.showPickupTangoList(allTangoList.get(0));
			//this.showPickupTangoList(myTangoList);
			for(int i=0; i<allTangoList.size(); i++){
				if(this.comparePickupTangoList(allTangoList.get(i), myTangoList)){
					System.out.println("allTangoList.get("+i+") has removed.");
					allTangoList.remove(i);
					break;
				}else{
					System.out.println("allTangoList.get("+i+") is not myTangoList");
				}
			}
			
			
			// ArrayList<ArrayList<PickupTango>> →　ArrayList<PickupTango>に解体する。
			ArrayList<PickupTango> refreshPickupTangoList = convergeArray(allTangoList);
			view.getShareTangoView().refreshPickupTangoList(refreshPickupTangoList);
		}
		// メッセージ送信Eventの場合
		else if( event instanceof SendMessageEvent ){	
			SendMessageEvent sendMessageEvent = (SendMessageEvent)event;
			view.getChatWindowView( sendMessageEvent.sender ).addMessage( sendMessageEvent.message );
		}
		// サインインEventの場合
		else if( event instanceof SignOnEvent ){
			SignOnEvent signOnEvent = (SignOnEvent)event;
			contactList.addContact(signOnEvent.contact);
			view.getContactListView().addContact(signOnEvent.contact);
		}
		// サインアウトEventの場合
		else if( event instanceof SignOffEvent ){
			SignOffEvent signOffEvent = (SignOffEvent)event;
			contactList.removeContact(signOffEvent.contact);
			view.getContactListView().removeContact(signOffEvent.contact);
			// MessengerView.getListener().onRefreshShareTangoList();
			// view.showSignInView();
		}
		// SavedTango送信Eventの場合
		else if( event instanceof SendSavedTangoEvent){
			System.out.println("SendSavedTangoEvent in MessengerServiceClientImpl has started!");
			SendSavedTangoEvent saveEvent = (SendSavedTangoEvent)event;
			if(saveEvent.isList==true){
				view.getShareTangoView().addPickupTangoList(saveEvent.pickupTangoList);
			}else{
				view.getShareTangoView().addPickupTangoList(saveEvent.pickupTango);
			}
		}
	}

	/**
	 * 複数のPickupTangoのリストを一つにまとめる。
	 * @param arrayList
	 * @return
	 */
	public ArrayList<PickupTango> convergeArray(ArrayList<ArrayList<PickupTango>> arrayList) {
		ArrayList<PickupTango> tangoArray = new ArrayList<PickupTango>();

		for(int i=0; i<arrayList.size(); i++){
			tangoArray.addAll(arrayList.get(i));
		}

		return tangoArray;
	}
	
	
	private void showPickupTangoList(ArrayList<PickupTango> pickupTangoList){
		String str = "show string default";
		if(pickupTangoList.isEmpty()){
			str = "Empty!";
		}else{
			str = "[";
			for(int i=0;i<pickupTangoList.size();i++){
				str += pickupTangoList.get(i).getName()+ ", ";
			}
			str = str.substring(0, str.length()-2);
			str += "]";
		}
		System.out.println(str);
	}
	
	private boolean comparePickupTangoList(ArrayList<PickupTango> a, ArrayList<PickupTango> b){
		if(a.isEmpty() && b.isEmpty()){
			return true;
		}else if(a.isEmpty() != b.isEmpty()){
			return false;
		}else if(a.size() != b.size()){
			return false;
		}else{
			for(int i=0;i<a.size();i++){
				if(a.get(i).getName().equals(b.get(i).getName())){
					// a[i] is same as b[i]
				}else{
					return false;
				}
			}
		}
		return true;
	}
}