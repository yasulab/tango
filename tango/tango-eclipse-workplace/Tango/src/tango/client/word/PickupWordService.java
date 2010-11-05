package tango.client.word;

import java.util.ArrayList;

import tango.client.ui.PickupTango;
import tango.client.ui.PickupTangoResultSet;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * @author yasukawa
 *
 */
public interface PickupWordService extends RemoteService {
	public ArrayList<PickupTango> pickupWordService(PickupTangoResultSet msg) throws Exception;

}
