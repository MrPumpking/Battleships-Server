package me.pumpking.battleships.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public final class Coordinates {

  @Getter
  private final int x;

  @Getter
  private final int y;

}
