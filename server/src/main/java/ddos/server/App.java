package ddos.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.io.IOException;
import java.net.InetSocketAddress;

import javax.ws.rs.ext.RuntimeDelegate;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class App {
	public static void main(String[] args) throws Exception {
		final HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		
		HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new JaxRsApplication(), HttpHandler.class);
		
		ExecutorService clientsThreadPool = Executors.newFixedThreadPool(100);
		
		server.createContext("/", handler);
		server.setExecutor(Executors.newFixedThreadPool(100));
		server.start();
		
		System.out.println("Server running on port 8080, press any key to stop..");
		System.in.read();
		
		server.stop(1);
		clientsThreadPool.shutdownNow();
	}
}
