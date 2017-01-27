package hr.vspr.dpasic.tenniswithme.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.MainActivity;
import hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp.UserInfoPublisher;
import hr.vspr.dpasic.tenniswithme.fragment.edit_user_info_mvp.UserInfoSubscriber;
import hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp.UserInfoPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp.UserInfoPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp.UserInfoView;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.ActionType;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment implements UserInfoView, UserInfoSubscriber {

    private Player player;
    private ActionType actionType;
    private OnFragmentInteractionListener mListener;
    private UserInfoPresenter userInfoPresenter;

    @BindView(R.id.tv_full_name)
    TextView tvFullName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_gender)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.fab_edit)
    FloatingActionButton fabEdit;
    @BindView(R.id.btn_confirm_friendship)
    Button btnConfirmFriendship;
    @BindView(R.id.btn_request_match)
    Button btnRequestMatch;
    @BindView(R.id.btn_request_friendship)
    Button btnRequestFriendship;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.userInfoView)
    FrameLayout userInfoView;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserInfoFragment.
     */
    public static UserInfoFragment newInstance(Player player, ActionType actionType) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.PLAYER, player);
        args.putSerializable(MainActivity.ACTION_TYPE, actionType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            player = getArguments().getParcelable(MainActivity.PLAYER);
            actionType = (ActionType) getArguments().getSerializable(MainActivity.ACTION_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, view);

        userInfoPresenter = new UserInfoPresenterImpl(this);

        prepareViewBasedOnActionType();
        updateUserInfo();

        return view;
    }

    private void prepareViewBasedOnActionType() {
        switch (actionType) {
            case VIEW_AND_EDIT:
                fabEdit.setVisibility(View.VISIBLE);
                btnConfirmFriendship.setVisibility(View.GONE);
                btnRequestMatch.setVisibility(View.GONE);
                btnRequestFriendship.setVisibility(View.GONE);
                break;
            case CONFIRM_FRIENDSHIP:
                fabEdit.setVisibility(View.GONE);
                btnConfirmFriendship.setVisibility(View.VISIBLE);
                btnRequestMatch.setVisibility(View.VISIBLE);
                btnRequestFriendship.setVisibility(View.GONE);
                break;
            case REQUEST_MATCH:
                fabEdit.setVisibility(View.GONE);
                btnConfirmFriendship.setVisibility(View.GONE);
                btnRequestMatch.setVisibility(View.VISIBLE);
                btnRequestFriendship.setVisibility(View.GONE);
                break;
            case REQUEST_FRIENDSHIP:
                fabEdit.setVisibility(View.GONE);
                btnConfirmFriendship.setVisibility(View.GONE);
                btnRequestMatch.setVisibility(View.VISIBLE);
                btnRequestFriendship.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO: read player info from REST
    }

    private void updateUserInfo() {
        tvFullName.setText(player.getFullName());
        tvEmail.setText(player.getEmail());
        tvAge.setText(player.getAge());
        tvSex.setText(player.getGender());
        tvSummary.setText(player.getSummary());
    }

    @OnClick(R.id.fab_edit)
    public void editUserInfoClick() {
        Fragment fragment = EditUserInfoFragment.newInstance(player);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, EditUserInfoFragment.class.getName())
                .addToBackStack(EditUserInfoFragment.class.getName()).commit();

        getActivity().setTitle(R.string.title_edit_user_info);
    }

    @OnClick(R.id.btn_confirm_friendship)
    public void confirmFriendshipClick() {
        loadingProgress.setVisibility(View.VISIBLE);
        userInfoPresenter.confirmFriendship(player);
    }

    @OnClick(R.id.btn_request_friendship)
    public void requestFriendshipClick() {

    }

    @OnClick(R.id.btn_request_match)
    public void requestMatchClick() {
        Fragment fragment = RequestMatchFragment.newInstance(player);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, RequestMatchFragment.class.getName())
                .addToBackStack(RequestMatchFragment.class.getName()).commit();

        getActivity().setTitle(R.string.title_request_match);
    }

    @Override
    public void acceptFriendship() {
        loadingProgress.setVisibility(View.GONE);

        btnConfirmFriendship.setVisibility(View.GONE);
        btnRequestMatch.setVisibility(View.VISIBLE);

        Snackbar.make(userInfoView, R.string.confirmed_friendship, Snackbar.LENGTH_LONG);
    }

    @Override
    public void update(UserInfoPublisher publisher, Player player) {
        this.player = player;
        updateUserInfo();
    }

    @Override
    public void notifyRequestError(String msg) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(userInfoView, msg, Snackbar.LENGTH_LONG);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
