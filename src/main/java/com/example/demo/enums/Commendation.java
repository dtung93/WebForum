package com.example.demo.enums;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
public enum Commendation {
  THUMBS_UP(1),
  THUMBS_DOWN(-1),
  MONEY(10),
  HEART(5);

  private final int value;
  Commendation(int value) {
    this.value = value;
  }
  public int getValue() {
    return value;
  }
}
