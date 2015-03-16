/* ============================================================================
 * Copyright (c) 2015 Imagic Bildverarbeitung AG, CH-8152 Glattbrugg.
 * All rights reserved.
 *
 * http://www.imagic.ch/
 * ============================================================================
 */
package com.sandrozbinden.messagesender;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 *
 * @version $Revision$
 * @version $Date$
 * @author $Author$
 * @owner Sandro Mario Zbinden
 */
public class ProcessedFolditUser {

    private Map<String, ProcessedFolditUserInfo> userNameProcessedInfoMap = new HashMap<String, ProcessedFolditUserInfo>();

    public ProcessedFolditUser() {
        init();
    }

    public void add(FolditUser folditUser) {
        OutputDirectory.ensureFile(Foldit.PROCESSED_FOLDIT_USER_FILE);
        try {
            ProcessedFolditUserInfo info = new ProcessedFolditUserInfo(folditUser);
            Files.append(info.getInfo() + System.lineSeparator(), OutputDirectory.getFile(Foldit.PROCESSED_FOLDIT_USER_FILE), Charsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Can't add new procssed user", e);
        }
    }

    private void init() {
        OutputDirectory.ensureFile(Foldit.PROCESSED_FOLDIT_USER_FILE);
        try {
            for (String line : Files.readLines(OutputDirectory.getFile(Foldit.PROCESSED_FOLDIT_USER_FILE), Charsets.UTF_8)) {
                ProcessedFolditUserInfo processedFolditUserInfo = new ProcessedFolditUserInfo(line);
                userNameProcessedInfoMap.put(processedFolditUserInfo.getUsername(), processedFolditUserInfo);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Can't get foldit processed file", e);
        }
    }

    public boolean isProcessed(FolditUser folditUser) {
        return userNameProcessedInfoMap.containsKey(folditUser.getUserName());
    }

}
