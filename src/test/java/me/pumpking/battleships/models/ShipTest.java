package me.pumpking.battleships.models;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShipTest {

    private Ship ship;

    @Before
    public void before() {
        this.ship = new Ship();
    }

    @Test
    public void removePartWithCoordinatesObject() {
        ship.addPart(new Coordinates(1, 1));
        ship.removePart(new Coordinates(1, 1));
        assertThat(ship.getParts()).isEmpty();
    }

    @Test
    public void removePartWithCoordinates() {
        ship.addPart(new Coordinates(1, 1));
        ship.removePart(1, 1);
        assertThat(ship.getParts()).isEmpty();
    }

}
