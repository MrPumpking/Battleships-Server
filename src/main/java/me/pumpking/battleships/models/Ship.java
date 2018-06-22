package me.pumpking.battleships.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Ship {

    private List<Coordinates> parts;

    public Ship() {
        this.parts = new ArrayList<>();
    }

    public void addPart(Coordinates coordinates) {
        parts.add(coordinates);
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
