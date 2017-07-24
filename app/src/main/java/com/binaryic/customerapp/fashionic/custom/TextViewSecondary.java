package com.binaryic.customerapp.fashionic.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


public class TextViewSecondary extends AppCompatTextView {
    public TextViewSecondary(Context paramContext) {
        super(paramContext);
        a();
    }

    public TextViewSecondary(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        a();
    }

    public TextViewSecondary(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        a();
    }

    public void a() {
        if (VERSION.SDK_INT < 21) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Cairo-Regular.ttf"), 0);
            return;
        }
        int i = getTypeface().getStyle();
        if (i == 1) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Cairo-Bold.ttf"), i);
            return;
        }
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Cairo-Regular.ttf"), 0);
    }
}

/* Location:           C:\Users\User\Downloads\Compressed\jd-gui-0.3.3.windows\classes_dex2jar.jar
 * Qualified Name:     com.vilara.android.view.custom.TextViewSecondary
 * JD-Core Version:    0.6.0
 */