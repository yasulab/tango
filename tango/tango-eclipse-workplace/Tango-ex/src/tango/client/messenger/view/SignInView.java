package tango.client.messenger.view;

import java.util.ArrayList;

import tango.client.Tango_ex;
import tango.client.ui.PickupTango;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SignInView extends Composite {

	private VerticalPanel mainPanel = new VerticalPanel();
	private TextBox nameBox = new TextBox();
	private MessengerView view;

	public SignInView( MessengerView view ){
		initWidget( mainPanel );
		this.view = view;
		mainPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		Button signInButton = new Button( "Sign In" );
		VerticalPanel vpanel = new VerticalPanel();
		vpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vpanel.add( new Label( "Sign in name:") );
		vpanel.add( nameBox );
		vpanel.add( signInButton );
		mainPanel.add( vpanel );
		nameBox.addKeyboardListener(new KeyboardListenerAdapter() {
			public void onKeyPress(Widget sender, char keyCode, int modifiers){
				if(keyCode == KEY_ENTER )
					signIn();
			}});

		signInButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				signIn();
			}});
	}
	
	public void signIn(){
		String name = nameBox.getText();
		if( name.length() > 0 ){
			mainPanel.clear();
			Label signInLabel = new Label("Signing in...");
			mainPanel.add( signInLabel );

			// !!!NEW!!!
			/**
			 * サインインと同時に現在の保存単語をすべてリスト化してサーバに送る。
			 * その後はPickupTangoを一個ずつ送る。
			 */
			ArrayList<PickupTango> pickupTangoList = new ArrayList<PickupTango>();
			pickupTangoList = Tango_ex.getSavedTangoList();
			System.out.println("local pickupTangoList in SignInView="+pickupTangoList.toString());
			MessengerView.getListener().onSignIn(name, pickupTangoList);

			// スレッド式に通信を行うので、onRefreshShareTangoList()が先に処理される恐れがある！
			// MessengerView.getListener().onRefreshShareTangoList(pickupTangoList);
			//view.getListener().onSendSavedTangoList(signInSavedTangoList);
		}
	}
}