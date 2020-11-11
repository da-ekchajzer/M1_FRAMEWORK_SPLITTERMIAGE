package app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;


import splitters.SlackMessage;



public class MyApp {
  public static void main(String[] args) throws Exception {
    var app = new App();
	SlackMessage msg = new SlackMessage(app);
	
    var server = new SlackAppServer(app, 3000);
    server.start();
  }
}
