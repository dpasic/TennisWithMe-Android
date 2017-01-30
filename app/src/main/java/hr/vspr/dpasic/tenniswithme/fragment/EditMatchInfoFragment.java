package hr.vspr.dpasic.tenniswithme.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

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
import hr.vspr.dpasic.tenniswithme.common.Utility;
import hr.vspr.dpasic.tenniswithme.fragment.edit_match_info_mvp.EditMatchInfoPresenter;
import hr.vspr.dpasic.tenniswithme.fragment.edit_match_info_mvp.EditMatchInfoPresenterImpl;
import hr.vspr.dpasic.tenniswithme.fragment.edit_match_info_mvp.EditMatchInfoView;
import hr.vspr.dpasic.tenniswithme.fragment.match_info_mvp.MatchInfoView;
import hr.vspr.dpasic.tenniswithme.model.Match;

public class EditMatchInfoFragment extends Fragment implements EditMatchInfoView {

    private final SimpleDateFormat SDF_DATE = new SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault());
    private final SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final SimpleDateFormat SDF_FULL_TIME = new SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault());

    private EditMatchInfoPresenter editMatchInfoPresenter;

    private Match match;
    private Calendar calendar = Calendar.getInstance();

    @BindView(R.id.tv_player1)
    TextView tvPlayer1;
    @BindView(R.id.tv_player2)
    TextView tvPlayer2;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_result)
    EditText etResult;
    @BindView(R.id.spinner_rating)
    Spinner spinnerRating;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.btn_choose_date)
    Button btnChooseDate;
    @BindView(R.id.btn_choose_time)
    Button btnChooseTime;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.activity_edit_match_info)
    LinearLayout editMatchInfoView;

    public EditMatchInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RequestMatchFragment.
     */
    public static EditMatchInfoFragment newInstance(Match match) {
        EditMatchInfoFragment fragment = new EditMatchInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.MATCH, match);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            match = getArguments().getParcelable(MainActivity.MATCH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_match_info, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.title_edit_match_info);

        editMatchInfoPresenter = new EditMatchInfoPresenterImpl(this);

        setMatchInfo();
        return view;
    }

    private void setMatchInfo() {
        tvPlayer1.setText(match.getPlayerOneName());
        tvPlayer2.setText(match.getPlayerTwoName());

        etCity.setText(match.getCityPlayed());
        etComment.setText(match.getComment());
        etResult.setText(match.getResult());

        String[] ratings = getContext().getResources().getStringArray(R.array.array_rating);
        spinnerRating.setSelection(Utility.getIndexOfItemInArray(match.getRating(), ratings));

        btnChooseDate.setText(SDF_DATE.format(match.getDatePlayed()));
        btnChooseTime.setText(SDF_TIME.format(match.getDatePlayed()));
    }

    @OnClick(R.id.btn_update_match)
    public void updateMatchClick() {
        match.setCityPlayed(etCity.getText().toString());
        match.setComment(etComment.getText().toString());
        match.setResult(etResult.getText().toString());
        match.setRating(spinnerRating.getSelectedItem().toString());

        try {
            match.setDatePlayed(SDF_FULL_TIME.parse(btnChooseDate.getText().toString() + " " + btnChooseTime.getText().toString()));
        } catch (ParseException e) {
            Log.d("DATE", e.getMessage());
        }

        loadingProgress.setVisibility(View.VISIBLE);
        editMatchInfoPresenter.updateMatch(match);
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
    public void onUpdatedMatch(Match match) {
        loadingProgress.setVisibility(View.GONE);

        MatchInfoView matchInfoView = (MatchInfoView) getActivity().getSupportFragmentManager()
                .findFragmentByTag(MatchInfoFragment.class.getName());
        matchInfoView.updateMatch(match);

        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void notifyRequestError(String msg) {
        loadingProgress.setVisibility(View.GONE);
        Snackbar.make(editMatchInfoView, msg, Snackbar.LENGTH_LONG);
    }
}
