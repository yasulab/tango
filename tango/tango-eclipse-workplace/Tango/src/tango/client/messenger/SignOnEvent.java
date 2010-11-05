package tango.client.messenger;

import java.util.ArrayList;

import tango.client.messenger.model.Contact;
import tango.client.ui.PickupTango;

public class SignOnEvent extends Event{
	public Contact contact;
	public ArrayList<PickupTango> savedTangoList;
}
