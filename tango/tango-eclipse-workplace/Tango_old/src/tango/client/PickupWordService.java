package tango.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * @author yasukawa
 *
 */
public interface PickupWordService extends RemoteService {
	public ArrayList<PickupWord> pickupWordService(PickupWordResultSet msg) throws Exception;

}
