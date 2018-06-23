package me.pumpking.battleships.models;

public enum Orientation {
  VERTICAL("vertical"),
  HORIZONTAL("horizontal");

  private String value;

  Orientation(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
