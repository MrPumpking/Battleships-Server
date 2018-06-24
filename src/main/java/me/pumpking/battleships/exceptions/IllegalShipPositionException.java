package me.pumpking.battleships.exceptions;

public class IllegalShipPositionException extends Exception {

  public IllegalShipPositionException(int x, int y) {
    super(String.format("Cannot place a ship at given coordinates: [%d, %d]", x, y));
  }

}
