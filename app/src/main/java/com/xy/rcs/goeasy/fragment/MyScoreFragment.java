package com.xy.rcs.goeasy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xy.rcs.goeasy.R;

public class MyScoreFragment extends Fragment {

    protected View mRootView;

    public MyScoreFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_my_score, container, false);



        return mRootView;
    }

}
