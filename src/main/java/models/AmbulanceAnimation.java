package models;

import java.awt.Point;

public class AmbulanceAnimation {
    public double currentX, currentY;
    public double targetX, targetY;
    public boolean active = false;
    public double speed = 4.0; 
    public float angle = 0;

    public void start(Point start, Point target) {
        this.currentX = start.x;
        this.currentY = start.y;
        this.targetX = target.x;
        this.targetY = target.y;
        this.active = true;
        
        this.angle = (float) Math.atan2(targetY - currentY, targetX - currentX);
    }

    public void update() {
        if (!active) return;

        double dx = targetX - currentX;
        double dy = targetY - currentY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed) {
            currentX = targetX;
            currentY = targetY;
            active = false;
        } else {
            currentX += (dx / distance) * speed;
            currentY += (dy / distance) * speed;
        }
    }
}