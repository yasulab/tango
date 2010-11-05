package tango.client.messenger;

import java.util.ArrayList;

import tango.client.messenger.model.Contact;
import tango.client.ui.PickupTango;

public class SendSavedTangoEvent extends Event{
	public Contact contact;
	public PickupTango pickupTango;
	public ArrayList<PickupTango> pickupTangoList;
	public boolean isList;
}
