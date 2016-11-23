package sample.server;

import java.net.URL;
import java.util.Objects;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import sample.robot.RobotAPI;

public class ServerEntryPoint {
  public static void main(String[] args) {
    Server server = new Server(3000);

    ServletContextHandler sevletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    sevletContextHandler.setContextPath("/");
    server.setHandler(sevletContextHandler);

    // WebSocketServlet
    MyWebSocketServlet myWebSocketServlet = new MyWebSocketServlet();
    ServletHolder servletHolder = new ServletHolder("api", myWebSocketServlet);
    sevletContextHandler.addServlet(servletHolder, "/api");

    // DefaultServlet(静的ファイルを配信)
    DefaultServlet defaultServlet = new DefaultServlet();
    ServletHolder defaultHolder = getDefaultHolder(defaultServlet);
    sevletContextHandler.addServlet(defaultHolder, "/");

    try {
      server.start();
      RobotAPI.setUpRobot();
      RobotAPI.eyesSparkling();
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static ServletHolder getDefaultHolder(DefaultServlet defaultServlet) {
    ServletHolder defaultHolder = new ServletHolder("default", defaultServlet);

    URL urlStatics = Thread.currentThread().getContextClassLoader().getResource("index.html");
    Objects.requireNonNull(urlStatics, "Unable to find index.html in classpath");
    String urlBase = urlStatics.toExternalForm().replaceFirst("/[^/]*$", "/");

    defaultHolder.setInitParameter("resourceBase", urlBase);
    defaultHolder.setInitParameter("dirAllowed", "true");
    return defaultHolder;
  }
}
