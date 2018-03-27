package com.emojitones.keyboard.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.CompletionInfo;


import com.emojitones.keyboard.R;
import com.emojitones.keyboard.adapter.PagerAdapter;


public class SoftKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener{


    static final boolean DEBUG = false;
    static final boolean PROCESS_HARD_KEYS = true;
    private ViewPager emojisPager;
    private CandidateView mCandidateView;
    private boolean mCapsLock;
    private boolean mCompletionOn;
    private CompletionInfo[] mCompletions;
    private StringBuilder mComposing = new StringBuilder();
    private LatinKeyboard mCurKeyboard;
    private View mEmojiLayout;
    private KeyboardView mInputView;
    private int mLastDisplayWidth;
    private long mLastShiftTime;
    private long mMetaState;
    private boolean mPredictionOn;
    private LatinKeyboard mQwertyKeyboard;
    private LatinKeyboard mSymbolsKeyboard;
    private LatinKeyboard mSymbolsShiftedKeyboard;
    private View mTabImageEmoji;
    private View mTabVideoEmoji;
    private String mWordSeparators;
    private String[] mimeTypes;

//    private OnClickListener tabClickListener = new C04015();

//    class C03982 implements OnClickListener {
//        C03982() {
//        }
//
//        public void onClick(View view) {
//            SoftKeyboard.this.keyDownUp(67);
//        }
//    }
//
//    class C03993 implements OnClickListener {
//        C03993() {
//        }
//
//        public void onClick(View view) {
//            IntentUtils.showChangeKeyboard(SoftKeyboard.this.getApplicationContext());
//        }
//    }
//
//    class C04004 implements OnClickListener {
//        C04004() {
//        }
//
//        public void onClick(View view) {
//            SoftKeyboard.this.getCurrentInputConnection().commitText(SoftKeyboard.this.getString(R.string.share_friends_body), 1);
//        }
//    }
//
//    class C04015 implements OnClickListener {
//        C04015() {
//        }
//
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.image:
//                    SoftKeyboard.this.emojisPager.setCurrentItem(1);
//                    return;
//                case R.id.homeToKeyBoardTop:
//                case R.id.homeToKeyBoard:
//                    SoftKeyboard.this.backToKeyBoard();
//                    return;
//                default:
//                    return;
//            }
//        }
//    }
//
//    class C07131 implements OnPageChangeListener {
//        C07131() {
//        }
//
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        }
//
//        public void onPageSelected(int position) {
//            if (position == 0) {
//                PageView pageView = (PageView) SoftKeyboard.this.emojisPager.findViewWithTag(Integer.valueOf(position));
//                if (pageView != null) {
//                    pageView.notifyDataSetChanged();
//                }
//            }
//        }
//
//        public void onPageScrollStateChanged(int state) {
//        }
//    }
//
//    class C07146 implements OnTabSelectedListener {
//        C07146() {
//        }
//
//        public void onTabSelected(Tab tab) {
//            Integer tag = SoftKeyboard.this.setSelectStatus(tab, true);
//            if (tag != null) {
//                SoftKeyboard.this.emojisPager.setCurrentItem(tag.intValue(), false);
//            }
//        }
//
//        public void onTabUnselected(Tab tab) {
//            SoftKeyboard.this.setSelectStatus(tab, false);
//        }
//
//        public void onTabReselected(Tab tab) {
//        }
//    }
//
//    public void onCreate() {
//        setTheme(R.style.AppTheme);
//        super.onCreate();
//        this.mWordSeparators = "";
//    }
//
//    public void onInitializeInterface() {
//        if (this.mQwertyKeyboard != null) {
//            int displayWidth = getMaxWidth();
//            if (displayWidth != this.mLastDisplayWidth) {
//                this.mLastDisplayWidth = displayWidth;
//            } else {
//                return;
//            }
//        }
//        this.mQwertyKeyboard = new LatinKeyboard(this, R.xml.qwerty);
//        this.mSymbolsKeyboard = new LatinKeyboard(this, R.xml.symbols);
//        this.mSymbolsShiftedKeyboard = new LatinKeyboard(this, R.xml.symbols_shift);
//    }

    public View onCreateInputView() {

        View rootView = getLayoutInflater().inflate(R.layout.keyboard_layout, null);

        TabLayout tablayout = (TabLayout) rootView.findViewById(R.id.keyboard_tab_layout);

        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.ic_tab_history));
        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.ic_tab_emojitones));

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);

