package hr.vspr.dpasic.tenniswithme.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.MainActivity;
import hr.vspr.dpasic.tenniswithme.common.Utility;
import hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp.EditUserInfoPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp.EditUserInfoPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp.EditUserInfoView;
import hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp.UserInfoView;
import hr.vspr.dpasic.tenniswithme.model.Player;

public class EditPlayerInfoFragment extends Fragment implements EditUserInfoView {

    private EditUserInfoPresenter editUserInfoPresenter;
    private Player player;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_surname)
    EditText etSurname;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.spinner_genders)
    Spinner spinnerGenders;
    @BindView(R.id.spinner_skills)
    Spinner spinnerSkills;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_summary)
    EditText etSummary;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.activity_edit_user_info)
    LinearLayout editUserInfoView;

    public EditPlayerInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerInfoFragment.
     */
    public static EditPlayerInfoFragment newInstance(Player player) {
        EditPlayerInfoFragment fragment = new EditPlayerInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_player_info, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.title_edit_user_info);

        editUserInfoPresenter = new EditUserInfoPresenterImpl(this);

        setUserInfo();
        return view;
    }

    private void setUserInfo() {
        etName.setText(player.getFirstName());
        etSurname.setText(player.getLastName());
        etCity.setText(player.getCity());
        etAge.setText(player.getAge());

        String[] genders = getContext().getResources().getStringArray(R.array.array_genders_undef);
        String[] skills = getContext().getResources().getStringArray(R.array.array_skills_undef);

        spinnerGenders.setSelection(Utility.getIndexOfItemInArray(player.getGenderDescription(), genders));
        spinnerSkills.setSelection(Utility.getIndexOfItemInArray(player.getSkillDescription(), skills));
        etSummary.setText(player.getSummary());
    }

    @OnClick(R.id.btn_save_profile)
    public void saveProfileClick() {
        loadingProgress.setVisibility(View.VISIBLE);

        player.setFirstName(etName.getText().toString());
        player.setLastName(etSurname.getText().toString());
        player.setCity(etCity.getText().toString());
        player.setAge(etAge.getText().toString());
        player.setGenderDescription(spinnerGenders.getSelectedItem().toString());
        player.setSkillDescription(spinnerSkills.getSelectedItem().toString());

        editUserInfoPresenter.updateProfile(player);
    }

    @Override
    public void notifyRequestError(String msg) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(editUserInfoView, msg, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onUpdatedProfile(Player player) {
        loadingProgress.setVisibility(View.GONE);

        UserInfoView userInfoView = (UserInfoView) getActivity().getSupportFragmentManager()
                .findFragmentByTag(PlayerInfoFragment.class.getName());
        userInfoView.updatePlayer(player);

        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}
