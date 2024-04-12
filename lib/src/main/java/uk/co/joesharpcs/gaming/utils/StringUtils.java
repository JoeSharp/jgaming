package uk.co.joesharpcs.gaming.utils;

public interface StringUtils {
  static boolean isNotBlank(String value) {
    return !value.isBlank();
  }
}
