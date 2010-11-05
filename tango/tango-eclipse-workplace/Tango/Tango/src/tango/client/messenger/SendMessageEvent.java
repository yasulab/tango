package tango.client.messenger;

import tango.client.messenger.model.Contact;
import tango.client.messenger.model.Message;

public class SendMessageEvent extends Event 
{
	public Contact sender;
	public Message message;
}
