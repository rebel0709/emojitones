package com.klinker.android.emoji_keyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.klinker.android.emoji_keyboard.view.EmojiKeyboardView;
import com.klinker.android.emoji_keyboard_trial.R;

import java.util.ArrayList;
import java.util.List;

public class EmojiKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private EmojiKeyboardView emojiKeyboardView;
    private KeyboardView mInputView;
    private LinearLayout mEmojiLayout;

    private LatinKeyboard mQwertyKeyboard;
    private LatinKeyboard mSymbolsKeyboard;
    private LatinKeyboard mSymbolsShiftedKeyboard;
    private LatinKeyboard mCurKeyboard;

    private boolean mCapsLock;
    private boolean mCompletionOn;
    private StringBuilder mComposing = new StringBuilder();
    private CompletionInfo[] mCompletions;
    private int mLastDisplayWidth;
    private long mLastShiftTime;
    private long mMetaState;
    private boolean mPredictionOn;

    private InputConnection inputConnection;
    private InputMethodManager previousInputMethodManager;
    private IBinder iBinder;

    private String mWordSeparators;
    private String[] mimeTypes;

    private static Context staticApplicationContext;

    public static Context getStaticApplicationContext() {
        return staticApplicationContext;
    }

    public EmojiKeyboardService() {
        super();

        if (Build.VERSION.SDK_INT >= 17) {
            enableHardwareAcceleration();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mWordSeparators = "";
    }

    @Override
    public View onCreateInputView() {

        staticApplicationContext = getApplicationContext();
        previousInputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        iBinder = this.getWindow().getWindow().getAttributes().token;
        //View rootView = getLayoutInflater().inflate(R.layout.emoji_keyboard_layout, null);
        //emojiKeyboardView = (EmojiKeyboardView)rootView.findViewById(R.id.emojiKeyboard);
        emojiKeyboardView = (EmojiKeyboardView)getLayoutInflater().inflate(R.layout.emoji_keyboard_layout, null);

        //this.mInputView = (KeyboardView) rootView.findViewById(R.id.keyboard);
        this.mInputView = (KeyboardView) emojiKeyboardView.getView().findViewById(R.id.keyboard);
        this.mEmojiLayout = (LinearLayout)emojiKeyboardView.getView().findViewById(R.id.emoji_area);
        this.mInputView.setOnKeyboardActionListener(this);
        this.mInputView.setKeyboard(this.mQwertyKeyboard);
        //return emojiKeyboardView.getView();
        return emojiKeyboardView.getView();
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        inputConnection = getCurrentInputConnection();

        this.mComposing.setLength(0);
        updateCandidates();
        if (!restarting) {
            this.mMetaState = 0;
        }
        setPredictionOn(false);
        setCompletionOn(false);
        this.mCompletions = null;
        switch (attribute.inputType & 15) {
            case 1:
                this.mCurKeyboard = this.mQwertyKeyboard;
                setPredictionOn(true);
                int variation = attribute.inputType & 4080;
                if (variation == 128 || variation == 144) {
                    setPredictionOn(false);
                }
                if (variation == 32 || variation == 16 || variation == 176) {
                    setPredictionOn(false);
                }
                if ((attribute.inputType & 65536) != 0) {
                    setPredictionOn(false);
                    setCompletionOn(isFullscreenMode());
                }
                updateShiftKeyState(attribute);
                break;
            case 2:
            case 4:
                this.mCurKeyboard = this.mSymbolsKeyboard;
                break;
            case 3:
                this.mCurKeyboard = this.mSymbolsKeyboard;
                break;
            default:
                this.mCurKeyboard = this.mQwertyKeyboard;
                updateShiftKeyState(attribute);
                break;
        }
        this.mCurKeyboard.setImeOptions(getResources(), attribute.imeOptions);
    }

    @Override
    public void onInitializeInterface() {
        super.onInitializeInterface();
        if (this.mQwertyKeyboard != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth != this.mLastDisplayWidth) {
                this.mLastDisplayWidth = displayWidth;
            } else {
                return;
            }
        }
        this.mQwertyKeyboard = new LatinKeyboard(this, R.xml.qwerty);
        this.mSymbolsKeyboard = new LatinKeyboard(this, R.xml.symbols);
        this.mSymbolsShiftedKeyboard = new LatinKeyboard(this, R.xml.symbols_shift);
    }

    public void sendText(String text) {
        inputConnection.commitText(text, 1);
    }

    private void updateCandidates() {
        if (!this.mCompletionOn) {
            if (this.mComposing.length() > 0) {
                ArrayList<String> list = new ArrayList();
                list.add(this.mComposing.toString());
                setSuggestions(list, true, true);
                return;
            }
            setSuggestions(null, false, false);
        }
    }

    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {
        if (suggestions != null && suggestions.size() > 0) {
            setCandidatesViewShown(true);
        } else if (isExtractViewShown()) {
            setCandidatesViewShown(true);
        }
        /*if (this.mCandidateView != null) {
            this.mCandidateView.setSuggestions(suggestions, completions, typedWordValid);
        }*/
        if (this.emojiKeyboardView != null) {
            //this.emojiKeyboardView.setSuggestions(suggestions, completions, typedWordValid);
        }
    }

    private void setPredictionOn(boolean mPredictionOn) {
        this.mPredictionOn = false;
    }

    private void setCompletionOn(boolean mCompletionOn) {
        this.mCompletionOn = mCompletionOn;
    }

    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null && this.mInputView != null && this.mQwertyKeyboard == this.mInputView.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (!(ei == null || ei.inputType == 0)) {
                InputConnection currentInputConnection = getCurrentInputConnection();
                if (currentInputConnection != null) {
                    caps = currentInputConnection.getCursorCapsMode(attr.inputType);
                }
            }
            KeyboardView keyboardView = this.mInputView;
            boolean z = this.mCapsLock || caps != 0;
            keyboardView.setShifted(z);
        }
    }

    public void backToKeyBoard() {
        this.mInputView.setVisibility(View.VISIBLE);
        this.mEmojiLayout.setVisibility(View.GONE);
    }

    public void sendDownKeyEvent(int keyEventCode, int flags) {
        inputConnection.sendKeyEvent(
                new KeyEvent(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        KeyEvent.ACTION_DOWN,
                        keyEventCode,
                        0,
                        flags
                )
        );
    }

    public void sendUpKeyEvent(int keyEventCode, int flags) {
        inputConnection.sendKeyEvent(
                new KeyEvent(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        KeyEvent.ACTION_UP,
                        keyEventCode,
                        0,
                        flags
                )
        );
    }
    public void sendDownAndUpKeyEvent(int keyEventCode, int flags){
        sendDownKeyEvent(keyEventCode, flags);
        sendUpKeyEvent(keyEventCode, flags);
    }


    @SuppressLint("MissingPermission")
    public void switchToPreviousInputMethod() {

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(25);

        try {
            previousInputMethodManager.switchToLastInputMethod(iBinder);
        } catch (Throwable t) { // java.lang.NoSuchMethodError if API_level<11
            Context context = getApplicationContext();
            CharSequence text = "Unfortunately input method switching isn't supported in your version of Android! You will have to do it manually :(";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /************************************************/
    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (primaryCode == 10 && this.mCurKeyboard.isEnterAction()) {
            keyDownUp(KeyEvent.KEYCODE_ENTER);
        } else if (isWordSeparator(primaryCode)) {
            if (this.mComposing.length() > 0) {
                commitTyped(getCurrentInputConnection());
            }
            sendKey(primaryCode);
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (primaryCode == -5) {
            handleBackspace();
        } else if (primaryCode == -1) {
            handleShift();
        } else if (primaryCode == 204) {
            handleEmoji();
        } else if (primaryCode == -3) {
            handleClose();
        } else if (primaryCode == -100) {
        } else {
            if (primaryCode != -2 || this.mInputView == null) {
                handleCharacter(primaryCode, keyCodes);
                return;
            }
            Keyboard current = this.mInputView.getKeyboard();
            if (current == this.mSymbolsKeyboard || current == this.mSymbolsShiftedKeyboard) {
                current = this.mQwertyKeyboard;
            } else {
                current = this.mSymbolsKeyboard;
            }
            this.mInputView.setKeyboard(current);
            if (current == this.mSymbolsKeyboard) {
                current.setShifted(false);
            }
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {
        handleBackspace();
    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
    /***********************************************/
    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(1, keyEventCode));
    }

    public boolean isWordSeparator(int code) {
        return getWordSeparators().contains(String.valueOf((char) code));
    }

    private String getWordSeparators() {
        return this.mWordSeparators;
    }

    private void commitTyped(InputConnection inputConnection) {
        if (this.mComposing.length() > 0) {
            inputConnection.commitText(this.mComposing, this.mComposing.length());
            this.mComposing.setLength(0);
            updateCandidates();
        }
    }

    private void sendKey(int keyCode) {
        switch (keyCode) {
            case 10:
                keyDownUp(KeyEvent.KEYCODE_ENTER);
                return;
            default:
                if (keyCode < 48 || keyCode > 57) {
                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                    return;
                } else {
                    keyDownUp((keyCode - 48) + 7);
                    return;
                }
        }
    }

    private void handleBackspace() {
        int length = this.mComposing.length();
        if (length > 1) {
            this.mComposing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(this.mComposing, 1);
            updateCandidates();
        } else if (length > 0) {
            this.mComposing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
            updateCandidates();
        } else {
            keyDownUp(67);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    private void handleShift() {
        boolean shifted = false;
        if (this.mInputView != null) {
            Keyboard currentKeyboard = this.mInputView.getKeyboard();
            if (this.mQwertyKeyboard == currentKeyboard) {
                if (this.mCapsLock) {
                    this.mCapsLock = false;
                    this.mInputView.setShifted(false);
                    return;
                }
                checkToggleCapsLock();
                if (this.mCapsLock || !this.mInputView.isShifted()) {
                    shifted = true;
                }
                //Log.d(GifHeaderParser.TAG, "shift_mode: " + (shifted ? "A" : "a") + " caps: " + (this.mCapsLock ? "on" : "off"));
                this.mInputView.setShifted(shifted);
            } else if (currentKeyboard == this.mSymbolsKeyboard) {
                this.mSymbolsKeyboard.setShifted(true);
                this.mInputView.setKeyboard(this.mSymbolsShiftedKeyboard);
                this.mSymbolsShiftedKeyboard.setShifted(true);
            } else if (currentKeyboard == this.mSymbolsShiftedKeyboard) {
                this.mSymbolsShiftedKeyboard.setShifted(false);
                this.mInputView.setKeyboard(this.mSymbolsKeyboard);
                this.mSymbolsKeyboard.setShifted(false);
            }
        }
    }

    private void handleEmoji() {
        if (this.mInputView != null) {
            this.mInputView.setVisibility(View.GONE);
        }
        if (this.mEmojiLayout != null) {
            this.mEmojiLayout.setVisibility(View.VISIBLE);
        }
    }

    private void handleClose() {
        commitTyped(getCurrentInputConnection());
        requestHideSelf(0);
        this.mInputView.closing();
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        boolean bUpdate = false;
        if (isInputViewShown() && this.mInputView.isShifted()) {
            primaryCode = Character.toUpperCase(primaryCode);
            if (isAlphabet(primaryCode)) {
                bUpdate = true;
            }
        }
        if (isAlphabet(primaryCode) && this.mPredictionOn && isCrutch601(primaryCode)) {
            this.mComposing.append((char) primaryCode);
            getCurrentInputConnection().setComposingText(this.mComposing, 1);
            updateCandidates();
        } else {
            getCurrentInputConnection().commitText(String.valueOf((char) primaryCode), 1);
        }
        if (bUpdate) {
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
    }

    private void checkToggleCapsLock() {
        long now = System.currentTimeMillis();
        if (this.mLastShiftTime + 800 > now) {
            this.mCapsLock = !this.mCapsLock;
            this.mLastShiftTime = 0;
            return;
        }
        this.mLastShiftTime = now;
    }

    private boolean isAlphabet(int code) {
        return true;
    }

    private boolean isCrutch601(int primaryCode) {
        return primaryCode != 10 || (primaryCode == 10 && !"6.0.1".equals(Build.VERSION.RELEASE));
    }
}
