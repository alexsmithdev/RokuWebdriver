package org.alexsmith.utilities;

import java.nio.charset.StandardCharsets;

public class Base64 {

    public static String decode(String base64String) {
        byte[] decoded = java.util.Base64.getDecoder().decode(base64String);
        return new String(decoded);
    }
}
