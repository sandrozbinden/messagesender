/* ============================================================================
 * Copyright (c) 2015 Imagic Bildverarbeitung AG, CH-8152 Glattbrugg.
 * All rights reserved.
 *
 * http://www.imagic.ch/
 * ============================================================================
 */
package com.sandrozbinden.messagesender;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @version $Revision$
 * @version $Date$
 * @author $Author$
 * @owner Sandro Mario Zbinden
 */
public class ProcessedFolditUserInfo {

    private String time;
    private String username;

    public ProcessedFolditUserInfo(String unparsed) {
        String[] splitted = unparsed.split("\t");
        this.time = splitted[0];
        this.username = splitted[1];
    }

    public ProcessedFolditUserInfo(FolditUser folditUser) {
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.username = folditUser.getUserName();
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
