package com.binaryic.customerapp.fashionic.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Hi on 7/29/2015.
 */
public class TextViewRS extends AppCompatTextView {
    public TextViewRS(Context context) {
        super(context);
        if(!isInEditMode()){
            setFont();}
    }

    public TextViewRS(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()){
            setFont();}
    }

    public TextViewRS(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()){
            setFont();}
    }
    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "Rupee_Foradian.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
