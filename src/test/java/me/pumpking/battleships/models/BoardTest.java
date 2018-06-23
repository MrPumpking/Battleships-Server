package me.pumpking.battleships.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

  private Board board;

  public static final int WIDTH = 10;
  public static final int HEIGHT = 10;

  public static Board createBoardMock(int width, int height) {
    Board board = spy(Board.class);
    int[] fields = new int[width * height];
    when(board.getWidth()).thenReturn(width);
    when(board.getHeight()).thenReturn(height);
    when(board.getFields()).thenReturn(fields);
    board.clear();
    return board;
  }

  @Before
  public void before() {
    board = new Board(WIDTH, HEIGHT);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createWithInvalidSize() {
    new Board(0, 0);
  }

  @Test
  public void checkInstantiation() {
    assertThat(board.getCurrentID()).isEqualTo(1);
    assertThat(board.getPlacedShips()).isNotNull();
    assertThat(board.getFields()).hasSize(WIDTH * HEIGHT);
  }

  @Test
  public void checkInstantiationForEmptyConstructor() {
    assertThat(board.getCurrentID()).isEqualTo(1);
    assertThat(board.getPlacedShips()).isNotNull();
  }

  @Test
  public void checkIfBoardGetsCleared() {
    board.setShipIDAt(0, 0, 1);
    board.clear();
    assertThat(board.getCurrentID()).isEqualTo(1);
    assertThat(board.getPlacedShips()).isEmpty();
    assertThat(board.getFields()).containsOnly(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setShipIDAtInvalidCoordinates() {
    board.setShipIDAt(-1, -1, 1);
  }

  @Test
  public void setShipIDAt() {
    board.setShipIDAt(0, 0, 1);
    assertThat(board.getFields()[0]).isEqualTo(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getShipIDAtInvalidCoordinates() {
    board.getShipIDAt(-1, -1);
  }

  @Test
  public void getShipIDAt() {
    board.getFields()[0] = 1;
    assertThat(board.getShipIDAt(0, 0)).isEqualTo(1);
  }

  @Test(expected = NullPointerException.class)
  public void getAreaAroundAreaWithNull() {
    board.getAreaAroundArea(null);
  }

  @Test
  public void getAreaAroundArea() {
    Area area = board.getAreaAroundArea(new Area(1, 1, 1, 3));
    assertThat(area.getXMin()).isEqualTo(0);
    assertThat(area.getYMin()).isEqualTo(0);
    assertThat(area.getXMax()).isEqualTo(2);
    assertThat(area.getYMax()).isEqualTo(4);
  }

  @Test
  public void getAreaAroundAreaAdjacentToBoardBorder() {
    Area area = board.getAreaAroundArea(new Area(0, 0, 0, 3));
    assertThat(area.getXMin()).isEqualTo(0);
    assertThat(area.getYMin()).isEqualTo(0);
    assertThat(area.getXMax()).isEqualTo(1);
    assertThat(area.getYMax()).isEqualTo(4);
  }

  @Test(expected = NullPointerException.class)
  public void isAreaInBoardEmptyWithNull() {
    board.isAreaInBoardEmpty(null);
  }

  @Test
  public void isAreaInBoardEmpty() {
    Area area = new Area(0, 0, WIDTH, HEIGHT);
    assertThat(board.isAreaInBoardEmpty(area)).isEqualTo(true);
  }

  @Test
  public void isAreaInBoardEmptyWithFieldsSet() {
    Area area = new Area(0, 0, WIDTH, HEIGHT);
    board.getFields()[1] = 1;
    assertThat(board.isAreaInBoardEmpty(area)).isEqualTo(false);
  }

  @Test(expected = NullPointerException.class)
  public void addNullShipToBoard() {
    board.addShipToBoard(null);
  }

  @Test
  public void addShipToBoard() {
    Ship ship = mock(Ship.class);
    when(ship.getParts()).thenReturn(Arrays.asList(
        new Coordinates(0, 0),
        new Coordinates(1, 0),
        new Coordinates(2, 0)
    ));
    board.addShipToBoard(ship);
    assertThat(board.getShipIDAt(0, 0)).isEqualTo(1);
    assertThat(board.getShipIDAt(1, 0)).isEqualTo(1);
    assertThat(board.getShipIDAt(2, 0)).isEqualTo(1);
    assertThat(board.getPlacedShips()).containsKeys(1);
    assertThat(board.getPlacedShips().get(1)).isEqualTo(ship);
  }

}
