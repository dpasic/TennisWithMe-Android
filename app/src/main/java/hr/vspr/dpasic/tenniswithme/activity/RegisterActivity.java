package hr.vspr.dpasic.tenniswithme.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.activity.register_mvp.RegisterPresenterImpl;
import hr.vspr.dpasic.tenniswithme.activity.register_mvp.RegisterView;
import hr.vspr.dpasic.tenniswithme.common.RestView;
import hr.vspr.dpasic.tenniswithme.view_model.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private RegisterPresenterImpl registerPresenter;

    @BindView(R.id.actv_email)
    AutoCompleteTextView actvEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.register_progress)
    ProgressBar registerProgress;
    @BindView(R.id.register_view)
    LinearLayout registerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        registerPresenter = new RegisterPresenterImpl(this);
    }

    @OnClick(R.id.email_register_button)
    public void registerClick(View view) {
        registerProgress.setVisibility(View.VISIBLE);
        registerPresenter.doRegister();
    }

    @OnClick(R.id.goto_login_button)
    public void goToLoginClick(View view) {
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }

    @Override
    public RegisterViewModel getRegisterViewModel() {
        String email = actvEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        return new RegisterViewModel(email, password, confirmPassword);
    }

    @Override
    public void goToApp() {
        registerProgress.setVisibility(View.GONE);

        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
    }

    @Override
    public void notifyRequestError(String msg) {
        registerProgress.setVisibility(View.GONE);
        Snackbar.make(registerView, msg, Snackbar.LENGTH_LONG).show();
    }
}
