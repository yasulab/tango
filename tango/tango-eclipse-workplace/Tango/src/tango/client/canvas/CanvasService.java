/**
 * 
 */
package tango.client.canvas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author yasukawa
 *
 */
@RemoteServiceRelativePath("canvasService")
public interface CanvasService extends RemoteService {
	public String canvasService(String msg) throws Exception;
}
