package tango.server.word;
/*
 * We must define Default Constructor of the Object
 * that we will send from Server to Client.
 */

import java.sql.*;
import java.util.ArrayList;

import tango.client.ui.PickupTango;
import tango.client.ui.PickupTangoResultSet;
import tango.client.word.PickupWordService;
import tango.server.api.ApacheDerbyService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class PickupWordServiceImpl extends RemoteServiceServlet implements PickupWordService {
	private static final long serialVersionUID = 1L;
	public static final int STATUS_CODE_OK = 200;

	public ArrayList<PickupTango> pickupWordService(PickupTangoResultSet resultSet){
		/* resultSetの設定条件に従ってDatabaseにQueryを投げる */
		resultSet = ApacheDerbyService.sendQuery(resultSet);
		return resultSet.pickupWords;
	}
}
