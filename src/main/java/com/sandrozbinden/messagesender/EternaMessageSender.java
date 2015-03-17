package com.sandrozbinden.messagesender;

import java.io.IOException;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EternaMessageSender extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(EternaMessageSender.class);

    @Override
    public void run() {
        int messagesSend = 0;
        logger.info("Initialize send messages on the eterna platform");
        EternaUserLoader userLoader = new EternaUserLoader();
        EternaLogin eternaLogin = new EternaLogin();
        eternaLogin.login();
        ProcessedEternaUser processedEternaUser = new ProcessedEternaUser();
        Message message = new Message();
        logger.info("Finished inizalize to send messages on the eterna platform");
        for (EternaUser eternaUser : userLoader) {
            if (processedEternaUser.isProcessed(eternaUser)) {
                logger.info("Skipping eterna  user: " + eternaUser.getUserName() + " this has already been processed");
            } else {
                logger.info("Sending eterna message number: " + messagesSend + " to user: " + eternaUser.getUserName());
                sendMessage(message.getMessage(eternaUser.getUserName()), eternaUser, eternaLogin.getSessionID());
                processedEternaUser.add(eternaUser);
                try {
                    sleep(Setting.getInstance().getFolditMessageRequestSleepInMS());
                } catch (InterruptedException e) {
                    throw new IllegalStateException("Can't wait after sending a message", e);
                }
                messagesSend = messagesSend + 1;
            }

        }
    }

    public void sendMessage(String message, EternaUser user, String sessionID) {
        try {
            String playerURL = Eterna.BASE_PLAYER_URL + "/" + user.getId() + "/";
            Response playerResponse = Jsoup.connect(playerURL).cookie(EternaLogin.COOKIE_SESSION_NAME, sessionID).timeout(20000).execute();

            logger.debug(playerResponse.parse().toString());

            Response response = Jsoup.connect(Eterna.BASE_NEW_MESSAGE_URL + "/").followRedirects(true).header("Accept", "*/*")
                    .header("Content-Type", "application/x-www-form-urlencoded").header("Accept-Encoding", "gzip,deflate").header("Connection", "keep-alive")
                    .header("X-Requested-With", "XMLHttpRequest").header("Referer", playerURL)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36")
                    .cookie(EternaLogin.COOKIE_SESSION_NAME, sessionID).timeout(20000).method(Method.GET).data("target_uid", user.getId())
                    .data("body", message).data("notification_type", "message").data("action", "add").data("type", "message").method(Method.POST).execute();
            JSONObject restResponse = (JSONObject) JSONValue.parse(response.parse().body().text());
            JSONObject data = (JSONObject) restResponse.get("data");
            if ((Boolean) data.get("success")) {
                logger.debug(response.parse().toString());
            } else {
                String error = "Could't send a message to eterna user: " + user.getUserName() + " response: " + response.parse().toString();
                logger.error(error);
                throw new IllegalStateException(error);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

}
