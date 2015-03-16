package com.sandrozbinden.messagesender;

public class Foldit {
    public static final int DEFAULT_TIME_OUT = 20000;
    public static final String ID = "FOLDIT";
    public static String BASE_URL = "http://fold.it/portal";
    public static String RANKING_URL = BASE_URL + "/players/s_all";
    public static final String LOGIN_URL = BASE_URL + "/main?destination=main";
    public static final String BASE_NEW_MESSAGE_URL = BASE_URL + "/messages/new";
    public static final String PROCESSED_FOLDIT_USER_FILE = "processed_foldit_users.txt";
}
