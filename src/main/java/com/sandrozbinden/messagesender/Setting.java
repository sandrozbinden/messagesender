package com.sandrozbinden.messagesender;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Setting {

    private static final Setting instance = new Setting();
    private Properties settings;

    private Setting() {
        settings = new Properties();
        try {
            try (InputStream is = new FileInputStream(getFile())) {
                settings.load(is);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Can't get resource", e);
        }
    }

    public File getFile() {
        return ConfigDirectoy.getFile("settings.properties");
    }

    public static Setting getInstance() {
        return instance;
    }

    public String getProperty(String key) {
        return settings.getProperty(key);
    }

    public String getFolditUsername() {
        return getProperty("foldit.username");
    }

    public String getFolditPassword() {
        return getProperty("foldit.password");
    }

    public String getEternaUsername() {
        return getProperty("eterna.username");
    }

    public int getFolditPlayersPageCount() {
        return Integer.valueOf(getProperty("foldit.players.pagecount"));
    }

    public int getFolditPlayersStartPage() {
        return Integer.valueOf(getProperty("foldit.players.startpage"));
    }

    public int getFolditPlayersRequestSleepInMS() {
        return Integer.valueOf(getProperty("foldit.players.request.sleepInMS"));
    }

    public int getFolditMessageRequestSleepInMS() {
        return Integer.valueOf(getProperty("foldit.message.request.sleepInMS"));
    }

    public String getEternaPassword() {
        return getProperty("eterna.password");
    }

    public int getEternaPlayersRequestSleepInMS() {
        return Integer.valueOf(getProperty("eterna.players.request.sleepInMS"));
    }

    public int getEternaPlayersChunkSize() {
        return Integer.valueOf(getProperty("eterna.players.chunk.size"));
    }

    public int getEternaPlayersMaxSkip() {
        return Integer.valueOf(getProperty("eterna.players.maxSkip"));
    }

    public int getEternaPlayersSkipStart() {
        return Integer.valueOf(getProperty("eterna.players.skip.start"));
    }

    public int getEternaMessageReuqestSleepInMS() {
        return Integer.valueOf(getProperty("eterna.message.request.sleepInMS"));
    }
}
