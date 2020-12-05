package Enums;
import Math.Vector2d;

public enum Orientation {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public Vector2d toUnitVector(){
        switch(this){
            case NORTH:
                return new Vector2d(0,1);
            case SOUTH:
                return new Vector2d(0,-1);
            case WEST:
                return new Vector2d(-1,0);
            case EAST:
                return new Vector2d(1,0);
            case NORTH_EAST:
                return new Vector2d(1,1);
            case NORTH_WEST:
                return new Vector2d(-1,1);
            case SOUTH_EAST:
                return new Vector2d(1,-1);
            case SOUTH_WEST:
                return new Vector2d(-1,-1);
        }
        return new Vector2d(0,0);
    }
}
