package me.pumpking.battleships.models;

import lombok.Getter;

public enum Orientation {
  VERTICAL("vertical"),
  HORIZONTAL("horizontal");

  @Getter
  private String value;

  Orientation(String value) {
    this.value = value;
  }

}
