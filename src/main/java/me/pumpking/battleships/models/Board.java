package me.pumpking.battleships.models;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;

public class Board {

  @Getter
  private int width;

  @Getter
  private int height;

  @VisibleForTesting
  @Getter(AccessLevel.PACKAGE)
  private int currentID;

  @VisibleForTesting
  @Getter(AccessLevel.PACKAGE)
  private int[] fields;

  @Getter
  private Map<Integer, Ship> placedShips;

  public Board(int width, int height) {
    int largestShipSize = ShipType.getLargestShipSize();
    Preconditions.checkArgument(width >= largestShipSize && height >= largestShipSize,
        "Cannot create board smaller than the biggest ship size");

    this.currentID = 1;
    this.width = width;
    this.height = height;
    this.fields = new int[width * height];
    this.placedShips = new HashMap<>();
  }

  public void clear() {
    currentID = 1;
    placedShips.clear();
    Arrays.fill(fields, 0);
  }

  private void checkCoordinates(int x, int y) {
    Preconditions.checkArgument(x >= 0 && y >= 0, "Coordinates cannot be smaller than 0");
    Preconditions.checkArgument(x < getWidth() && y < getHeight(),
        "Coordinates cannot be larger than board");
  }

  public int getShipIDAt(int x, int y) {
    checkCoordinates(x, y);
    return fields[y * getWidth() + x];
  }

  public void setShipIDAt(int x, int y, int id) {
    checkCoordinates(x, y);
    fields[y * getWidth() + x] = id;
  }

  public void addShipToBoard(Ship ship) {
    ship.getParts().forEach(coords -> setShipIDAt(coords.getX(), coords.getY(), currentID));
    placedShips.put(currentID++, ship);
  }

  @VisibleForTesting
  Area getAreaAroundArea(Area area) {
    Preconditions.checkNotNull(area, "Cannot get surrounding area for null");

    int xMin = Math.max(area.getXMin() - 1, 0);
    int yMin = Math.max(area.getYMin() - 1, 0);
    int xMax = Math.min(area.getXMax() + 1, getWidth() - 1);
    int yMax = Math.min(area.getYMax() + 1, getHeight() - 1);
    return new Area(xMin, yMin, xMax, yMax);
  }

  @VisibleForTesting
  boolean isAreaInBoardEmpty(Area area) {
    Preconditions.checkNotNull(area, "Cannot check null area");

    for (int y = area.getYMin(); y < area.getYMax(); y++) {
      for (int x = area.getXMin(); x < area.getXMax(); x++) {
        if (getShipIDAt(x, y) != 0) {
          return false;
        }
      }
    }
    return true;
  }

}
