package splitters;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;

import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import app.Credentials;

public class SlackMessage {
	private App app;
	private String messageInput;
	private Map<String, String> messagesOutput;
	private SlackSplitter splitter;

	public SlackMessage(App app) {
		this.app = app;
		this.splitter = new SlackSplitter();
		createMessageHandler();
	}

	private void createMessageHandler() {
		app.message("", (req, ctx) -> {
			System.out.println("== Splitting ==");
			var event = req.getEvent();
			this.messageInput = StringEscapeUtils.unescapeHtml4(event.getText());
			messageSplit();
			messageRoute();
			
			return ctx.ack();
		});

	}

	private void messageRoute() {

		for (String chanel : messagesOutput.keySet()) {
			sendMessage(chanel, messagesOutput.get(chanel));
		}
		
	}

	private void sendMessage(String chanel, String msg) {
		Slack slack = Slack.getInstance();

		MethodsClient methods = slack.methods(Credentials.slackToken);
		
		ChatPostMessageRequest request = ChatPostMessageRequest.builder()
		  .channel(chanel)
		  .text(msg)
		  .build();

		try {
			@SuppressWarnings("unused")
			ChatPostMessageResponse response = methods.chatPostMessage(request);
		} catch (IOException | SlackApiException e) {
			e.printStackTrace();
		}
		
	}
		

	private void messageSplit() {
		this.messagesOutput = splitter.split(messageInput);
	}

}
