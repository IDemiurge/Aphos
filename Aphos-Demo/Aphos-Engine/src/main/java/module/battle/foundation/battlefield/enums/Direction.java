package module.battle.foundation.battlefield.enums;

import system.utils.EnumFinder;

/**
 * Created by Alexander on 11/20/2023
 */
public enum Direction {
    UP(false, 90, true, null, false),
    DOWN(false, 270, true, null, true),
    LEFT(false, 180, false, false, null),
    RIGHT(false, 360, false, true, null),
    UP_LEFT(true, 135, true, false, false),
    UP_RIGHT(true, 45, true, true, false),
    DOWN_RIGHT(true, 315, true, true, true),
    DOWN_LEFT(true, 225, true, false, true),
    ;
    public Boolean growX;
    public Boolean growY;
    private boolean vertical;
    private boolean diagonal;
    private int degrees;

    Direction(boolean diagonal, int degrees, boolean vertical,
              Boolean growX, Boolean growY) {
        this.vertical = vertical;
        this.growX = growX;
        this.growY = growY;
        this.diagonal = diagonal;
        this.degrees = degrees;
    }

    public static Direction get(String name) {
        return EnumFinder.get(Direction.class, name);
    }
}
