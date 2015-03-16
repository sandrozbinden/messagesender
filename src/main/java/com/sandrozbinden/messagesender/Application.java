package com.sandrozbinden.messagesender;


public class Application {

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
		}
	}

	public static void main(String[] args) {
		Application application = new Application();
		application.sendMessages();
	}

}
