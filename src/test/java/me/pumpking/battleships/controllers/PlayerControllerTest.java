package me.pumpking.battleships.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import me.pumpking.battleships.exceptions.IllegalShipPositionException;
import me.pumpking.battleships.models.Board;
import me.pumpking.battleships.models.Orientation;
import me.pumpking.battleships.models.Player;
import me.pumpking.battleships.models.ShipType;
import org.junit.Before;
import org.junit.Test;

public class PlayerControllerTest {

  private PlayerController controller;

  private static final int BOARD_WIDTH = 10;
  private static final int BOARD_HEIGHT = 10;

  @Before
  public void before() {
    Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
    Player player = mock(Player.class);
    when(player.getBoard()).thenReturn(board);

    this.controller = new PlayerController(player);
  }

  @Test(expected = IllegalArgumentException.class)
  public void placeShipWithInvalidCoordinates() throws IllegalShipPositionException {
    controller.placeShip(-1, 0, Orientation.HORIZONTAL, ShipType.BATTLESHIP);
  }

  @Test(expected = NullPointerException.class)
  public void placeShipWithNullOrientation() throws IllegalShipPositionException {
    controller.placeShip(0, 0, null, ShipType.BATTLESHIP);
  }

  @Test(expected = NullPointerException.class)
  public void placeShipWithNullShipType() throws IllegalShipPositionException {
    controller.placeShip(0, 0, Orientation.HORIZONTAL, null);
  }

  @Test
  public void placeShipHorizontal() throws IllegalShipPositionException {
    controller.placeShip(0, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);
    assertThat(controller.getPlayer().getBoard().getShipIDAt(0, 0)).isEqualTo(1);
    assertThat(controller.getPlayer().getBoard().getShipIDAt(1, 0)).isEqualTo(1);
  }

  @Test
  public void placeShipVertical() throws IllegalShipPositionException {
    controller.placeShip(0, 0, Orientation.VERTICAL, ShipType.DESTROYER);
    assertThat(controller.getPlayer().getBoard().getShipIDAt(0, 0)).isEqualTo(1);
    assertThat(controller.getPlayer().getBoard().getShipIDAt(0, 1)).isEqualTo(1);
  }

  @Test
  public void placeShipCrossingBorderHorizontally() throws IllegalShipPositionException {
    controller.placeShip(BOARD_WIDTH - 1, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);
    assertThat(controller.getPlayer().getBoard().getShipIDAt(BOARD_WIDTH - 1, 0)).isEqualTo(1);
    assertThat(controller.getPlayer().getBoard().getShipIDAt(0, 0)).isEqualTo(1);
  }

  @Test
  public void placeShipCrossingBorderVertically() throws IllegalShipPositionException {
    controller.placeShip(0, BOARD_HEIGHT - 1, Orientation.VERTICAL, ShipType.DESTROYER);
    assertThat(controller.getPlayer().getBoard().getShipIDAt(0, BOARD_HEIGHT - 1)).isEqualTo(1);
    assertThat(controller.getPlayer().getBoard().getShipIDAt(0, 0)).isEqualTo(1);
  }

  @Test(expected = IllegalShipPositionException.class)
  public void placeShipCollidingWithAnotherShip() throws IllegalShipPositionException {
    controller.placeShip(0, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);
    controller.placeShip(2, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);
  }


}
