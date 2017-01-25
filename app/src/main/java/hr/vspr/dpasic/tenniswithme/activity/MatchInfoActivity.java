package hr.vspr.dpasic.tenniswithme.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.model.ActionType;
import hr.vspr.dpasic.tenniswithme.model.Match;

public class MatchInfoActivity extends AppCompatActivity {

    private final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault());

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_info);

        ButterKnife.bind(this);

        match = getIntent().getParcelableExtra(MainActivity.MATCH);
        actionType = (ActionType) getIntent().getSerializableExtra(MainActivity.ACTION_TYPE);

        prepareViewBasedOnActionType();
        setMatchInfo();
    }

    private void prepareViewBasedOnActionType() {
        switch (actionType) {
            case VIEW_AND_EDIT:
                fabEdit.setVisibility(View.VISIBLE);
                btnConfirmMatch.setVisibility(View.GONE);
                break;
            case CONFIRM_FRIENDSHIP:
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
        //TODO: complete action
    }
}
