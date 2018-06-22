package me.pumpking.battleships.models;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import me.pumpking.battleships.exceptions.IllegalShipPositionException;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private int width;
    private int height;
    private int nextID;
    private int[] fields;
    private Map<Integer, Ship> placedShips;

    public Board(int width, int height) {
        int largestShipSize = ShipType.getLargestShipSize();
        Preconditions.checkArgument(width >= largestShipSize && height >= largestShipSize, "Cannot create board smaller than the biggest ship size");

        this.nextID = 1;
        this.width = width;
        this.height = height;
        this.fields = new int[width * height];
        this.placedShips = new HashMap<>();
    }

    public void clear() {
        nextID = 1;
        placedShips.clear();

        for (int i = 0; i < fields.length; i++) {
            fields[i] = 0;
        }
    }

    public int getShipIDAt(int x, int y) {
        Preconditions.checkArgument(x >= 0 && y >= 0 && x < width && y < height, "Invalid coordinates");
        return fields[y * width + x];
    }

    public void setShipIDAt(int x, int y, int id) {
        Preconditions.checkArgument(x >= 0 && y >= 0 && x < width && y < height, "Invalid coordinates");
        fields[y * width + x] = id;
    }

    @VisibleForTesting
    Area getAreaSurroundingShip(int shipXStart, int shipYStart, int shipXEnd, int shipYEnd) {
        Preconditions.checkArgument(shipXStart >= 0 && shipYStart >= 0 && shipXEnd < width && shipYEnd < height, "Invalid coordinates");

        int xMin = Math.max(shipXStart - 1, 0);
        int yMin = Math.max(shipYStart - 1, 0);
        int xMax = Math.min(shipXEnd + 1, width - 1);
        int yMax = Math.min(shipYEnd + 1, height - 1);
        return new Area(xMin, yMin, xMax, yMax);
    }

    @VisibleForTesting
    boolean isAreaInBoardEmpty(Area area) {
        Preconditions.checkNotNull(area, "Cannot check null area");

        for (int y = area.getYMin(); y < area.getYMax(); y++) {
            for (int x = area.getXMin(); x < area.getXMax(); x++) {
                if (getShipIDAt(x, y) != 0) return false;
            }
        }
        return true;
    }

    public void placeShip(int xStart, int yStart, Orientation orientation, ShipType shipType) throws IllegalShipPositionException {
        Preconditions.checkNotNull(shipType, "ShipType cannot be null");
        Preconditions.checkNotNull(orientation, "Orientation cannot be null");
        Preconditions.checkArgument(xStart >= 0 && yStart >= 0 && xStart < width && yStart < height, "Invalid coordinates");

        boolean isPlacedCorrectly;
        boolean isCrossingTheBorder = false;
        int additionalShipParts = shipType.getSize() - 1;
        boolean isVertical = orientation == Orientation.VERTICAL;

        int xEnd = (isVertical ? xStart : xStart + additionalShipParts);
        int yEnd = (isVertical ? yStart + additionalShipParts : yStart);

        if (xEnd >= width || yEnd >= height) {
            isCrossingTheBorder = true;

            xEnd = (isVertical ? xEnd : xEnd - width);
            yEnd = (isVertical ? yEnd - height : yEnd);

            Area firstPart = getAreaSurroundingShip(xStart, yStart, (isVertical ? xStart : width - 1), (isVertical ? height - 1 : yStart));
            Area secondPart = getAreaSurroundingShip(xEnd, yEnd, (isVertical ? xEnd : 0), (isVertical ? 0 : yEnd));

            isPlacedCorrectly = isAreaInBoardEmpty(firstPart) && isAreaInBoardEmpty(secondPart);

        } else {
            isPlacedCorrectly = isAreaInBoardEmpty(getAreaSurroundingShip(xStart, yStart, xEnd, yEnd));
        }

        if (!isPlacedCorrectly) {
            throw new IllegalShipPositionException(xStart, yStart);
        }

        Ship ship = new Ship();

        if (!isCrossingTheBorder) {
            for (int i = 0; i < shipType.getSize(); i++) {
                ship.addPart(new Coordinates((isVertical ? xStart : xStart++), (isVertical ? yStart++ : yStart)));
            }
        } else {
            int firstHalfParts = (isVertical ? height - yStart : width - xStart);
            int secondHalfParts = (isVertical ? yEnd : xEnd) + 1;

            for (int i = 0; i < firstHalfParts; i++) {
                ship.addPart(new Coordinates((isVertical ? xStart : xStart++), (isVertical ? yStart++ : yStart)));
            }

            for (int i = 0; i < secondHalfParts; i++) {
                ship.addPart(new Coordinates((isVertical ? xEnd : xEnd--), (isVertical ? yEnd-- : yEnd)));
            }
        }

        ship.getParts().forEach(coordinates -> setShipIDAt(coordinates.getX(), coordinates.getY(), nextID));
        placedShips.put(nextID, ship);
        nextID++;
    }

    public void print() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(getShipIDAt(x, y) + " ");
            }
            System.out.println();
        }
    }

    @VisibleForTesting
    int[] getFields() {
        return fields;
    }

    @VisibleForTesting
    int getNextID() {
        return nextID;
    }

    @VisibleForTesting
    Map<Integer, Ship> getPlacedShips() {
        return placedShips;
    }

}
