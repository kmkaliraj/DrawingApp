package com.example.kalirajkalimuthu.drawingapp;

import android.content.Context;
import android.view.VelocityTracker;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by kalirajkalimuthu on 2/22/17.
 */

public class CircleDrawingView extends View {

    private int color = Color.BLACK;
    private UserMode mode = UserMode.INSERT;
    private VelocityTracker mVelocityTracker = null;
    private int screenWidth;
    private int screenHeight;
    private Float xVelocity = 0f;
    private Float yVelocity = 0f;
    private Circle selectedScalingCircle;
    private List<Circle> circles = new ArrayList<>();

       public CircleDrawingView(Context context){
            super(context);

       }

    public CircleDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserMode getMode() {
        return mode;
    }

    public void setMode(UserMode mode) {
        this.mode = mode;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        for(Circle circle: circles){
            PointF p = circle.getPoint();
            Paint paint = circle.getPaint();
            canvas.drawCircle(p.x, p.y, circle.getRadius(), paint);

            if(mode == UserMode.MOVE) {
                handleCircleMove();
                invalidate();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

           if(mode == UserMode.INSERT) {
               int action = event.getAction();
               int actionCode = action & MotionEvent.ACTION_MASK;
               switch (actionCode) {
                   case MotionEvent.ACTION_DOWN:
                       insertCircles(event);
                       break;
                   case MotionEvent.ACTION_MOVE:
                       handleActionMoveForScalingCircles(event);
                       break;
               }
               invalidate();
           }
         else if(mode == UserMode.DELETE){
              deleteCircles(event);
               invalidate();
           }
        else if(mode == UserMode.MOVE){
               int action = event.getAction();
               int actionCode = action & MotionEvent.ACTION_MASK;
               switch (actionCode) {
                   case MotionEvent.ACTION_DOWN:
                       if(mVelocityTracker == null) {
                           mVelocityTracker = VelocityTracker.obtain();
                       }
                       else {
                           mVelocityTracker.clear();
                       }
                       mVelocityTracker.addMovement(event);
                       break;
                   case MotionEvent.ACTION_MOVE:
                       mVelocityTracker.addMovement(event);
                       mVelocityTracker.computeCurrentVelocity(1);
                       xVelocity = mVelocityTracker.getXVelocity();
                       yVelocity = mVelocityTracker.getYVelocity();
                       setMoveCircle(event);
                       break;
                   case MotionEvent.ACTION_UP:
                       handleCircleMove();
                       invalidate();
                       break;
                   case MotionEvent.ACTION_CANCEL:
                       mVelocityTracker.recycle();
                       mVelocityTracker = null;
                       break;
               }

           }

        return true;
    }

    private void setMoveCircle(MotionEvent event){
        for(Circle circle: circles){
            if(circle.contains(event.getX(),event.getY()))
                circle.setMoving(true);
        }
    }

    private boolean xIsOutOfBounds(Float x, Float radius) {

        if (x-radius < 0)
            return true;
        if (x +radius > screenWidth)
            return true;
        return false;
    }

    private boolean yIsOutOfBounds(Float y, Float radius) {

        if (y-radius < 0)
            return true;
        if (y + radius + 160  > screenHeight) {
            return true;
        }
        return false;
    }

    public void handleActionMoveForScalingCircles(MotionEvent event) {

        if (selectedScalingCircle != null && selectedScalingCircle.contains(event.getX(), event.getY())) {
            Float currentDistance = (float) selectedScalingCircle.getDistance(event.getX(), event.getY());
            Float historyDistance = (float) selectedScalingCircle.getDistance(selectedScalingCircle.getHistoryPoint().x, selectedScalingCircle.getHistoryPoint().y);
            Float newRadius = 0f;

            if (currentDistance > historyDistance)
                newRadius = selectedScalingCircle.getRadius() + currentDistance;
            else
                newRadius = selectedScalingCircle.getRadius() - currentDistance;

            if (xIsOutOfBounds(selectedScalingCircle.getPoint().x, newRadius)
                    || yIsOutOfBounds(selectedScalingCircle.getPoint().y, newRadius))
                selectedScalingCircle.setRadius(selectedScalingCircle.getRadius());
            else {
                selectedScalingCircle.setRadius(newRadius);
                PointF point = new PointF();
                point.x = event.getX();
                point.y = event.getY();
                selectedScalingCircle.setHistoryPoint(point);

            }
        }
    }

    public void insertCircles(MotionEvent event){

        PointF point = new PointF();
        point.x = event.getX();
        point.y = event.getY();

        Circle circle = new Circle(point);
        (circle.getPaint()).setColor(color); // set the selected color

        if(xIsOutOfBounds(circle.getPoint().x, circle.getRadius())
                || yIsOutOfBounds(circle.getPoint().y, circle.getRadius())) {
            circle = null;
            return;
        }
            selectedScalingCircle = circle;
            circles.add(circle);
    }

  public void deleteCircles(MotionEvent event){

      ArrayList<Circle>  deleteCandidates = new ArrayList<>();
      for(Circle circle: circles){
          if(circle.contains(event.getX(),event.getY()))
              deleteCandidates.add(circle);
      }

      for(Circle circle: deleteCandidates)
          circles.remove(circle);
  }

    public boolean handleCircleMove() {
        PointF point = null;

        for(Circle circle: circles) {

            if (circle.isMoving() || circle.isInMotion()){
                point = circle.getPoint();

                if(circle.isMoving()){
                    circle.setxVelocity(xVelocity);
                    circle.setyVelocity(yVelocity);
                    circle.setInMotion(true);
                    circle.setMoving(false);
                }

                if (xIsOutOfBounds(circle.getPoint().x + circle.getxVelocity(), circle.getRadius())) {
                    circle.setxVelocity(circle.getxVelocity()* -0.5f);
                    point.x += circle.getxVelocity();
                }
                else
                    point.x += circle.getxVelocity();

                if (yIsOutOfBounds(circle.getPoint().y+circle.getyVelocity(), circle.getRadius())) {
                    circle.setyVelocity(circle.getyVelocity() * -0.5f);
                    point.y += circle.getyVelocity();
                }
                else
                    point.y += circle.getyVelocity();
            }
        }
        return true;
    }


}
