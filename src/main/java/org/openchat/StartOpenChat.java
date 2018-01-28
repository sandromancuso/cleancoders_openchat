package org.openchat;

public class StartOpenChat {

    static OpenChat openChat;

    public static void main(String[] args) {
        openChat = new OpenChat();
        new Thread(() -> openChat.start()).start();
//        openChat.start();
        System.out.println("Started Open Chat");
    }
}
