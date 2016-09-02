package com.gari.canarychallenge.contentefragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gari.canarychallenge.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TurnbyturnFragment extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String flagstring;



    public TurnbyturnFragment() {
        // Required empty public constructor
    }
   TextView contente_textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_turnbyturn, container, false);
        sharedPreferences=getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        flagstring=sharedPreferences.getString("flag",null);
        contente_textview=(TextView)view.findViewById(R.id.details_testview
        );

        contente_textview.setMovementMethod(new ScrollingMovementMethod());

        if(flagstring=="5km"){
            contente_textview.setText(Html.fromHtml(getString(R.string.fivekiro)));
        }
        else if (flagstring=="50km"){

            contente_textview.setText(Html.fromHtml(getString(R.string.fiftenkiro)));
        }
        else if (flagstring=="50mile"){

            contente_textview.setText(Html.fromHtml(getString(R.string.fiftenmile)));
        }
        else if (flagstring=="75km"){

            contente_textview.setText(Html.fromHtml(getString(R.string.houndmile)));
        }
        else if (flagstring=="100mile"){

            contente_textview.setText(Html.fromHtml(getString(R.string.severnkiro)));
        }
        else {
            contente_textview.setText(Html.fromHtml(getString(R.string.severnkiro)));

        }


        // Inflate the layout for this fragment
        return view;
    }

}
