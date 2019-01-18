package com.xy.rcs.goeasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xy.rcs.goeasy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TempFragment extends Fragment {


    private LinearLayout linearLayout;
    private View mRootView;

    public TempFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_temp, container, false);
        linearLayout=getActivity().findViewById(R.id.tital_area);
        linearLayout.setVisibility(View.GONE);
        Transition transition=new Explode();
        getActivity().getWindow().setEnterTransition(transition);
        getActivity().getWindow().setExitTransition(transition);
        return mRootView;
    }

}
