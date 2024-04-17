package uk.co.joesharpcs.gaming.gol;

import java.util.HashMap;
import java.util.Map;

public abstract class GolPatterns {

  public static final Map<String, String> NAMED_PATTERNS = new HashMap<>();

  public static final String BLOCK =
      """
            ....
            .00.
            .00.
            ....
            """;

  public static final String BEE_HIVE =
      """
            ......
            ..00..
            .0..0.
            ..00..
            ......
            """;

  public static final String LOAF =
      """
            ......
            ..00..
            .0..0.
            ..0.0.
            ...0..
            ......
            """;
  public static final String BOAT =
      """
            .....
            .00..
            .0.0.
            ..0..
            .....
            """;
  public static final String TUB =
      """
            .....
            ..0..
            .0.0.
            ..0..
            .....
            """;
  public static final String BLINKER =
      """
            .....
            ..0..
            ..0..
            ..0..
            .....
            """;
  public static final String TOAD =
      """
            ......
            ......
            ..000.
            .000..
            ......
            ......
            """;
  public static final String BEACON =
      """
            ......
            .00...
            .0....
            ....0.
            ...00.
            ......
            """;

  static {
    NAMED_PATTERNS.put("block", BLOCK);
    NAMED_PATTERNS.put("beehive", BEE_HIVE);
    NAMED_PATTERNS.put("loaf", LOAF);
    NAMED_PATTERNS.put("boat", BOAT);
    NAMED_PATTERNS.put("tub", TUB);
    NAMED_PATTERNS.put("blinker", BLINKER);
    NAMED_PATTERNS.put("toad", TOAD);
    NAMED_PATTERNS.put("beacon", BEACON);
  }
}
