package com.binaryic.customerapp.fashionic.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Asd on 20-09-2016.
 */
public class ImageViewRectVertical extends AppCompatImageView {
    public ImageViewRectVertical(Context paramContext)
    {
        super(paramContext);
    }

    public ImageViewRectVertical(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public ImageViewRectVertical(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
    }

    protected void onMeasure(int paramInt1, int paramInt2)
    {
        super.onMeasure(paramInt1, paramInt2);
        int i = getMeasuredHeight();
        setMeasuredDimension(i, i + i / 2);
    }
}
