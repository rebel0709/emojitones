package com.emojitones.keyboard.fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.emojitones.keyboard.R;
import com.emojitones.keyboard.keyboard.SoftKeyboard;


import java.lang.ref.WeakReference;
import java.util.List;

public  class InstallKeyboardFragment extends Fragment {

    private static final String TAG = "InstallFragment";

    private Button mBtnEnableKeyboard;
    private Button mBtnSelectKeyBoard;
    private Button mBtnStartEmoji;

    private InputMethodChangeReceiver mReceiver;

    public static Fragment newInstance() {
        return new InstallKeyboardFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static class InputMethodChangeReceiver extends BroadcastReceiver {

        private WeakReference<InstallKeyboardFragment> mActivityRef;
        public InputMethodChangeReceiver(InstallKeyboardFragment activity) {
            this.mActivityRef = new WeakReference(activity);
        }

        public void onReceive(Context context, Intent intent) {
            InstallKeyboardFragment act = (InstallKeyboardFragment) this.mActivityRef.get();

            if (act != null) {
                act.updateViews();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.installkeyboard_fragment, container, false);

        mReceiver = new InputMethodChangeReceiver(this);

        this.mBtnEnableKeyboard = (Button) rootView.findViewById(R.id.btnGoToInputSettings);
        this.mBtnEnableKeyboard.setOnClickListener(new enableKeyboardListener());

        this.mBtnSelectKeyBoard = (Button) rootView.findViewById(R.id.btnSelectKeyboard);
        this.mBtnSelectKeyBoard.setOnClickListener(new selectKeyboardListener());

        this.mBtnStartEmoji = (Button) rootView.findViewById(R.id.btnstartemoji);


        return rootView;
    }

    class enableKeyboardListener implements View.OnClickListener {
        enableKeyboardListener() {
        }

        public void onClick(View v) {
           openLangInputActivity();
        }
    }

    class selectKeyboardListener implements View.OnClickListener {
        selectKeyboardListener() {
        }

        public void onClick(View v) {
            showPicker();
        }
    }

    private void openLangInputActivity() {
        startActivity(new Intent("android.settings.INPUT_METHOD_SETTINGS"));
    }

    private void showPicker() {
        InputMethodManager imManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        if (imManager != null) {
            imManager.showInputMethodPicker();
        }
    }

    private void updateViews() {
        if (!isInputMethodEnabled()) {
            this.mBtnEnableKeyboard.setEnabled(true);
            this.mBtnSelectKeyBoard.setEnabled(false);
        } else if (isInputMethodSelected()) {
            this.mBtnEnableKeyboard.setEnabled(false);
            this.mBtnSelectKeyBoard.setEnabled(false);
        } else {
            this.mBtnEnableKeyboard.setEnabled(false);
            this.mBtnSelectKeyBoard.setEnabled(true);
        }
    }

    private boolean isInputMethodEnabled() {

        InputMethodManager imManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        if (imManager != null) {
            List<InputMethodInfo> list = imManager.getEnabledInputMethodList();
            ComponentName myInputMethod = new ComponentName(getActivity(), SoftKeyboard.class);
            for (InputMethodInfo info : list) {
                if (myInputMethod.flattenToShortString().equals(info.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInputMethodSelected() {
        String id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.DEFAULT_INPUT_METHOD);
        Log.d(TAG, "ID: " + id);
        ComponentName defaultInputMethod = ComponentName.unflattenFromString(id);
        Log.d(TAG, "NAME: " + defaultInputMethod);
        ComponentName myInputMethod = new ComponentName(getActivity(), SoftKeyboard.class);
        Log.d(TAG, "MY NAME: " + myInputMethod);
        return myInputMethod.equals(defaultInputMethod);
    }

    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_INPUT_METHOD_CHANGED));
        updateViews();
    }

    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }
}