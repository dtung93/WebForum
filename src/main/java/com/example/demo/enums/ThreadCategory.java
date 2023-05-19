package com.example.demo.enums;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
public enum ThreadCategory {
  TECH("Technology Discussion"),
  NEWS("National and International News"),
  REVIEW("Product and Service reviews"),
  ANNOUNCEMENT("Administrator announcement"),
  NATURE("Nature and Geography"),
  GAME("Video games"),
  SPORTS("Sports"),
  STUDIES("Studies and research"),
  HEALTH("Health and Life"),
  STORIES("Personal and sharing"),
  GENERAL("General discussion"),
  TRADE("Buying and Selling");
  private final String label;

  ThreadCategory(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
