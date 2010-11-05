package tango.client.messenger;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import tango.client.messenger.model.Contact;
import tango.client.messenger.model.Message;
import tango.client.ui.PickupTango;

public interface MessengerService extends RemoteService{
	void signIn( String name, ArrayList<PickupTango> pickupTangoList );
	void signOut();
	void sendMessage( Contact to, Message message );
	void sendSavedTango(PickupTango pickupTango);
	void sendSavedTangoList(ArrayList<PickupTango> pickupTangoList);
	void refreshShareTangoList(ArrayList<PickupTango> pickupTangoList);
	ArrayList<Event> getEvents();
}
