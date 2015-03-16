package com.sandrozbinden.messagesender;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ConfigDirectoy {

    public static void ensureFile(String fileName) {
        File file = getFile(fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                Files.write("", file, Charsets.UTF_8);
            } catch (IOException e) {
                throw new IllegalStateException("Can't create file", e);
            }
        }
    }

    public static final File getFile(String fileName) {
        return new File("config/" + fileName);
    }
}
