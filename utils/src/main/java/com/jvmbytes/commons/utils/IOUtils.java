package com.jvmbytes.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wongoo
 */
public class IOUtils {

    public static final int EOF = -1;

    public static byte[] toByteArray(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        int n;
        byte[] buffer = new byte[4096];
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static void writeByteArrayToFile(final File file, final byte[] data) throws IOException {
        new FileOutputStream(file, false).write(data, 0, (int) file.length());
    }
}
