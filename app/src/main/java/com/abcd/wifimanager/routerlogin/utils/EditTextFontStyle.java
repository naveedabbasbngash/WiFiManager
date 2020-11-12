package com.abcd.wifimanager.routerlogin.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextFontStyle extends EditText {
    public EditTextFontStyle(Context context) {
        super(context);
        init();
    }

    public EditTextFontStyle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public EditTextFontStyle(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Limerick-Regular.ttf"));
    }
}
