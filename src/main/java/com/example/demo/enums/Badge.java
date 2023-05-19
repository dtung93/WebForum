package com.example.demo.enums;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
public enum Badge {
  SILVER("Silver Member"),
  GOLD("Gold Member"),
  PLATINUM("Platinum Member"),
  MOD("Moderator"),
  ADMIN("Administrator");

  private final String label;
  Badge (String label) {
      this.label = label;
  }
  public String getLabel () {
    return label;
  }

}
