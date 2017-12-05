package ae.netaq.ecards.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ae.netaq.ecards.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M.Refaat on 11/23/2017.
 */

public class MyCardsFragment extends Fragment {

    @BindView(R.id.no_data)
    RelativeLayout noData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_cards, container, false);
        ButterKnife.bind(this, view);

        return view;

    }
}
