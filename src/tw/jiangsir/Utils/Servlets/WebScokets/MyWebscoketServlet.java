package tw.jiangsir.Utils.Servlets.WebScokets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

@WebServlet(urlPatterns = { "/myWebsocket" })
public class MyWebscoketServlet extends WebSocketServlet {

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
	    HttpServletRequest request) {
	// TODO Auto-generated method stub
	return null;
    }

}
