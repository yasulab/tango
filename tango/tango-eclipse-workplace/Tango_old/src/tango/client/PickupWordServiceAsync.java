package tango.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author yasukawa
 *
 */
public interface PickupWordServiceAsync {
	public void pickupWordService(PickupWordResultSet msg, AsyncCallback<ArrayList<PickupWord>> callback);

}
