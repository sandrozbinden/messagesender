package com.sandrozbinden.messagesender;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ProcessedEternaUser {

    private Map<String, ProcessedEternaUserInfo> userNameProcessedInfoMap = new HashMap<String, ProcessedEternaUserInfo>();

    public ProcessedEternaUser() {
        init();
    }

    public void add(EternaUser eternaUser) {
        OutputDirectory.ensureFile(Eterna.PROCESSED_ETERNA_USER_FILE);
        try {
            ProcessedEternaUserInfo info = new ProcessedEternaUserInfo(eternaUser);
            Files.append(info.getInfo() + System.lineSeparator(), OutputDirectory.getFile(Eterna.PROCESSED_ETERNA_USER_FILE), Charsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Can't add new processed user", e);
        }
    }

    private void init() {
        OutputDirectory.ensureFile(Eterna.PROCESSED_ETERNA_USER_FILE);
        try {
            for (String line : Files.readLines(OutputDirectory.getFile(Eterna.PROCESSED_ETERNA_USER_FILE), Charsets.UTF_8)) {
                ProcessedEternaUserInfo processedFolditUserInfo = new ProcessedEternaUserInfo(line);
                userNameProcessedInfoMap.put(processedFolditUserInfo.getUsername(), processedFolditUserInfo);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Can't get eterna processed file", e);
        }
    }

    public boolean isProcessed(EternaUser eternaUser) {
        return userNameProcessedInfoMap.containsKey(eternaUser.getUserName());
    }
    
}