//        PagerAdapter adapter = new PagerAdapter(getFragmentManager(),tablayout.getTabCount());
//        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener((tablayout)));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

          mInputView = (KeyboardView) rootView.findViewById(R.id.keyboard);
          mInputView.setOnKeyboardActionListener(this);
          mInputView.setKeyboard(this.mQwertyKeyboard);

//        rootView.findViewById(R.id.ivDeleteEmoji).setOnClickListener(new C03982());
//        rootView.findViewById(R.id.homeToKeyBoardTop).setOnClickListener(this.tabClickListener);
//        rootView.findViewById(R.id.homeToKeyBoard).setOnClickListener(new C03993());
//        rootView.findViewById(R.id.shareTopKeyboard).setOnClickListener(new C04004());

        this.mEmojiLayout = rootView.findViewById(R.id.emojiLayout);

//        initTab(rootView);
//        App.instance().getAnalytics().showKeyboard();
        return rootView;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

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

//    public void onDestroy() {
//        super.onDestroy();
//        if (this.emojisPager != null) {
//            this.emojisPager.clearOnPageChangeListeners();
//        }
//    }
//
//    public View onCreateCandidatesView() {
//        this.mCandidateView = new CandidateView(this);
//        this.mCandidateView.setService(this);
//        return this.mCandidateView;
//    }
//
//    public void onStartInput(EditorInfo attribute, boolean restarting) {
//        super.onStartInput(attribute, restarting);
//        this.mComposing.setLength(0);
//        updateCandidates();
//        if (!restarting) {
//            this.mMetaState = 0;
//        }
//        setPredictionOn(false);
//        setCompletionOn(false);
//        this.mCompletions = null;
//        switch (attribute.inputType & 15) {
//            case 1:
//                this.mCurKeyboard = this.mQwertyKeyboard;
//                setPredictionOn(true);
//                int variation = attribute.inputType & 4080;
//                if (variation == 128 || variation == 144) {
//                    setPredictionOn(false);
//                }
//                if (variation == 32 || variation == 16 || variation == 176) {
//                    setPredictionOn(false);
//                }
//                if ((attribute.inputType & 65536) != 0) {
//                    setPredictionOn(false);
//                    setCompletionOn(isFullscreenMode());
//                }
//                updateShiftKeyState(attribute);
//                break;
//            case 2:
//            case 4:
//                this.mCurKeyboard = this.mSymbolsKeyboard;
//                break;
//            case 3:
//                this.mCurKeyboard = this.mSymbolsKeyboard;
//                break;
//            default:
//                this.mCurKeyboard = this.mQwertyKeyboard;
//                updateShiftKeyState(attribute);
//                break;
//        }
//        this.mCurKeyboard.setImeOptions(getResources(), attribute.imeOptions);
//    }
//
//    public void onFinishInput() {
//        super.onFinishInput();
//        this.mComposing.setLength(0);
//        updateCandidates();
//        setCandidatesViewShown(false);
//        this.mCurKeyboard = this.mQwertyKeyboard;
//        if (this.mInputView != null) {
//            this.mInputView.closing();
//        }
//    }
//
//    public void onStartInputView(EditorInfo editorInfo, boolean restarting) {
//        super.onStartInputView(editorInfo, restarting);
//        this.mInputView.setKeyboard(this.mCurKeyboard);
//        this.mInputView.closing();
//        this.mimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo);
//    }
//
//    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
//        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
//        if (this.mComposing.length() <= 0) {
//            return;
//        }
//        if (newSelStart != candidatesEnd || newSelEnd != candidatesEnd) {
//            this.mComposing.setLength(0);
//            updateCandidates();
//            InputConnection ic = getCurrentInputConnection();
//            if (ic != null) {
//                ic.finishComposingText();
//            }
//        }
//    }
//
//    public void onDisplayCompletions(CompletionInfo[] completions) {
//        if (this.mCompletionOn) {
//            this.mCompletions = completions;
//            if (completions == null) {
//                setSuggestions(null, false, false);
//                return;
//            }
//            List<String> stringList = new ArrayList();
//            int i = 0;
//            while (true) {
//                if (i < (completions != null ? completions.length : 0)) {
//                    CompletionInfo ci = completions[i];
//                    if (ci != null) {
//                        stringList.add(ci.getText().toString());
//                    }
//                    i++;
//                } else {
//                    setSuggestions(stringList, true, true);
//                    return;
//                }
//            }
//        }
//    }
//
//    private boolean translateKeyDown(int keyCode, KeyEvent event) {
//        this.mMetaState = MetaKeyKeyListener.handleKeyDown(this.mMetaState, keyCode, event);
//        int c = event.getUnicodeChar(MetaKeyKeyListener.getMetaState(this.mMetaState));
//        this.mMetaState = MetaKeyKeyListener.adjustMetaAfterKeypress(this.mMetaState);
//        InputConnection ic = getCurrentInputConnection();
//        if (c == 0 || ic == null) {
//            return false;
//        }
//        if ((Integer.MIN_VALUE & c) != 0) {
//            c &= Integer.MAX_VALUE;
//        }
//        if (this.mComposing.length() > 0) {
//            int composed = KeyEvent.getDeadChar(this.mComposing.charAt(this.mComposing.length() - 1), c);
//            if (composed != 0) {
//                c = composed;
//                this.mComposing.setLength(this.mComposing.length() - 1);
//            }
//        }
//        onKey(c, null);
//        return true;
//    }
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case 4:
//                if (event.getRepeatCount() == 0 && this.mInputView != null && this.mInputView.handleBack()) {
//                    return true;
//                }
//            case 66:
//                return false;
//            case 67:
//                if (this.mComposing.length() > 0) {
//                    onKey(-5, null);
//                    return true;
//                }
//                break;
//            default:
//                if (keyCode == 62 && (event.getMetaState() & 2) != 0) {
//                    InputConnection ic = getCurrentInputConnection();
//                    if (ic != null) {
//                        ic.clearMetaKeyStates(2);
//                        keyDownUp(29);
//                        keyDownUp(42);
//                        keyDownUp(32);
//                        keyDownUp(46);
//                        keyDownUp(43);
//                        keyDownUp(37);
//                        keyDownUp(32);
//                        return true;
//                    }
//                }
//                if (this.mPredictionOn && translateKeyDown(keyCode, event)) {
//                    return true;
//                }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (this.mPredictionOn) {
//            this.mMetaState = MetaKeyKeyListener.handleKeyUp(this.mMetaState, keyCode, event);
//        }
//        return super.onKeyUp(keyCode, event);
//    }
//
//    private void commitTyped(InputConnection inputConnection) {
//        if (this.mComposing.length() > 0) {
//            inputConnection.commitText(this.mComposing, this.mComposing.length());
//            this.mComposing.setLength(0);
//            updateCandidates();
//        }
//    }
//
//    private void updateShiftKeyState(EditorInfo attr) {
//        if (attr != null && this.mInputView != null && this.mQwertyKeyboard == this.mInputView.getKeyboard()) {
//            int caps = 0;
//            EditorInfo ei = getCurrentInputEditorInfo();
//            if (!(ei == null || ei.inputType == 0)) {
//                InputConnection currentInputConnection = getCurrentInputConnection();
//                if (currentInputConnection != null) {
//                    caps = currentInputConnection.getCursorCapsMode(attr.inputType);
//                }
//            }
//            KeyboardView keyboardView = this.mInputView;
//            boolean z = this.mCapsLock || caps != 0;
//            keyboardView.setShifted(z);
//        }
//    }
//
//    private boolean isAlphabet(int code) {
//        return true;
//    }
//
//    private void keyDownUp(int keyEventCode) {
//        getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, keyEventCode));
//        getCurrentInputConnection().sendKeyEvent(new KeyEvent(1, keyEventCode));
//    }
//
//    private void sendKey(int keyCode) {
//        switch (keyCode) {
//            case 10:
//                keyDownUp(66);
//                return;
//            default:
//                if (keyCode < 48 || keyCode > 57) {
//                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
//                    return;
//                } else {
//                    keyDownUp((keyCode - 48) + 7);
//                    return;
//                }
//        }
//    }
//
//    public void onKey(int primaryCode, int[] keyCodes) {
//        if (primaryCode == 10 && this.mCurKeyboard.isEnterAction()) {
//            keyDownUp(66);
//        } else if (isWordSeparator(primaryCode)) {
//            if (this.mComposing.length() > 0) {
//                commitTyped(getCurrentInputConnection());
//            }
//            sendKey(primaryCode);
//            updateShiftKeyState(getCurrentInputEditorInfo());
//        } else if (primaryCode == -5) {
//            handleBackspace();
//        } else if (primaryCode == -1) {
//            handleShift();
//        } else if (primaryCode == 204) {
//            handleEmoji();
//        } else if (primaryCode == -3) {
//            handleClose();
//        } else if (primaryCode == -100) {
//        } else {
//            if (primaryCode != -2 || this.mInputView == null) {
//                handleCharacter(primaryCode, keyCodes);
//                return;
//            }
//            Keyboard current = this.mInputView.getKeyboard();
//            if (current == this.mSymbolsKeyboard || current == this.mSymbolsShiftedKeyboard) {
//                current = this.mQwertyKeyboard;
//            } else {
//                current = this.mSymbolsKeyboard;
//            }
//            this.mInputView.setKeyboard(current);
//            if (current == this.mSymbolsKeyboard) {
//                current.setShifted(false);
//            }
//        }
//    }
//
//    public void onText(CharSequence text) {
//        InputConnection ic = getCurrentInputConnection();
//        if (ic != null) {
//            ic.beginBatchEdit();
//            if (this.mComposing.length() > 0) {
//                commitTyped(ic);
//            }
//            ic.commitText(text, 0);
//            ic.endBatchEdit();
//            updateShiftKeyState(getCurrentInputEditorInfo());
//        }
//    }
//
//    private void updateCandidates() {
//        if (!this.mCompletionOn) {
//            if (this.mComposing.length() > 0) {
//                ArrayList<String> list = new ArrayList();
//                list.add(this.mComposing.toString());
//                setSuggestions(list, true, true);
//                return;
//            }
//            setSuggestions(null, false, false);
//        }
//    }
//
//    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {
//        if (suggestions != null && suggestions.size() > 0) {
//            setCandidatesViewShown(true);
//        } else if (isExtractViewShown()) {
//            setCandidatesViewShown(true);
//        }
//        if (this.mCandidateView != null) {
//            this.mCandidateView.setSuggestions(suggestions, completions, typedWordValid);
//        }
//    }
//
//    private void handleBackspace() {
//        int length = this.mComposing.length();
//        if (length > 1) {
//            this.mComposing.delete(length - 1, length);
//            getCurrentInputConnection().setComposingText(this.mComposing, 1);
//            updateCandidates();
//        } else if (length > 0) {
//            this.mComposing.setLength(0);
//            getCurrentInputConnection().commitText("", 0);
//            updateCandidates();
//        } else {
//            keyDownUp(67);
//        }
//        updateShiftKeyState(getCurrentInputEditorInfo());
//    }
//
//    private void handleShift() {
//        boolean shifted = false;
//        if (this.mInputView != null) {
//            Keyboard currentKeyboard = this.mInputView.getKeyboard();
//            if (this.mQwertyKeyboard == currentKeyboard) {
//                if (this.mCapsLock) {
//                    this.mCapsLock = false;
//                    this.mInputView.setShifted(false);
//                    return;
//                }
//                checkToggleCapsLock();
//                if (this.mCapsLock || !this.mInputView.isShifted()) {
//                    shifted = true;
//                }
//                Log.d(GifHeaderParser.TAG, "shift_mode: " + (shifted ? "A" : "a") + " caps: " + (this.mCapsLock ? "on" : "off"));
//                this.mInputView.setShifted(shifted);
//            } else if (currentKeyboard == this.mSymbolsKeyboard) {
//                this.mSymbolsKeyboard.setShifted(true);
//                this.mInputView.setKeyboard(this.mSymbolsShiftedKeyboard);
//                this.mSymbolsShiftedKeyboard.setShifted(true);
//            } else if (currentKeyboard == this.mSymbolsShiftedKeyboard) {
//                this.mSymbolsShiftedKeyboard.setShifted(false);
//                this.mInputView.setKeyboard(this.mSymbolsKeyboard);
//                this.mSymbolsKeyboard.setShifted(false);
//            }
//        }
//    }
//
//    private void handleCharacter(int primaryCode, int[] keyCodes) {
//        boolean bUpdate = false;
//        if (isInputViewShown() && this.mInputView.isShifted()) {
//            primaryCode = Character.toUpperCase(primaryCode);
//            if (isAlphabet(primaryCode)) {
//                bUpdate = true;
//            }
//        }
//        if (isAlphabet(primaryCode) && this.mPredictionOn && isCrutch601(primaryCode)) {
//            this.mComposing.append((char) primaryCode);
//            getCurrentInputConnection().setComposingText(this.mComposing, 1);
//            updateCandidates();
//        } else {
//            getCurrentInputConnection().commitText(String.valueOf((char) primaryCode), 1);
//        }
//        if (bUpdate) {
//            updateShiftKeyState(getCurrentInputEditorInfo());
//        }
//    }
//
//    private boolean isCrutch601(int primaryCode) {
//        return primaryCode != 10 || (primaryCode == 10 && !"6.0.1".equals(VERSION.RELEASE));
//    }
//
//    private void handleClose() {
//        commitTyped(getCurrentInputConnection());
//        requestHideSelf(0);
//        this.mInputView.closing();
//    }
//
//    private void backToKeyBoard() {
//        this.mInputView.setVisibility(0);
//        this.mEmojiLayout.setVisibility(8);
//    }
//
//    private void handleEmoji() {
//        Log.d(GifHeaderParser.TAG, "handleEmoji");
//        if (this.mInputView != null) {
//            this.mInputView.setVisibility(8);
//        }
//        if (this.mEmojiLayout != null) {
//            this.mEmojiLayout.setVisibility(0);
//        }
//    }
//
//    private void checkToggleCapsLock() {
//        long now = System.currentTimeMillis();
//        if (this.mLastShiftTime + 800 > now) {
//            this.mCapsLock = !this.mCapsLock;
//            this.mLastShiftTime = 0;
//            return;
//        }
//        this.mLastShiftTime = now;
//    }
//
//    private String getWordSeparators() {
//        return this.mWordSeparators;
//    }
//
//    public boolean isWordSeparator(int code) {
//        return getWordSeparators().contains(String.valueOf((char) code));
//    }
//
//    public void pickDefaultCandidate() {
//        pickSuggestionManually(0);
//    }
//
//    public void pickSuggestionManually(int index) {
//        if (this.mCompletionOn && this.mCompletions != null && index >= 0 && index < this.mCompletions.length) {
//            getCurrentInputConnection().commitCompletion(this.mCompletions[index]);
//            if (this.mCandidateView != null) {
//                this.mCandidateView.clear();
//            }
//            updateShiftKeyState(getCurrentInputEditorInfo());
//        } else if (this.mComposing.length() > 0) {
//            commitTyped(getCurrentInputConnection());
//        }
//    }
//
//    public void swipeRight() {
//        if (this.mCompletionOn) {
//            pickDefaultCandidate();
//        }
//    }
//
//    public void swipeLeft() {
//        handleBackspace();
//    }
//
//    public void swipeDown() {
//        handleClose();
//    }

