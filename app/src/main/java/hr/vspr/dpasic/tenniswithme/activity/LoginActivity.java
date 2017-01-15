package hr.vspr.dpasic.tenniswithme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.login_mvp.LoginPresenterImpl;
import hr.vspr.dpasic.tenniswithme.login_mvp.LoginView;
import hr.vspr.dpasic.tenniswithme.view_model.LoginViewModel;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenterImpl loginPresenter;

    @BindView(R.id.actv_email)
    AutoCompleteTextView actvEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginPresenter = new LoginPresenterImpl(this);

        if (loginPresenter.tokenExists()) {
            openMainActivity();
        }
    }

    @OnClick(R.id.email_sign_in_button)
    public void signInClick(View view) {
        loginPresenter.doLogin();
    }

    @OnClick(R.id.goto_register_button)
    public void goToRegisterClick(View view) {
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        startActivity(registerActivity);
    }

    @Override
    public LoginViewModel getLoginViewModel() {
        String email = actvEmail.getText().toString();
        String password = etPassword.getText().toString();

        return new LoginViewModel(email, password);
    }

    @Override
    public void goToApp() {
        openMainActivity();
    }

    private void openMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
    }

    @Override
    public void showLoginError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}

