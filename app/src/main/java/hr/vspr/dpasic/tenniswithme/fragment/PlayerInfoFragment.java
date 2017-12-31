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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.LoginActivity;
import hr.vspr.dpasic.tenniswithme.activity.MainActivity;
import hr.vspr.dpasic.tenniswithme.common.Utility;
import hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp.UserInfoPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp.UserInfoPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.user_info_mvp.UserInfoView;
import hr.vspr.dpasic.tenniswithme.model.Player;
import hr.vspr.dpasic.tenniswithme.model.ActionType;
import hr.vspr.dpasic.tenniswithme.model.PlayersRating;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerInfoFragment extends Fragment implements UserInfoView {

    private Player player;
    private ActionType actionType;
    private OnFragmentInteractionListener mListener;
    private UserInfoPresenter userInfoPresenter;

    @BindView(R.id.tv_full_name)
    TextView tvFullName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_skill)
    TextView tvSkill;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.tv_wins)
    TextView tvWins;
    @BindView(R.id.tv_overall_rating)
    TextView tvOverallRating;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.spinner_rating)
    Spinner spinnerRating;
    @BindView(R.id.fab_edit)
    FloatingActionButton fabEdit;
    @BindView(R.id.layout_spinner_rating)
    LinearLayout layoutSpinnerRating;
    @BindView(R.id.btn_confirm_friendship)
    Button btnConfirmFriendship;
    @BindView(R.id.btn_request_match)
    Button btnRequestMatch;
    @BindView(R.id.btn_request_friendship)
    Button btnRequestFriendship;
    @BindView(R.id.btn_udpate_rating)
    Button btnUdpateRating;
    @BindView(R.id.btn_sign_out)
    Button btnSignOut;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.userInfoView)
    FrameLayout userInfoView;

    public PlayerInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerInfoFragment.
     */
    public static PlayerInfoFragment newInstance(Player player, ActionType actionType) {
        PlayerInfoFragment fragment = new PlayerInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_player_info, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.title_user_info);

        userInfoPresenter = new UserInfoPresenterImpl(this);

        if (actionType != ActionType.VIEW_AND_EDIT) {
            userInfoPresenter.getPlayersRating(player);
        }

        prepareViewBasedOnActionType();
        updateUserInfo();

        return view;
    }

    private void prepareViewBasedOnActionType() {
        switch (actionType) {
            case VIEW_AND_EDIT:
                fabEdit.setVisibility(View.VISIBLE);
                layoutSpinnerRating.setVisibility(View.GONE);
                btnConfirmFriendship.setVisibility(View.GONE);
                btnRequestMatch.setVisibility(View.GONE);
                btnRequestFriendship.setVisibility(View.GONE);
                btnUdpateRating.setVisibility(View.GONE);
                btnSignOut.setVisibility(View.VISIBLE);
                break;
            case CONFIRM_FRIENDSHIP:
                fabEdit.setVisibility(View.GONE);
                layoutSpinnerRating.setVisibility(View.VISIBLE);
                btnConfirmFriendship.setVisibility(View.VISIBLE);
                btnRequestMatch.setVisibility(View.VISIBLE);
                btnRequestFriendship.setVisibility(View.GONE);
                btnUdpateRating.setVisibility(View.VISIBLE);
                btnSignOut.setVisibility(View.GONE);
                break;
            case REQUEST_MATCH:
                fabEdit.setVisibility(View.GONE);
                layoutSpinnerRating.setVisibility(View.VISIBLE);
                btnConfirmFriendship.setVisibility(View.GONE);
                btnRequestMatch.setVisibility(View.VISIBLE);
                btnRequestFriendship.setVisibility(View.GONE);
                btnUdpateRating.setVisibility(View.VISIBLE);
                btnSignOut.setVisibility(View.GONE);
                break;
            case REQUEST_FRIENDSHIP:
                fabEdit.setVisibility(View.GONE);
                layoutSpinnerRating.setVisibility(View.VISIBLE);
                btnConfirmFriendship.setVisibility(View.GONE);
                btnRequestMatch.setVisibility(View.VISIBLE);
                btnRequestFriendship.setVisibility(View.VISIBLE);
                btnUdpateRating.setVisibility(View.VISIBLE);
                btnSignOut.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void updateUserInfo() {
        tvFullName.setText(player.getFullName());
        tvEmail.setText(player.getEmail());
        tvCity.setText(player.getCity());
        tvAge.setText(player.getAge());
        tvGender.setText(player.getGenderDescription());
        tvSkill.setText(player.getSkillDescription());
        tvSummary.setText(player.getSummary());

        if (player.isHasPlatinumBadge()) {
            tvPoints.setText(String.format("%s (Platinum Badge)", player.getPoints()));
        } else if (player.isHasGoldBadge()) {
            tvPoints.setText(String.format("%s (Gold Badge)", player.getPoints()));
        } else if (player.isHasSilverBadge()) {
            tvPoints.setText(String.format("%s (Silver Badge)", player.getPoints()));
        } else if (player.isHasBronzeBadge()) {
            tvPoints.setText(String.format("%s (Bronze Badge)", player.getPoints()));
        } else {
            tvPoints.setText(String.format("%s", player.getPoints()));
        }

        if (player.isHasWinnerMasterBadge()) {
            tvWins.setText(String.format("%s (Master Badge)", player.getWonGames()));
        } else if (player.isHasWinnerChallengerBadge()) {
            tvWins.setText(String.format("%s (Challenger Badge)", player.getWonGames()));
        } else if (player.isHasWinnerRookieBadge()) {
            tvWins.setText(String.format("%s (Rookie Badge)", player.getWonGames()));
        } else {
            tvWins.setText(String.format("%s", player.getWonGames()));
        }

        if (player.isFavoritePlayer()) {
            tvOverallRating.setText(String.format("%.1f (Favorite Player Badge)", Double.parseDouble(player.getOverallRating())));
        } else if (player.getOverallRating() != null) {
            tvOverallRating.setText(String.format("%.1f", Double.parseDouble(player.getOverallRating())));
        }
    }

    @OnClick(R.id.fab_edit)
    public void editUserInfoClick() {
        Fragment fragment = EditPlayerInfoFragment.newInstance(player);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, EditPlayerInfoFragment.class.getName())
                .addToBackStack(EditPlayerInfoFragment.class.getName()).commit();
    }

    @OnClick(R.id.btn_confirm_friendship)
    public void confirmFriendshipClick() {
        loadingProgress.setVisibility(View.VISIBLE);
        userInfoPresenter.confirmFriendship(player);
    }

    @OnClick(R.id.btn_request_friendship)
    public void requestFriendshipClick() {
        loadingProgress.setVisibility(View.VISIBLE);
        userInfoPresenter.requestFriendship(player);
    }

    @OnClick(R.id.btn_sign_out)
    public void signOutClick() {
        loadingProgress.setVisibility(View.VISIBLE);
        userInfoPresenter.signOut();
    }

    @OnClick(R.id.btn_request_match)
    public void requestMatchClick() {
        Fragment fragment = RequestMatchFragment.newInstance(player);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, RequestMatchFragment.class.getName())
                .addToBackStack(RequestMatchFragment.class.getName()).commit();
    }

    @OnClick(R.id.btn_udpate_rating)
    public void updateRatingClick() {
        loadingProgress.setVisibility(View.VISIBLE);

        PlayersRating playersRating = new PlayersRating();
        playersRating.setRatedId(player.getId());
        playersRating.setRatingDescription(spinnerRating.getSelectedItem().toString());

        userInfoPresenter.createOrUpdatePlayersRating(playersRating);
    }

    @Override
    public void acceptedFriendship() {
        loadingProgress.setVisibility(View.GONE);
        btnConfirmFriendship.setVisibility(View.GONE);

        Snackbar.make(userInfoView, R.string.confirmed_friendship, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void friendshipRequested() {
        loadingProgress.setVisibility(View.GONE);
        btnRequestFriendship.setVisibility(View.GONE);

        Snackbar.make(userInfoView, R.string.requested_friendship, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void signedOut() {
        loadingProgress.setVisibility(View.GONE);

        Intent loginActivity = new Intent(getContext(), LoginActivity.class);
        loginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginActivity);
    }

    @Override
    public void updatePlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setPlayersRating(PlayersRating playersRating) {
        if (playersRating == null) {
            return;
        }

        String[] ratings = getContext().getResources().getStringArray(R.array.array_rating);
        spinnerRating.setSelection(Utility.getIndexOfItemInArray(playersRating.getRatingDescription(), ratings));
    }

    @Override
    public void onUpdatePlayersRating(PlayersRating playersRating) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(userInfoView, R.string.updated_players_rating, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void notifyRequestError(String msg) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(userInfoView, msg, Snackbar.LENGTH_LONG).show();
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
        void onFragmentInteraction(Uri uri);
    }
}
