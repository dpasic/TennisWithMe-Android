package hr.vspr.dpasic.tenniswithme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.edit_user_info_mvp.EditUserInfoView;
import hr.vspr.dpasic.tenniswithme.activity.edit_user_info_mvp.UserInfoPublisher;
import hr.vspr.dpasic.tenniswithme.activity.edit_user_info_mvp.UserInfoSubscriber;
import hr.vspr.dpasic.tenniswithme.model.Player;

public class EditUserInfoActivity extends AppCompatActivity implements EditUserInfoView, UserInfoPublisher {

    private static final String USER = "player";

    private Player player;
    private List<UserInfoSubscriber> subscribers;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_surname)
    EditText etSurname;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_gender)
    EditText etSex;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_summary)
    EditText etSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        ButterKnife.bind(this);

        player = getIntent().getParcelableExtra(USER);
        subscribers = new ArrayList<>();

        setUserInfo();
    }

    private void setUserInfo() {
        etName.setText(player.getFirstName());
        etSurname.setText(player.getLastName());
        etEmail.setText(player.getEmail());
        etAge.setText(player.getAge());
        etSex.setText(player.getGender());
        etSummary.setText(player.getSummary());
    }

    @OnClick(R.id.btn_save_profile)
    public void saveProfileClick(View view) {
        notifySubscribers(player);
        finish();
    }

    @Override
    public void subscribe(UserInfoSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void notifySubscribers(Player player) {
        for (UserInfoSubscriber sub : subscribers) {
            sub.update(this, player);
        }
    }

    @Override
    public void notifyRequestError(String msg) {

    }
}
