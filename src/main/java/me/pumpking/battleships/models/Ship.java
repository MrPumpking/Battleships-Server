package me.pumpking.battleships.models;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;

public class Ship {

  @Getter
  private boolean isCrossingBorder;

  @Getter
  private Coordinates endCoords;

  @Getter
  private Coordinates startCoords;

  @VisibleForTesting
  @Getter(AccessLevel.PACKAGE)
  private Board board;

  @VisibleForTesting
  @Getter(AccessLevel.PACKAGE)
  private ShipType shipType;

  @VisibleForTesting
  @Getter(AccessLevel.PACKAGE)
  private Orientation orientation;

  private boolean isVertical;
  private List<Coordinates> parts;

  public Ship(Board board, int xStart, int yStart, Orientation orientation, ShipType shipType) {
    Preconditions.checkNotNull(board, "Board cannot be null");
    Preconditions.checkNotNull(shipType, "ShipType cannot be null");
    Preconditions.checkNotNull(orientation, "Orientation cannot be null");
    Preconditions.checkArgument(xStart >= 0 && yStart >= 0, "Start coordinates have to be >= 0");
    Preconditions.checkArgument(xStart < board.getWidth() && yStart < board.getHeight(),
        "Start coordinates have to be < board size");

    this.board = board;
    this.shipType = shipType;
    this.orientation = orientation;
    this.parts = new ArrayList<>();
    this.startCoords = new Coordinates(xStart, yStart);
    this.isVertical = orientation == Orientation.VERTICAL;

    calculateEndCoordinates();
  }

  private void calculateEndCoordinates() {
    int additionalShipParts = shipType.getSize() - 1;

    int xEnd = (isVertical ? startCoords.getX() : startCoords.getX() + additionalShipParts);
    int yEnd = (isVertical ? startCoords.getY() + additionalShipParts : startCoords.getY());

    if (xEnd >= board.getWidth() || yEnd >= board.getHeight()) {
      isCrossingBorder = true;
      xEnd = (isVertical ? xEnd : xEnd - board.getWidth());
      yEnd = (isVertical ? yEnd - board.getHeight() : yEnd);
    }

    endCoords = new Coordinates(xEnd, yEnd);
  }

  public boolean isPlacedCorrectly() {
    if (isCrossingBorder) {
      Area firstPart = new Area(startCoords.getX(), startCoords.getY(),
          (isVertical ? startCoords.getX() : board.getWidth() - 1),
          (isVertical ? board.getHeight() - 1 : startCoords.getY()));

      Area secondPart = new Area(endCoords.getX(), endCoords.getY(),
          (isVertical ? endCoords.getX() : 0),
          (isVertical ? 0 : endCoords.getY()));

      Area firstPartSurroundings = board.getAreaAroundArea(firstPart);
      Area secondPartSurroundings = board.getAreaAroundArea(secondPart);

      return (board.isAreaInBoardEmpty(firstPartSurroundings) && board
          .isAreaInBoardEmpty(secondPartSurroundings));
    }

    Area shipArea = new Area(startCoords.getX(), startCoords.getY(), endCoords.getX(),
        endCoords.getY());

    return board.isAreaInBoardEmpty(board.getAreaAroundArea(shipArea));
  }

  public void populateParts() {
    int xStart = startCoords.getX();
    int yStart = startCoords.getY();

    if (!isCrossingBorder) {
      for (int i = 0; i < shipType.getSize(); i++) {
        parts.add(
            new Coordinates((isVertical ? xStart : xStart++), (isVertical ? yStart++ : yStart)));
      }

    } else {
      int xEnd = endCoords.getX();
      int yEnd = endCoords.getY();

      int firstHalfParts = (isVertical ? board.getHeight() - startCoords.getY()
          : board.getWidth() - startCoords.getX());
      int secondHalfParts = (isVertical ? endCoords.getY() : endCoords.getX()) + 1;

      for (int i = 0; i < firstHalfParts; i++) {
        parts.add(
            new Coordinates((isVertical ? xStart : xStart++), (isVertical ? yStart++ : yStart)));
      }

      for (int i = 0; i < secondHalfParts; i++) {
        parts.add(new Coordinates((isVertical ? xEnd : xEnd--), (isVertical ? yEnd-- : yEnd)));
      }
    }
  }

  public void removePart(Coordinates coordinates) {
    parts.remove(coordinates);
  }

  public void removePart(int x, int y) {
    parts.removeIf(coordinates -> coordinates.getX() == x && coordinates.getY() == y);
  }

  public boolean hasSunk() {
    return parts.isEmpty();
  }

  public List<Coordinates> getParts() {
    return Collections.unmodifiableList(parts);
  }

}
