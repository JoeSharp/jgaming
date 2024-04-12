package uk.co.joesharpcs.gaming.monopoly;

public record BoardLocation(Type type, String name) {
  public enum Type {
    GO,
    PROPERTY,
    CHANCE_CARD,
    GO_TO_JAIL,
    FREE_PARKING,
    JAIL;

    public BoardLocation create(String name) {
      return new BoardLocation(this, name);
    }

    public BoardLocation create() {
      return new BoardLocation(this, this.name());
    }
  }
}
