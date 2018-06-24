package me.pumpking.battleships.controllers;

import com.google.common.annotations.VisibleForTesting;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.pumpking.battleships.exceptions.IllegalShipPositionException;
import me.pumpking.battleships.models.Board;
import me.pumpking.battleships.models.Orientation;
import me.pumpking.battleships.models.Player;
import me.pumpking.battleships.models.Ship;
import me.pumpking.battleships.models.ShipType;

@AllArgsConstructor
public class PlayerController {

  @VisibleForTesting
  @Getter(AccessLevel.PACKAGE)
  private Player player;

  public void placeShip(int xStart, int yStart, Orientation orientation, ShipType shipType)
      throws IllegalShipPositionException {

    Board board = player.getBoard();
    Ship ship = new Ship(board, xStart, yStart, orientation, shipType);

    if (!ship.isPlacedCorrectly()) {
      throw new IllegalShipPositionException(xStart, yStart);
    }

    ship.populateParts();
    board.addShipToBoard(ship);
  }

}
