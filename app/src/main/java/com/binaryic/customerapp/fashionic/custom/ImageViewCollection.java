package com.binaryic.customerapp.fashionic.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Asd on 28-09-2016.
 */
public class ImageViewCollection extends AppCompatImageView {
    public ImageViewCollection(Context paramContext) {
        super(paramContext);
    }

    public ImageViewCollection(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public ImageViewCollection(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {

        super.onMeasure(paramInt1, paramInt2);
        int i = getMeasuredWidth();
        if (i == getMeasuredHeight()) {
            setMeasuredDimension(i, i);
            return;
        }
        setMeasuredDimension(i, i / 2);
    }
}
