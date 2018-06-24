package me.pumpking.battleships.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ShipTest {

  private Ship ship;
  private Board board;

  private static final ShipType SHIP_TYPE = ShipType.DESTROYER;

  @Before
  public void before() {
    int boardSize = ShipType.getLargestShipSize() + 1;
    board = new Board(boardSize, boardSize);
    ship = new Ship(board, 0, 0, Orientation.HORIZONTAL, SHIP_TYPE);
    ship.populateParts();
  }

  @Test
  public void checkInstantiation() {
    assertThat(ship.getBoard()).isNotNull();
    assertThat(ship.getParts()).isNotNull();
    assertThat(ship.getStartCoords()).isEqualTo(new Coordinates(0, 0));
    assertThat(ship.getOrientation()).isNotNull();
    assertThat(ship.getShipType()).isNotNull();
  }

  @Test
  public void checkEndCoordinatesCalculation() {
    assertThat(ship.getEndCoords())
        .isEqualTo(new Coordinates(SHIP_TYPE.getSize() - 1, 0));
  }

  @Test
  public void checkEndCoordsCalculationWhenShipCrossesBorderHorizontally() {
    if (ShipType.getLargestShipSize() == 1) {
      return;
    }

    Ship ship = new Ship(board, 2, 0, Orientation.HORIZONTAL, ShipType.getLargestShipType());
    assertThat(ship.getEndCoords()).isEqualTo(new Coordinates(0, 0));
  }

  @Test
  public void checkEndCoordsCalculationWhenShipCrossesBorderVertically() {
    if (ShipType.getLargestShipSize() == 1) {
      return;
    }

    Ship ship = new Ship(board, 0, 2, Orientation.VERTICAL, ShipType.getLargestShipType());
    assertThat(ship.getEndCoords()).isEqualTo(new Coordinates(0, 0));
  }

  @Test
  public void checkPartPopulationWhenNotCrossingTheBorder() {
    Ship ship = new Ship(board, 0, 0, Orientation.HORIZONTAL, ShipType.SUBMARINE);
    ship.populateParts();

    assertThat(ship.getParts()).containsExactly(
        new Coordinates(0, 0),
        new Coordinates(1, 0),
        new Coordinates(2, 0)
    );
  }

  @Test
  public void checkPartPopulationWhenCrossingTheBorder() {
    Ship ship = new Ship(board, board.getWidth() - 2, 0, Orientation.HORIZONTAL,
        ShipType.SUBMARINE);
    ship.populateParts();

    assertThat(ship.getParts()).containsExactly(
        new Coordinates(board.getWidth() - 2, 0),
        new Coordinates(board.getWidth() - 1, 0),
        new Coordinates(0, 0)
    );
  }

  @Test
  public void removePartWithCoordinatesObject() {
    ship.removePart(new Coordinates(ship.getStartCoords().getX(), ship.getStartCoords().getY()));
    assertThat(ship.getParts()).hasSize(SHIP_TYPE.getSize() - 1);
  }

  @Test
  public void removePartWithCoordinates() {
    ship.removePart(ship.getStartCoords().getX(), ship.getStartCoords().getY());
    assertThat(ship.getParts()).hasSize(SHIP_TYPE.getSize() - 1);
  }

  @Test
  public void checkIfPlacementPassesWhenShipDoesntCrossBorder() {
    assertThat(ship.isPlacedCorrectly()).isEqualTo(true);
  }

  @Test
  public void checkIfPlacementPassesWhenShipCrossedBorder() {
    Ship ship = new Ship(board, 0, 2, Orientation.VERTICAL, ShipType.getLargestShipType());
    assertThat(ship.isPlacedCorrectly()).isEqualTo(true);
  }

  @Test
  public void checkIfPlacementDoesntPassWhenShipCollidesWithAnotherShip() {
    board.addShipToBoard(ship);
    Ship ship2 = new Ship(board, 0, 1, Orientation.HORIZONTAL, SHIP_TYPE);
    assertThat(ship2.isPlacedCorrectly()).isEqualTo(false);
  }

}
