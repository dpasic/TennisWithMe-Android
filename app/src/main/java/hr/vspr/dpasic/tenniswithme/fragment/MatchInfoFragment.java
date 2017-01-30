package hr.vspr.dpasic.tenniswithme.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.MainActivity;
import hr.vspr.dpasic.tenniswithme.fragment.match_info_mvp.MatchInfoPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.match_info_mvp.MatchInfoPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.match_info_mvp.MatchInfoView;
import hr.vspr.dpasic.tenniswithme.model.ActionType;
import hr.vspr.dpasic.tenniswithme.model.Match;

public class MatchInfoFragment extends Fragment implements MatchInfoView {

    private final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault());

    private MatchInfoPresenter matchInfoPresenter;
    private Match match;
    private ActionType actionType;

    @BindView(R.id.tv_player1)
    TextView tvPlayer1;
    @BindView(R.id.tv_player2)
    TextView tvPlayer2;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.fab_edit)
    FloatingActionButton fabEdit;
    @BindView(R.id.btn_confirm_match)
    Button btnConfirmMatch;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.activity_match_info)
    RelativeLayout matchInfoView;

    public MatchInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerInfoFragment.
     */
    public static MatchInfoFragment newInstance(Match match, ActionType actionType) {
        MatchInfoFragment fragment = new MatchInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.MATCH, match);
        args.putSerializable(MainActivity.ACTION_TYPE, actionType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            match = getArguments().getParcelable(MainActivity.MATCH);
            actionType = (ActionType) getArguments().getSerializable(MainActivity.ACTION_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_info, container, false);
        ButterKnife.bind(this, view);

        matchInfoPresenter = new MatchInfoPresenterImpl(this);

        prepareViewBasedOnActionType();
        setMatchInfo();

        return view;
    }

    private void prepareViewBasedOnActionType() {
        switch (actionType) {
            case VIEW_AND_EDIT:
                fabEdit.setVisibility(View.VISIBLE);
                btnConfirmMatch.setVisibility(View.GONE);
                break;
            case CONFIRM_MATCH:
                fabEdit.setVisibility(View.GONE);
                btnConfirmMatch.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setMatchInfo() {
        tvPlayer1.setText(match.getPlayerOneName());
        tvPlayer2.setText(match.getPlayerTwoName());
        tvCity.setText(match.getCityPlayed());
        tvComment.setText(match.getComment());
        tvRating.setText(match.getRating());
        tvResult.setText(match.getResult());
        tvTime.setText(SDF.format(match.getDatePlayed()));
    }

    @OnClick(R.id.btn_confirm_match)
    public void confirmMatchClick() {
        loadingProgress.setVisibility(View.VISIBLE);
        matchInfoPresenter.confirmMatch(match);
    }

    @OnClick(R.id.fab_edit)
    public void editMatchInfoClick() {
        Fragment fragment = EditMatchInfoFragment.newInstance(match);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, EditMatchInfoFragment.class.getName())
                .addToBackStack(EditMatchInfoFragment.class.getName()).commit();

        getActivity().setTitle(R.string.title_edit_match_info);
    }

    @Override
    public void acceptedMatch() {
        loadingProgress.setVisibility(View.GONE);
        btnConfirmMatch.setVisibility(View.GONE);
        fabEdit.setVisibility(View.VISIBLE);

        Snackbar.make(matchInfoView, R.string.confirmed_match, Snackbar.LENGTH_LONG);
    }

    @Override
    public void updateMatch(Match match) {
        this.match = match;
    }

    @Override
    public void notifyRequestError(String msg) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(matchInfoView, msg, Snackbar.LENGTH_LONG);
    }
}
