package uk.co.joesharpcs.gaming.go;

public interface StringUtils {
    static boolean isNotBlank(String value) {
        return !value.isBlank();
    }
}
