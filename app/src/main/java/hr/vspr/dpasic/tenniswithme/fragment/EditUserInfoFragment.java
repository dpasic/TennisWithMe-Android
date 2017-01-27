package hr.vspr.dpasic.tenniswithme.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.MainActivity;
import hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp.EditUserInfoView;
import hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp.UserInfoPublisher;
import hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp.UserInfoSubscriber;
import hr.vspr.dpasic.tenniswithme.model.Player;

public class EditUserInfoFragment extends Fragment implements EditUserInfoView, UserInfoPublisher {

    private Player player;
    private List<UserInfoSubscriber> subscribers;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_surname)
    EditText etSurname;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_gender)
    EditText etSex;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_summary)
    EditText etSummary;

    public EditUserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserInfoFragment.
     */
    public static EditUserInfoFragment newInstance(Player player) {
        EditUserInfoFragment fragment = new EditUserInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            player = getArguments().getParcelable(MainActivity.PLAYER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_user_info, container, false);
        ButterKnife.bind(this, view);

        subscribers = new ArrayList<>();

        setUserInfo();

        return view;
    }

    private void setUserInfo() {
        etName.setText(player.getFirstName());
        etSurname.setText(player.getLastName());
        etEmail.setText(player.getEmail());
        etAge.setText(player.getAge());
        etSex.setText(player.getGender());
        etSummary.setText(player.getSummary());
    }

    @OnClick(R.id.btn_save_profile)
    public void saveProfileClick(View view) {
        notifySubscribers(player);

        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void subscribe(UserInfoSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void notifySubscribers(Player player) {
        for (UserInfoSubscriber sub : subscribers) {
            sub.update(this, player);
        }
    }

    @Override
    public void notifyRequestError(String msg) {

    }
}
