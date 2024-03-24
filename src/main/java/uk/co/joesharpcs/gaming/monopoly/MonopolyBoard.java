package uk.co.joesharpcs.gaming.monopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import uk.co.joesharpcs.gaming.monopoly.spaces.*;

public class MonopolyBoard {

  private List<MonopolyBoardSpace> spaces;

  public MonopolyBoard() {
    this.spaces = new ArrayList<>();

    this.initializeBoard();
  }

  private void initializeBoard() {
    Stream.of(
            new PassGo(),
            new PropertySpace(Colour.BROWN, "Burger Joint", 1),
            new PropertySpace(Colour.BROWN, "Pizza Place", 1),
            new ChanceCard(),
            new PropertySpace(Colour.LIGHT_BLUE, "Sweet Shop", 1),
            new PropertySpace(Colour.LIGHT_BLUE, "Ice Cream Parlour", 1),
            new TimeoutZone(),
            new PropertySpace(Colour.PINK, "Museum", 2),
            new PropertySpace(Colour.PINK, "Library", 2),
            new ChanceCard(),
            new PropertySpace(Colour.ORANGE, "Skate Park", 2),
            new PropertySpace(Colour.ORANGE, "Swimming Pool", 2),
            new FreeParking(),
            new PropertySpace(Colour.RED, "Arcade", 3),
            new PropertySpace(Colour.RED, "Cinema", 3),
            new ChanceCard(),
            new PropertySpace(Colour.YELLOW, "Toy Store", 3),
            new PropertySpace(Colour.YELLOW, "Pet Store", 3),
            new GoToTimeout(),
            new PropertySpace(Colour.GREEN, "Bowling Alley", 4),
            new PropertySpace(Colour.GREEN, "Zoo", 4),
            new ChanceCard(),
            new PropertySpace(Colour.NAVY_BLUE, "Amusement Park", 5),
            new PropertySpace(Colour.NAVY_BLUE, "Boardwalk", 5))
        .forEach(this.spaces::add);
  }
}
