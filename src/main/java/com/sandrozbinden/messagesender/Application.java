package com.sandrozbinden.messagesender;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private void sendMessages() {
        try {
            List<Thread> enabledThreads = new ArrayList<>();
            FolditMessageSender folditMessageSender = new FolditMessageSender();
            if (Setting.getInstance().isFolditEnabled()) {
                logger.info("Starting foldit message sending. To disable set foldit.enabled=false in " + Setting.FILE_NAME);
                folditMessageSender.start();
                enabledThreads.add(folditMessageSender);
            } else {
                logger.info("Foldit message sending disabled. To enable set foldit.enabled=true in " + Setting.FILE_NAME);
            }

            if (Setting.getInstance().isEternaEnabled()) {
                logger.info("Starting eterna message sending. To disable set eterna.enabled=false in " + Setting.FILE_NAME);
                EternaMessageSender eternaMessageSender = new EternaMessageSender();
                eternaMessageSender.start();
                enabledThreads.add(eternaMessageSender);
            } else {
                logger.info("Eterna message sending disabled. To enable set eterna.enabled=true in " + Setting.FILE_NAME);
            }

            for (Thread thread : enabledThreads) {
                thread.join();
            }
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
