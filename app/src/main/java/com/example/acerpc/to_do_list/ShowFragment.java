package com.example.acerpc.to_do_list;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ShowFragment extends Fragment {
    databaseHelper help;
    TextView v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        help=new databaseHelper(this.getActivity());
        return inflater.inflate(R.layout.fragment_show, container, false);
    }
    public void setText(View view)
    {
         //v=(TextView)findViewById()
    }

    // TODO: Rename method, update argument and hook method into UI event

}
