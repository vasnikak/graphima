/**
 * Copyright (C) 2019, by Vasileios Nikakis
 *
 * graphima: yet another Java graph-theory library
 */
package com.sitienda.graphima;

import java.util.Objects;

/**
 *
 * @author Vasileios Nikakis
 */
public class MazeCell {
    
    private int x;
    private int y;
    private int value;
    
    public MazeCell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public MazeCell(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFree() { 
        return (value == 0);
    }
    
    public boolean isBlocked() { 
        return (value == 1);
    }
    
    @Override
    public String toString() { 
        return "(" + x + "," + y + ")";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x,y,value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MazeCell other = (MazeCell) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
    
}
