package hr.vspr.dpasic.tenniswithme.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import hr.vspr.dpasic.tenniswithme.model.Player;

public class RequestMatchActivity extends AppCompatActivity {

    private static final String USER = "player";

    private Player loginPlayer;
    private Player otherPlayer;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault());
    private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_request);

        ButterKnife.bind(this);

        loginPlayer = SQLite.select().from(Player.class).querySingle();
        otherPlayer = getIntent().getParcelableExtra(USER);

        tvPlayer1.setText(loginPlayer.getFullName());
        tvPlayer2.setText(otherPlayer.getFullName());

        Date currentDate = new Date();
        btnChooseDate.setText(sdfDate.format(currentDate));
        btnChooseTime.setText(sdfTime.format(currentDate));
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
}
