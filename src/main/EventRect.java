package main;

import java.awt.Rectangle;

public class EventRect extends Rectangle {
    int eventRectDefaultX, eventRectDefaultY;
    boolean eventDone = false;

    public EventRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        eventRectDefaultX = x;
        eventRectDefaultY = y;   
    }
}
