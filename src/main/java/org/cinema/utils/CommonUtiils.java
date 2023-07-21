package org.cinema.utils;

import jakarta.enterprise.context.ApplicationScoped;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;
import javax.xml.bind.DatatypeConverter;

@ApplicationScoped
public class CommonUtiils {

  // Password encoder
  public static String hashPassword(String password) {
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      byte[] digest = md.digest();
      return DatatypeConverter.printHexBinary(digest);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] uuidToBytesArray(UUID uuid) {
    if (Objects.isNull(uuid)) return null;
    long mostSigBits = uuid.getMostSignificantBits();
    long leastSigBits = uuid.getLeastSignificantBits();

    byte[] byteArray = new byte[16];
    for (int i = 0; i < 8; i++) {
      byteArray[i] = (byte) (mostSigBits >>> 8 * (7 - i));
    }
    for (int i = 8; i < 16; i++) {
      byteArray[i] = (byte) (leastSigBits >>> 8 * (7 - i));
    }

    return byteArray;
  }
}
