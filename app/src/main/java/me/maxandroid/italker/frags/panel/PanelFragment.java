package me.maxandroid.italker.frags.panel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.maxandroid.italker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PanelFragment extends android.support.v4.app.Fragment {


    public PanelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_panel, container, false);
    }


    public void showFace() {

    }

    public void showRecord() {

    }

    public void showGallery() {

    }
}
