package me.pumpking.battleships.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class CoordinatesTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.forClass(Coordinates.class).verify();
  }

}
