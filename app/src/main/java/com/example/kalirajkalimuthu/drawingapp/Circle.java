package com.example.kalirajkalimuthu.drawingapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by kalirajkalimuthu on 2/26/17.
 */

public class Circle{

    private PointF point;
    private Paint paint;
    private Float  radius = 80f;
    private boolean isMoving = false;
    private boolean inMotion = true;
    private Float xVelocity = 0f;
    private Float yVelocity = 0f;
    private PointF historyPoint;

    public Circle(PointF point){
        this.point = point;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10.0f);
        historyPoint = new PointF();
        historyPoint.x=point.x;
        historyPoint.y=point.y;
    }


    public PointF getHistoryPoint() {
        return historyPoint;
    }

    public void setHistoryPoint(PointF historyPoint) {
        this.historyPoint = historyPoint;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setxVelocity(Float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public Float getxVelocity() {
        return this.xVelocity;
    }

    public boolean isInMotion() {
        return inMotion;
    }

    public void setInMotion(boolean inMotion) {
        this.inMotion = inMotion;
    }

    public Float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(Float yVelocity) {
        this.yVelocity = yVelocity;
    }
    public PointF getPoint() {
        return point;
    }

    public void setPoint(PointF point) {
        this.point = point;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

    public double getDistance(float x2, float y2){
        float x1 = point.x, y1=point.y;
        float x = Math.abs(x1-x2);
        float y = Math.abs(y1-y2);
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }

    public boolean contains(float x2, float y2){
        double distance = getDistance(x2,y2);
        if(distance <= (double)radius)
            return true;

        return false;
    }
}