package com.binaryic.customerapp.fashionic.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class ImageViewBanner extends AppCompatImageView {
    public ImageViewBanner(Context paramContext) {
        super(paramContext);
    }

    public ImageViewBanner(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public ImageViewBanner(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {

        super.onMeasure(paramInt1, paramInt2);
        int i = getMeasuredWidth();
        if (i == getMeasuredHeight()) {
            setMeasuredDimension(i, i);
            return;
        }
        Double h = (i*0.5);
        setMeasuredDimension(i, h.intValue());
    }
}

/* Location:           C:\Users\User\Downloads\Compressed\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     com.vilara.android.view.custom.ImageViewBanner
 * JD-Core Version:    0.6.0
 */