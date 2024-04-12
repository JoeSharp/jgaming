package uk.co.joesharpcs.gaming.monopoly;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

  private List<BoardLocation> spaces;
  private Map<String, Property> properties;

  public Board() {
    this.initializeBoard();
  }

  private void initializeBoard() {
    this.spaces =
        Stream.of(
                BoardLocation.Type.GO.create(),
                BoardLocation.Type.PROPERTY.create("Burger Joint"),
                BoardLocation.Type.PROPERTY.create("Pizza Place"),
                BoardLocation.Type.CHANCE_CARD.create(),
                BoardLocation.Type.PROPERTY.create("Sweet Shop"),
                BoardLocation.Type.PROPERTY.create("Ice Cream Parlour"),
                BoardLocation.Type.JAIL.create(),
                BoardLocation.Type.PROPERTY.create("Museum"),
                BoardLocation.Type.PROPERTY.create("Library"),
                BoardLocation.Type.CHANCE_CARD.create(),
                BoardLocation.Type.PROPERTY.create("Skate Park"),
                BoardLocation.Type.PROPERTY.create("Swimming Pool"),
                BoardLocation.Type.FREE_PARKING.create(),
                BoardLocation.Type.PROPERTY.create("Arcade"),
                BoardLocation.Type.PROPERTY.create("Cinema"),
                BoardLocation.Type.CHANCE_CARD.create(),
                BoardLocation.Type.PROPERTY.create("Toy Store"),
                BoardLocation.Type.PROPERTY.create("Pet Store"),
                BoardLocation.Type.GO_TO_JAIL.create(),
                BoardLocation.Type.PROPERTY.create("Bowling Alley"),
                BoardLocation.Type.PROPERTY.create("Zoo"),
                BoardLocation.Type.CHANCE_CARD.create(),
                BoardLocation.Type.PROPERTY.create("Amusement Park"),
                BoardLocation.Type.PROPERTY.create("Boardwalk"))
            .toList();
    this.properties =
        Stream.of(
                new Property(Property.Colour.BROWN, "Burger Joint", 1),
                new Property(Property.Colour.BROWN, "Pizza Place", 1),
                new Property(Property.Colour.LIGHT_BLUE, "Sweet Shop", 1),
                new Property(Property.Colour.LIGHT_BLUE, "Ice Cream Parlour", 1),
                new Property(Property.Colour.PINK, "Museum", 2),
                new Property(Property.Colour.PINK, "Library", 2),
                new Property(Property.Colour.ORANGE, "Skate Park", 2),
                new Property(Property.Colour.ORANGE, "Swimming Pool", 2),
                new Property(Property.Colour.RED, "Arcade", 3),
                new Property(Property.Colour.RED, "Cinema", 3),
                new Property(Property.Colour.YELLOW, "Toy Store", 3),
                new Property(Property.Colour.YELLOW, "Pet Store", 3),
                new Property(Property.Colour.GREEN, "Bowling Alley", 4),
                new Property(Property.Colour.GREEN, "Zoo", 4),
                new Property(Property.Colour.NAVY_BLUE, "Amusement Park", 5),
                new Property(Property.Colour.NAVY_BLUE, "Boardwalk", 5))
            .collect(Collectors.toMap(Property::name, d -> d));
  }
}
