package Math;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return ("(" + this.x + "," + this.y + ")");
    }

    public boolean precedes(Vector2d vec){
        return (this.x <= vec.x && this.y <= vec.y);
    }

    public boolean follows(Vector2d vec){
        return (this.x >= vec.x && this.y >= vec.y);
    }

    public Vector2d upperRight(Vector2d other){
        int resX, resY;
        if(this.x > other.x) resX = this.x;
        else resX = other.x;
        if(this.y > other.y) resY = this.y;
        else resY = other.y;
        return new Vector2d(resX, resY);
    }

    public Vector2d lowerLeft(Vector2d other){
        int resX, resY;
        if(this.x < other.x) resX = this.x;
        else resX = other.x;
        if(this.y < other.y) resY = this.y;
        else resY = other.y;
        return new Vector2d(resX, resY);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other){
        if (this == other) return true;
        if (this.hashCode() != other.hashCode()) return false;
        if (!(other instanceof Vector2d)) return false;
        Vector2d otherVector = (Vector2d) other;
        return (this.x == otherVector.x && this.y == otherVector.y);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }

    public Vector2d opposite(){
        return new Vector2d(- this.x, - this.y);
    }

    public Vector2d copy(){ return new Vector2d(this.x, this.y);}

}
