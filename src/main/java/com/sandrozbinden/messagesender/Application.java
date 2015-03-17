package com.sandrozbinden.messagesender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private void sendMessages() {
        try {
            FolditMessageSender folditMessageSender = new FolditMessageSender();
            folditMessageSender.start();

            EternaMessageSender eternaMessageSender = new EternaMessageSender();
            eternaMessageSender.start();

            folditMessageSender.join();
            eternaMessageSender.join();

        } catch (InterruptedException e) {
            throw new IllegalStateException("Can't finish message send execution", e);
        } catch (RuntimeException e) {
            logger.error("Programm terminated with: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.sendMessages();
    }

}
