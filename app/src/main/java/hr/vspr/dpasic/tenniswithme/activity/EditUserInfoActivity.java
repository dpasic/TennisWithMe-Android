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
import hr.vspr.dpasic.tenniswithme.model.User;

public class EditUserInfoActivity extends AppCompatActivity implements EditUserInfoView, UserInfoPublisher {

    private static final String USER = "user";

    private User user;
    private List<UserInfoSubscriber> subscribers;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_surname)
    EditText etSurname;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_sex)
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

        user = getIntent().getParcelableExtra(USER);
        subscribers = new ArrayList<>();

        setUserInfo();
    }

    private void setUserInfo() {
        etName.setText(user.getFirstName());
        etSurname.setText(user.getLastName());
        etEmail.setText(user.getEmail());
        etAge.setText(user.getAge());
        etSex.setText(user.getSex());
        etSummary.setText(user.getSummary());
    }

    @OnClick(R.id.btn_save_profile)
    public void saveProfileClick(View view) {
        notifySubscribers(user);
        finish();
    }

    @Override
    public void subscribe(UserInfoSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void notifySubscribers(User user) {
        for (UserInfoSubscriber sub : subscribers) {
            sub.update(this, user);
        }
    }
}
