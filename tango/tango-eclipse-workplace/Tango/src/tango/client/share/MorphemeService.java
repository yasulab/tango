package tango.client.share;
import com.google.gwt.user.client.rpc.RemoteService;

public interface MorphemeService extends RemoteService {
	public String morphemeService(String msg) throws Exception;
}
