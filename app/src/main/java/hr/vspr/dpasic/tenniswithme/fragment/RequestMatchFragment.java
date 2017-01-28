package hr.vspr.dpasic.tenniswithme.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import hr.vspr.dpasic.tenniswithme.activity.MainActivity;
import hr.vspr.dpasic.tenniswithme.fragment.request_match_mvp.RequestMatchPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.request_match_mvp.RequestMatchPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.request_match_mvp.RequestMatchView;
import hr.vspr.dpasic.tenniswithme.model.Match;
import hr.vspr.dpasic.tenniswithme.model.Player;

public class RequestMatchFragment extends Fragment implements RequestMatchView {

    private final SimpleDateFormat SDF_DATE = new SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault());
    private final SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final SimpleDateFormat SDF_FULL_TIME = new SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault());

    private RequestMatchPresenter requestMatchPresenter;

    private Player loginPlayer;
    private Player otherPlayer;
    private Calendar calendar = Calendar.getInstance();

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

    public RequestMatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerInfoFragment.
     */
    public static RequestMatchFragment newInstance(Player player) {
        RequestMatchFragment fragment = new RequestMatchFragment();
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            otherPlayer = getArguments().getParcelable(MainActivity.PLAYER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_request, container, false);
        ButterKnife.bind(this, view);

        requestMatchPresenter = new RequestMatchPresenterImpl(this);

        loginPlayer = SQLite.select().from(Player.class).querySingle();

        tvPlayer1.setText(loginPlayer.getFullName());
        tvPlayer2.setText(otherPlayer.getFullName());

        Date currentDate = new Date();
        btnChooseDate.setText(SDF_DATE.format(currentDate));
        btnChooseTime.setText(SDF_TIME.format(currentDate));

        return view;
    }

    @OnClick(R.id.btn_send_request)
    public void sendRequestClick() {
        Match requestMatch = new Match();
        requestMatch.setCityPlayed(etCity.getText().toString());
        requestMatch.setComment(etComment.getText().toString());

        try {
            requestMatch.setDatePlayed(SDF_FULL_TIME.parse(btnChooseDate.getText().toString() + " " + btnChooseTime.getText().toString()));
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
            calendar.setTime(SDF_DATE.parse(btnChooseDate.getText().toString()));
        } catch (ParseException e) {
            Log.d("CALENDAR", e.getMessage());
        }

        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                btnChooseDate.setText(String.format("%s.%s.%s.", get2NumFormat(dayOfMonth), get2NumFormat(++monthOfYear), year));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.btn_choose_time)
    public void chooseTimeClick() {
        try {
            calendar.setTime(SDF_TIME.parse(btnChooseTime.getText().toString()));
        } catch (ParseException e) {
            Log.d("CALENDAR", e.getMessage());
        }

        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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

        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void notifyRequestError(String msg) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(activityRequestView, msg, Snackbar.LENGTH_LONG);
    }
}
