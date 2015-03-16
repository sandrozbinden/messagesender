/* ============================================================================
 * Copyright (c) 2015 Imagic Bildverarbeitung AG, CH-8152 Glattbrugg.
 * All rights reserved.
 *
 * http://www.imagic.ch/
 * ============================================================================
 */
package com.sandrozbinden.messagesender;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 *
 * @version $Revision$
 * @version $Date$
 * @author $Author$
 * @owner Sandro Mario Zbinden
 */
public class OutputDirectory {

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
        return new File("output/" + fileName);
    }
}
