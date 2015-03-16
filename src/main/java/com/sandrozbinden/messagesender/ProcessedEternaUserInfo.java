package com.sandrozbinden.messagesender;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcessedEternaUserInfo {

    private String time;
    private String username;

    public ProcessedEternaUserInfo(String unparsed) {
        String[] splitted = unparsed.split("\t");
        this.time = splitted[0];
        this.username = splitted[1];
    }

    public ProcessedEternaUserInfo(EternaUser eternaUser) {
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.username = eternaUser.getUserName();
    }

    public String getInfo() {
        return time + "\t" + username + "\t";
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }
}
