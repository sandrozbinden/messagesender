package com.sandrozbinden.messagesender;

import java.io.IOException;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FolditMessageSender extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(FolditMessageSender.class);

	@Override
	public void run() {
		int messagesSend = 0;
		logger.info("Initialize send messages on the foldit platform");
		FolditUserLoader userLoader = new FolditUserLoader();
		FolditLogin folditLogin = new FolditLogin();
		folditLogin.login();
		ProcessedFolditUser processedFolditUser = new ProcessedFolditUser();
		Message message = new Message();
		logger.info("Finished inizalize to send messages on the foldit platform");
		for (FolditUser folditUser : userLoader) {
			if (processedFolditUser.isProcessed(folditUser)) {
				logger.info("Skipping foldit  user: " + folditUser.getUserName() + " this has already been processed");
			} else {
				logger.info("Sending foldit message number: " + messagesSend + " to user: " + folditUser.getUserName());
				// sendMessage(message.getMessage(folditUser.getUserName()),
				// folditUser, folditLogin.getSessionID());
				processedFolditUser.add(folditUser);
				try {
					sleep(Setting.getInstance().getFolditMessageRequestSleepInMS());
				} catch (InterruptedException e) {
					throw new IllegalStateException("Can't wait after sending a message", e);
				}
				messagesSend = messagesSend + 1;
			}
		}
	}

	public void sendMessage(String message, FolditUser user, String sessionID) {
		try {
			Response newMessageResponse = Jsoup.connect(Foldit.BASE_NEW_MESSAGE_URL + "/" + user.getId()).cookie(FolditLogin.COOKIE_SESSION_NAME, sessionID).cookie("has_js", "1").execute();
			Document newMessageDocument = newMessageResponse.parse();
			logger.debug(newMessageResponse.parse().toString());

			Response response = Jsoup.connect(Foldit.BASE_NEW_MESSAGE_URL + "/" + user.getId()).followRedirects(true)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8").header("Content-Type", "application/x-www-form-urlencoded")
					.header("Accept-Encoding", "gzip,deflate").header("Cache-Control", "max-age=0").header("Connection", "keep-alive")
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36").cookie(FolditLogin.COOKIE_SESSION_NAME, sessionID)
					.cookie("has_js", "1").timeout(20000).method(Method.GET).data("recipient", user.getUserName()).data("subject", " Subject").data("body", message).data("op", "Send message")
					.data("form_build_id", newMessageDocument.select("[name=form_build_id]").val()).data("form_token", newMessageDocument.select("[name=form_token]").val())
					.data("form_id", newMessageDocument.select("[name=form_id]").val()).method(Method.POST).execute();

			logger.debug(response.parse().toString());
		} catch (IOException e) {
			throw new IllegalStateException("Can't write message", e);
		}

	}
}
