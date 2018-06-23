package me.pumpking.battleships.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
class Area {

  @Getter
  private int xMin;

  @Getter
  private int yMin;

  @Getter
  private int xMax;

  @Getter
  private int yMax;

}
