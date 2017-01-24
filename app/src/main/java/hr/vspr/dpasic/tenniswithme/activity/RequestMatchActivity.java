package hr.vspr.dpasic.tenniswithme.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.request_match_mvp.RequestMatchPresenter;
import hr.vspr.dpasic.tenniswithme.activity.request_match_mvp.RequestMatchPresenterImpl;
import hr.vspr.dpasic.tenniswithme.activity.request_match_mvp.RequestMatchView;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.Player;

public class RequestMatchActivity extends AppCompatActivity implements RequestMatchView {

    private static final String USER = "player";

    private RequestMatchPresenter requestMatchPresenter;

    private Player loginPlayer;
    private Player otherPlayer;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault());
    private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private SimpleDateFormat sdfFullDate = new SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault());

    @BindView(R.id.tv_player1)
    TextView tvPlayer1;
    @BindView(R.id.tv_player2)
    TextView tvPlayer2;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.btn_choose_date)
    Button btnChooseDate;
    @BindView(R.id.btn_choose_time)
    Button btnChooseTime;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.activity_request)
    LinearLayout activityRequestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_request);

        ButterKnife.bind(this);

        requestMatchPresenter = new RequestMatchPresenterImpl(this);

        loginPlayer = SQLite.select().from(Player.class).querySingle();
        otherPlayer = getIntent().getParcelableExtra(USER);

        tvPlayer1.setText(loginPlayer.getFullName());
        tvPlayer2.setText(otherPlayer.getFullName());

        Date currentDate = new Date();
        btnChooseDate.setText(sdfDate.format(currentDate));
        btnChooseTime.setText(sdfTime.format(currentDate));
    }

    @OnClick(R.id.btn_send_request)
    public void sendRequestClick() {
        Match requestMatch = new Match();
        requestMatch.setCityPlayed(etCity.getText().toString());
        requestMatch.setComment(etComment.getText().toString());

        try {
            requestMatch.setDatePlayed(sdfFullDate.parse(btnChooseDate.getText().toString() + " " + btnChooseTime.getText().toString()));
        } catch (ParseException e) {
            Log.d("DATE", e.getMessage());
        }

        requestMatch.setPlayerOneId(loginPlayer.getId());
        requestMatch.setPlayerOneName(loginPlayer.getFullName());
        requestMatch.setPlayerTwoId(otherPlayer.getId());
        requestMatch.setPlayerTwoName(otherPlayer.getFullName());

        loadingProgress.setVisibility(View.VISIBLE);
        requestMatchPresenter.requestMatch(requestMatch);
    }

    @OnClick(R.id.btn_choose_date)
    public void chooseDateClick() {
        try {
            calendar.setTime(sdfDate.parse(btnChooseDate.getText().toString()));
        } catch (ParseException e) {
            Log.d("CALENDAR", e.getMessage());
        }

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                btnChooseDate.setText(String.format("%s.%s.%s.", get2NumFormat(dayOfMonth), get2NumFormat(++monthOfYear), year));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.btn_choose_time)
    public void chooseTimeClick() {
        try {
            calendar.setTime(sdfTime.parse(btnChooseTime.getText().toString()));
        } catch (ParseException e) {
            Log.d("CALENDAR", e.getMessage());
        }

        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                btnChooseTime.setText(String.format("%s:%s", get2NumFormat(hour), get2NumFormat(minute)));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private String get2NumFormat(int num) {
        if (num < 10) {
            return String.format("0%s", num);
        }
        return Integer.toString(num);
    }

    @Override
    public void requestCompleted(Match match) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(activityRequestView, R.string.match_requested, Snackbar.LENGTH_LONG);
        finish();
    }

    @Override
    public void notifyRequestError(String msg) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(activityRequestView, msg, Snackbar.LENGTH_LONG);
    }
}
