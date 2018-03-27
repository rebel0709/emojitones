package com.emojitones.keyboard.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.Keyboard.Row;

import com.emojitones.keyboard.R;


public class LatinKeyboard extends Keyboard {
    private int imeAction = 1;
    private Key mEnterKey;

    static class LatinKey extends Key {
        public LatinKey(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
            super(res, parent, x, y, parser);
        }

        public boolean isInside(int x, int y) {
            if (this.codes[0] == -3) {
                y -= 10;
            }
            return super.isInside(x, y);
        }
    }

    public LatinKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public LatinKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        Key key = new LatinKey(res, parent, x, y, parser);
        if (key.codes[0] == 10) {
            this.mEnterKey = key;
        }
        return key;
    }

    void setImeOptions(Resources res, int options) {
        if (this.mEnterKey != null) {
            this.imeAction = 1073742079 & options;
            switch (this.imeAction) {
                case 2:
                    this.mEnterKey.iconPreview = null;
                    this.mEnterKey.icon = null;
                    this.mEnterKey.label = res.getText(R.string.label_go_key);
                    return;
                case 3:
                    this.mEnterKey.icon = res.getDrawable(R.drawable.sym_keyboard_search);
                    this.mEnterKey.label = null;
                    return;
                case 4:
                    this.mEnterKey.iconPreview = null;
                    this.mEnterKey.icon = null;
                    this.mEnterKey.label = res.getText(R.string.label_send_key);
                    return;
                case 5:
                    this.mEnterKey.iconPreview = null;
                    this.mEnterKey.icon = null;
                    this.mEnterKey.label = res.getText(R.string.label_next_key);
                    return;
                default:
                    this.mEnterKey.icon = res.getDrawable(R.drawable.sym_keyboard_return);
                    this.mEnterKey.label = null;
                    return;
            }
        }
    }

    public boolean isEnterAction() {
        switch (this.imeAction) {
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }
}
