package com.example.kalirajkalimuthu.drawingapp;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View circleDrawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circleDrawingView = new CircleDrawingView(this);
        setContentView(circleDrawingView);
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        ((CircleDrawingView)circleDrawingView).setScreenHeight(screenSize.y);
        ((CircleDrawingView)circleDrawingView).setScreenWidth(screenSize.x);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.insert:
                ((CircleDrawingView)circleDrawingView).setMode(UserMode.INSERT);
                Toast.makeText(this, "Mode:Insert", Toast.LENGTH_LONG).show();
                break;
            case R.id.delete:
                ((CircleDrawingView)circleDrawingView).setMode(UserMode.DELETE);
                Toast.makeText(this, "Mode:Delete", Toast.LENGTH_LONG).show();
                break;
            case R.id.move:
                ((CircleDrawingView)circleDrawingView).setMode(UserMode.MOVE);
                circleDrawingView.invalidate() ;
                Toast.makeText(this, "Mode:Move", Toast.LENGTH_LONG).show();
                break;
            case R.id.red:
                ((CircleDrawingView)circleDrawingView).setColor(Color.RED);
                Toast.makeText(this, "Color:Red", Toast.LENGTH_LONG).show();
                break;
            case R.id.green:
                ((CircleDrawingView)circleDrawingView).setColor(Color.GREEN);
                Toast.makeText(this, "Color:Green", Toast.LENGTH_LONG).show();
                break;
            case R.id.black:
                ((CircleDrawingView)circleDrawingView).setColor(Color.BLACK);
                Toast.makeText(this, "Color:Black", Toast.LENGTH_LONG).show();
                break;
            case R.id.blue:
                ((CircleDrawingView)circleDrawingView).setColor(Color.BLUE);
                Toast.makeText(this, "Color:Blue", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
