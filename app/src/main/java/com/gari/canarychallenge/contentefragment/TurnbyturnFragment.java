package com.gari.canarychallenge.contentefragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gari.canarychallenge.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TurnbyturnFragment extends Fragment {


    public TurnbyturnFragment() {
        // Required empty public constructor
    }
   TextView contente_textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_turnbyturn, container, false);

        contente_textview=(TextView)view.findViewById(R.id.details_testview
        );

        contente_textview.setText(Html.fromHtml(get));

        // Inflate the layout for this fragment
        return view;
    }

}
