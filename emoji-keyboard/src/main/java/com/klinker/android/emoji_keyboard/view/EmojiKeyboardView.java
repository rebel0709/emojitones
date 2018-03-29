package com.klinker.android.emoji_keyboard.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.athbk.ultimatetablayout.IFTabAdapter;
import com.athbk.ultimatetablayout.UltimateTabLayout;
import com.klinker.android.emoji_keyboard.EmojiKeyboardService;
import com.klinker.android.emoji_keyboard.adapter.EmojiPagerAdapter;
import com.klinker.android.emoji_keyboard_trial.R;

public class EmojiKeyboardView extends View implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener{

    private ViewPager viewPager;
    /*private PagerSlidingTabStrip pagerSlidingTabStrip;
    UltimateTabLayout pagerSlidingTabStrip;*/
    //private LinearLayout layout;
    private FrameLayout layout;

    private EmojiPagerAdapter emojiPagerAdapter;
    private EmojiKeyboardService emojiKeyboardService;

    public EmojiKeyboardView(Context context) {
        super(context);
        initialize(context);
    }

    public EmojiKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public EmojiKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }
    ImageView imvSwitchKeyboard, imvSelKeyboard, imvRecent, imvAllEmoji, imvBackspace;
    TextView tvShare;
    private void initialize(Context context) {

        emojiKeyboardService = (EmojiKeyboardService) context;

        LayoutInflater inflater = (LayoutInflater)   getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //layout = (LinearLayout) inflater.inflate(R.layout.keyboard_main, null);
        layout = (FrameLayout) inflater.inflate(R.layout.keyboard_main, null);

        viewPager = (ViewPager) layout.findViewById(R.id.emojiKeyboard);
        imvSwitchKeyboard =(ImageView)layout.findViewById(R.id.switch_keyboard);imvSwitchKeyboard.setOnClickListener(this);
        tvShare = (TextView)layout.findViewById(R.id.tv_share);tvShare.setOnClickListener(this);
        imvSelKeyboard = (ImageView)layout.findViewById(R.id.select_keyboard);imvSelKeyboard.setOnClickListener(this);
        imvRecent = (ImageView)layout.findViewById(R.id.recent);imvRecent.setOnClickListener(this);
        imvAllEmoji=(ImageView)layout.findViewById(R.id.all_emoji);imvAllEmoji.setOnClickListener(this);
        imvBackspace =(ImageView)layout.findViewById(R.id.backspace);imvBackspace.setOnClickListener(this);

        emojiPagerAdapter = new EmojiPagerAdapter(context, viewPager, height);

        viewPager.setAdapter(emojiPagerAdapter);

        //setupDeleteButton();

        viewPager.setCurrentItem(1);
        imvAllEmoji.setSelected(true);

        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this);
    }

    public View getView() {
        return layout;
    }

    public void notifyDataSetChanged() {
        emojiPagerAdapter.notifyDataSetChanged();
        viewPager.refreshDrawableState();
    }

    /*private void setupDeleteButton() {

        Button delete = (Button) layout.findViewById(R.id.deleteButton);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiKeyboardService.sendDownAndUpKeyEvent(KeyEvent.KEYCODE_DEL, 0);
            }
        });

        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                emojiKeyboardService.switchToPreviousInputMethod();
                return false;
            }
        });
    }*/


    private int width;
    private int height;
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);

        Log.d("emojiKeyboardView", width +" : " + height);
        setMeasuredDimension(width, height);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Log.d("sharedPreferenceChange", "function called on change of shared preferences with key " + key);
        if (key.equals("icon_set")){
            emojiPagerAdapter = new EmojiPagerAdapter(getContext(), viewPager, height);
            viewPager.setAdapter(emojiPagerAdapter);
            this.invalidate();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.switch_keyboard) {
            emojiKeyboardService.backToKeyBoard();
        } else if (id == R.id.tv_share) {

        }else if (id == R.id.select_keyboard) {
            InputMethodManager ime=(InputMethodManager)emojiKeyboardService.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(ime!=null) {
                ime.showInputMethodPicker();
            }
        }else if (id == R.id.recent) {
            imvRecent.setSelected(true);
            imvAllEmoji.setSelected(false);
            viewPager.setCurrentItem(0);
        }else if (id == R.id.all_emoji) {
            imvRecent.setSelected(false);
            imvAllEmoji.setSelected(true);
            viewPager.setCurrentItem(1);
        }else if (id == R.id.backspace) {
            //emojiKeyboardService.sendDownAndUpKeyEvent(KeyEvent.KEYCODE_DEL, true);
            emojiKeyboardService.sendDownAndUpKeyEvent(KeyEvent.KEYCODE_DEL, 1);
        }
    }
}