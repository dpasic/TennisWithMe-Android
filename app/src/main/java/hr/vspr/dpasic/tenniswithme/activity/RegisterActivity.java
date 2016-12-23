package hr.vspr.dpasic.tenniswithme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.vspr.dpasic.tenniswithme.R;
import hr.vspr.dpasic.tenniswithme.login_mvp.LoginPresenterImpl;
import hr.vspr.dpasic.tenniswithme.register_mvp.RegisterPresenterImpl;
import hr.vspr.dpasic.tenniswithme.register_mvp.RegisterView;
import hr.vspr.dpasic.tenniswithme.view_model.LoginViewModel;
import hr.vspr.dpasic.tenniswithme.view_model.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private RegisterPresenterImpl registerPresenter;

    @BindView(R.id.actv_email)
    AutoCompleteTextView actvEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        registerPresenter = new RegisterPresenterImpl(this);
    }

    public void registerClick(View view) {
        registerPresenter.doRegister();
    }

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
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    @Override
    public void showRegisterError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
