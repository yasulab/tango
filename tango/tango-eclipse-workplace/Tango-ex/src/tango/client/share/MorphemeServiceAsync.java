package tango.client.share;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MorphemeServiceAsync {
	public void morphemeService(String msg, AsyncCallback<String> callback);
}
