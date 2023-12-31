package org.cinema.utils;

import jakarta.enterprise.context.ApplicationScoped;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.xml.bind.DatatypeConverter;

@ApplicationScoped
public class CommonUtils {

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

  public static List<byte[]> listUuidToListBytesArray(List<UUID> uuids) {
    List<byte[]> list = new ArrayList<>();
    for (UUID uuid : uuids) {
      list.add(uuidToBytesArray(uuid));
    }

    return list;
  }

  public static List<UUID> convertStringToListUUID(String uuids) {
    List<UUID> uuidList = new ArrayList<>();
    String[] uuidStrings = uuids.split("#");

    for (String uuidStr : uuidStrings) {
      try {
        UUID uuid = UUID.fromString(uuidStr.trim());
        uuidList.add(uuid);
      } catch (IllegalArgumentException e) {
        // Handle invalid UUID format
        System.err.println("Invalid UUID format: " + uuidStr);
      }
    }

    return uuidList;
  }
}