//
//    public void onRelease(int primaryCode) {
//    }
//
//    public void onEmojiClick(@NonNull Emoji emoji) {
//        if (emoji.isAvailable()) {
//            Log.d(GifHeaderParser.TAG, "Open Emoji: " + emoji.toString());
//            boolean isImageKeyboardSupport = false;
//            for (String mimeType : this.mimeTypes) {
//                if (emoji.compareMimeTypes(mimeType)) {
//                    isImageKeyboardSupport = true;
//                    break;
//                }
//            }
//            IntentUtils.sentEmojiTo(this, emoji, isImageKeyboardSupport);
//            return;
//        }
//        showPleaseBuyMessage(emoji);
//    }
//
//    public void showPleaseBuyMessage(Emoji emoji) {
//        Snackbar.make(this.emojisPager, (CharSequence) "showPleaseBuyMessage", 0).show();
//    }
//
//    private void updateTabs() {
//        int i = 0;
//        while (i < this.emojiAdapter.getCount()) {
//            try {
//                PageView pageView = (PageView) this.emojisPager.findViewWithTag(Integer.valueOf(i));
//                if (pageView != null) {
//                    pageView.notifyDataSetChanged();
//                }
//                i++;
//            } catch (Exception e) {
//                return;
//            }
//        }
//    }
//
//    private void initTab(View rootView) {
//        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(this.emojisPager);
//        tabLayout.setOnTabSelectedListener(new C07146());
//        this.emojisPager.setCurrentItem(1, false);
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            Tab tab = tabLayout.getTabAt(i);
//            if (tab != null) {
//                View tabView = this.emojiAdapter.getTabView(i, getApplicationContext());
//                if (tabView != null) {
//                    tabView.setTag(Integer.valueOf(i));
//                    tab.setCustomView(tabView);
//                    tab.setTag(tabView);
//                }
//                if (i == 1) {
//                    setSelectStatus(tab, true);
//                }
//            }
//        }
//    }
//
//    private Integer setSelectStatus(Tab tab, boolean bSelectStatus) {
//        Integer result = null;
//        try {
//            Object o = tab.getTag();
//            if (o != null && (o instanceof View)) {
//                View view = (View) o;
//                result = (Integer) view.getTag();
//                ImageView img = (ImageView) view.findViewById(R.id.emojiKeyboardTab);
//                ImageView imgSelected = (ImageView) view.findViewById(R.id.emojiKeyboardTabSelected);
//                if (!(img == null || imgSelected == null)) {
//                    int i;
//                    if (bSelectStatus) {
//                        i = 8;
//                    } else {
//                        i = 0;
//                    }
//                    img.setVisibility(i);
//                    if (bSelectStatus) {
//                        i = 0;
//                    } else {
//                        i = 8;
//                    }
//                    imgSelected.setVisibility(i);
//                }
//            }
//        } catch (Exception e) {
//        }
//        return result;
//    }
//
//    private void setCompletionOn(boolean mCompletionOn) {
//        this.mCompletionOn = mCompletionOn;
//    }
//
//    private void setPredictionOn(boolean mPredictionOn) {
//        this.mPredictionOn = false;
//    }
}
