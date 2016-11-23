package sample;

import java.util.Map;

public class Message {
  public String controller;
  public String action;
  public Map<String, String> data;
  
  public Message(String controller, String action, Map<String, String> data) {
    this.controller = controller;
    this.action     = action;
    this.data       = data;
  }
}