package com.omkarmoghe.androidgamedev;

import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Omkar Moghe on 2/5/2015.
 *
 * This class extends the RectF class and adds an option to assign a paint to the rec.
 */
public class RectFP extends RectF {

    private Paint paint;

    public RectFP () {
        super();
    }

    public RectFP (float left, float top, float right, float bottom) {
        super (left, top, right, bottom);
    }

    public void setPaint (Paint p) {
        paint = p;
    }

    public Paint getPaint () {
        return paint;
    }
}
