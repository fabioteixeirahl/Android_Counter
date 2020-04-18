package isel.poo.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

class CounterView extends View {
    private final int FACTOR = -10;
    private int counnterValue = 0;

    public CounterView (Context ctx) {
        super(ctx);
    }

    Paint p = new Paint();
    /*{
        p.setColor(Color.RED);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        canvas.drawCircle(w/2, h/2+counnterValue*FACTOR, w/4, p);
    }

    public void setValue(int value){
        counnterValue = value;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_MOVE){
            int y = (int) event.getY();
            setValue(-(getWidth()/2-y)/FACTOR);
            if (listener!=null){
                listener.valueChanged(counnterValue);
            }
            return true;
        }else
            return super.onTouchEvent(event);
    }

    public interface Listener {
        void valueChanged(int value);
    }
    private Listener listener = null;
    public void setListener(Listener l) {
        listener = l;
    }

    public int getCounterValue() {
        return counnterValue;
    }

}
