package me.pumpking.battleships.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public enum ShipType {
  NONE(0, 0),
  CARRIER(1, 5),
  BATTLESHIP(2, 4),
  CRUISER(3, 3),
  SUBMARINE(4, 3),
  DESTROYER(5, 2);

  private int id;
  private int size;

  ShipType(int id, int size) {
    this.id = id;
    this.size = size;
  }

  public int getId() {
    return id;
  }

  public int getSize() {
    return size;
  }

  public static int getLargestShipSize() {
    return Collections
        .max(Arrays.asList(values()), Comparator.comparing(ShipType::getSize)).size;
  }

}
