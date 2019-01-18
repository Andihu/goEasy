package com.xy.rcs.goeasy.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;

import com.xy.rcs.goeasy.R;


public class SweepCodeFragment extends Fragment {


    private static final String TAG = "SweepCodeFragment";
    protected View mRootView;
    private TextView textView;

    public SweepCodeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        startCaptureActivityForResult();
        mRootView = inflater.inflate(R.layout.fragment_sweep_code, container, false);
        textView = mRootView.findViewById(R.id.tetxt);



        return mRootView;
    }

    private void startCaptureActivityForResult() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity
                .VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity
                .VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity
                .VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case CaptureActivity.RESULT_OK:
                     //   tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        //or do sth
                        textView.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        Log.e(TAG, "onActivityResult: "+data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT) );
                        break;
                    case CaptureActivity.RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                           // tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));

                            textView.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                            Log.e(TAG, "onActivityResult: "+data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT) );
                        }
                        break;
                }
                break;
        }
    }
}
