package me.pumpking.battleships.models;

import me.pumpking.battleships.exceptions.IllegalShipPositionException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    private Board board;

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    @Before
    public void before() {
        board = new Board(WIDTH, HEIGHT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithInvalidSize() {
        new Board(0, 0);
    }

    @Test
    public void clear() {
        board.setShipIDAt(0, 0, 1);
        board.clear();
        assertThat(board.getNextID()).isEqualTo(1);
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

    @Test(expected = IllegalArgumentException.class)
    public void getAreaSurroundingShipWithInvalidCoordinates() {
        board.getAreaSurroundingShip(-1, 0, 0, 0);
    }

    @Test
    public void getAreaSurroundingShip() {
        Area area = board.getAreaSurroundingShip(1, 1, 1, 3);
        assertThat(area.getXMin()).isEqualTo(0);
        assertThat(area.getYMin()).isEqualTo(0);
        assertThat(area.getXMax()).isEqualTo(2);
        assertThat(area.getYMax()).isEqualTo(4);
    }

    @Test
    public void getAreaSurroundingShipPlacedByTheWall() {
        Area area = board.getAreaSurroundingShip(0, 0, 0, 3);
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

    @Test(expected = IllegalArgumentException.class)
    public void placeShipWithInvalidCoordinates() throws IllegalShipPositionException {
        board.placeShip(-1, 0, Orientation.HORIZONTAL, ShipType.BATTLESHIP);
    }

    @Test(expected = NullPointerException.class)
    public void placeShipWithNullOrientation() throws IllegalShipPositionException {
        board.placeShip(0, 0, null, ShipType.BATTLESHIP);
    }

    @Test(expected = NullPointerException.class)
    public void placeShipWithNullShipType() throws IllegalShipPositionException {
        board.placeShip(0, 0, Orientation.HORIZONTAL, null);
    }

    @Test
    public void placeShipHorizontal() throws IllegalShipPositionException {
        board.placeShip(0, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);
        assertThat(board.getShipIDAt(0, 0)).isEqualTo(1);
        assertThat(board.getShipIDAt(1, 0)).isEqualTo(1);
    }

    @Test
    public void placeShipVertical() throws IllegalShipPositionException {
        board.placeShip(0, 0, Orientation.VERTICAL, ShipType.DESTROYER);
        assertThat(board.getShipIDAt(0, 0)).isEqualTo(1);
        assertThat(board.getShipIDAt(0, 1)).isEqualTo(1);
    }

    @Test
    public void placeShipCrossingBorderHorizontally() throws IllegalShipPositionException {
        board.placeShip(WIDTH - 1, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);
        assertThat(board.getShipIDAt(WIDTH - 1, 0)).isEqualTo(1);
        assertThat(board.getShipIDAt(0, 0)).isEqualTo(1);
    }

    @Test
    public void placeShipCrossingBorderVertically() throws IllegalShipPositionException {
        board.placeShip(0, HEIGHT - 1, Orientation.VERTICAL, ShipType.DESTROYER);
        assertThat(board.getShipIDAt(0, HEIGHT - 1)).isEqualTo(1);
        assertThat(board.getShipIDAt(0, 0)).isEqualTo(1);
    }

    @Test(expected = IllegalShipPositionException.class)
    public void placeShipCollidingWithAnotherShip() throws IllegalShipPositionException {
        board.placeShip(0, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);
        board.placeShip(2, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);
    }

}
