package com.hcmus.Utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CryptoUtil {
   public static String encodeWithBase64(String text) {
      return Base64.getEncoder().encodeToString(text.getBytes());
   }
   public static String decodeWithBase64(String encodedString) {
      byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
      return new String(decodedBytes);
   }
   public static String encryptWithSHA256(String text) {
      return Hashing.sha256()
            .hashString(text, StandardCharsets.UTF_8)
            .toString();
   }
}
