package sample.robot;

import java.util.Map;

public interface Controller {
  public void executeAction(String action, Map<String, String> data);
}
