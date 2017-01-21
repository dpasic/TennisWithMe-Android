package hr.vspr.dpasic.tenniswithme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.UserActionType;

public class MatchInfoActivity extends AppCompatActivity {

    private static final String MATCH = "match";

    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_info);

        ButterKnife.bind(this);

        match = getIntent().getParcelableExtra(MATCH);
    }
}
