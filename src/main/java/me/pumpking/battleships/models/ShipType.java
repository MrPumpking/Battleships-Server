package me.pumpking.battleships.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import lombok.Getter;

public enum ShipType {
  NONE(0, 0),
  CARRIER(1, 5),
  BATTLESHIP(2, 4),
  CRUISER(3, 3),
  SUBMARINE(4, 3),
  DESTROYER(5, 2);

  @Getter
  private int id;

  @Getter
  private int size;

  ShipType(int id, int size) {
    this.id = id;
    this.size = size;
  }

  public static ShipType getLargestShipType() {
    return Collections.max(Arrays.asList(values()), Comparator.comparing(ShipType::getSize));
  }

  public static int getLargestShipSize() {
    return getLargestShipType().size;
  }

}
