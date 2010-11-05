/**
 * 
 */
package tango.client.canvas;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author yasukawa
 *
 */
public interface CanvasServiceAsync {
	public void canvasService(String msg, AsyncCallback<String> callback);
}
