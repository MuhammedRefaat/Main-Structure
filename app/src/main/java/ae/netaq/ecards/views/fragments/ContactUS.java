package ae.netaq.ecards.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ae.netaq.ecards.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M.Refaat on 11/29/2017.
 */

public class ContactUS extends Fragment {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.subject)
    EditText subject;
    @BindView(R.id.message)
    EditText message;

    @BindView(R.id.submit)
    Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // get the main layout
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        // bind the views
        ButterKnife.bind(this, view);
        // set Submit listener
        submit.setOnClickListener(onSubmitClick);
        return view;
    }

    /**
     * The onClick Listener for the Sub,it Button
     */
    private View.OnClickListener onSubmitClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO
        }
    };

}
