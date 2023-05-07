import java.io.Closeable;
import java.io.IOException;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.catalina.ant.SessionsTask;


@ServerEndpoint(value = "/ws")
public class ServerSocket {
	private static Vector<Session> sessions = new Vector<Session>();
	@OnOpen
	public void open(Session sess) {
		System.out.println("connected");
		sessions.add(sess);
	}
	
	@OnMessage
	public void onMessage(String mess, Session sess){
		System.out.println(mess);
		try {
			for(Session s: sessions) {
				s.getBasicRemote().sendText(mess);
			}	
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			close(sess);
		}
	}
	
	@OnClose
	public void close(Session sess) {
		System.out.println("Disconnected");
		sessions.remove(sess);
	}
	
	@OnError
	public void error(Throwable error) {
		System.out.println("ERROR");
	}
	
}
