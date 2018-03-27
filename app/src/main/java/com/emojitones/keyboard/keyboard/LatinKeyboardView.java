package com.emojitones.keyboard.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

public class LatinKeyboardView extends KeyboardView {
    static final int KEYCODE_OPTIONS = -100;

    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected boolean onLongPress(Key key) {
        if (key.codes[0] != -3) {
            return super.onLongPress(key);
        }
        getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
        return true;
    }
}
