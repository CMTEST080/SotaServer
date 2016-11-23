package sample.server;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import sample.Message;
import sample.robot.RobotAPI;

public class MyWebSocketListener implements WebSocketListener {
  private static final Logger LOG = Log.getLogger(MyWebSocketListener.class);
  private Session session;

  public void onWebSocketClose(int statusCode, String reason) {
    this.session = null;
    LOG.info("WebSocket Close: {} - {}", statusCode, reason);
  }

  public void onWebSocketConnect(Session session) {
    this.session = session;
    LOG.info("WebSocket Connect: {}", session);
    this.session.getRemote().sendString("You are now connected to " + this.getClass().getName(), null);
  }

  public void onWebSocketError(Throwable cause) {
    LOG.warn("WebSocket Error", cause);
  }

  public void onWebSocketText(String message) {
    LOG.info("WebSocket Receive Text: {}", message);
    if ((session == null) || (!session.isOpen())) { 
      return;
    }

    Gson gson = new Gson();
    Message json = null;
    try {
      json = gson.fromJson(message, Message.class);
    } catch (JsonSyntaxException e) {
      session.getRemote().sendString(e.getMessage(), null);
      return;
    }

    RobotAPI.call(json);
    session.getRemote().sendString(gson.toJson(json), null);
  }

  @Override
  public void onWebSocketBinary(byte[] payload, int offset, int len) {
    // TODO Auto-generated method stub
    LOG.info("WebSocket Receive Binary Request");
    session.getRemote().sendString("Binary request is not supported", null);
  }
}