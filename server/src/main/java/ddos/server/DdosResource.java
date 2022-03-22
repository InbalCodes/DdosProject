package ddos.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

@Path("ddos")
public class DdosResource {
	private static final ConcurrentMap<Integer, DdosProtector> clientDdosProtection = 
			new ConcurrentHashMap<Integer, DdosProtector>();
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getSomething(@QueryParam("clientId") int clientId) {
    	DdosProtector ddosProtector = clientDdosProtection.computeIfAbsent(
    			clientId, k -> new DdosProtector(5, 5, TimeUnit.SECONDS));
    	
    	if (!ddosProtector.check()) {
    		return Response
				.status(Response.Status.SERVICE_UNAVAILABLE)
				.build();
    	}
        
        System.out.println("Handling client: " + clientId);
    	
        return Response
			.status(Response.Status.OK)
			.build();
    }
}
