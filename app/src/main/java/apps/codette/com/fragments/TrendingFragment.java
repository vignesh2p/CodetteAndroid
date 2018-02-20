package apps.codette.com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.codette.com.pingme.R;


/**
 * Created by user on 02-02-2018.
 */

public class TrendingFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_layout, container, false);
        return view;
    }
}
