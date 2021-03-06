package cs48.project.com.parl.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.Activity;

import cs48.project.com.parl.R;
import cs48.project.com.parl.core.login.LoginContract;
import cs48.project.com.parl.core.login.LoginPresenter;
import cs48.project.com.parl.ui.activities.ConvoListingActivity;
import cs48.project.com.parl.ui.activities.LoginActivity;
import cs48.project.com.parl.ui.activities.RegisterActivity;

/**
 https://sourcey.com/beautiful-android-login-and-signup-screens-with-material-design/
 */

public class LoginFragment extends Fragment implements View.OnClickListener, LoginContract.View {
    private LoginPresenter mLoginPresenter;
    private EditText mETxtEmail, mETxtPassword;
    private Button mBtnLogin, mBtnRegister;
    private ProgressDialog mProgressDialog;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mETxtEmail = (EditText) view.findViewById(R.id.edit_text_email_id);
        mETxtPassword = (EditText) view.findViewById(R.id.edit_text_password);
        mBtnLogin = (Button) view.findViewById(R.id.button_login);
        mBtnRegister = (Button) view.findViewById(R.id.button_register);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }


    private void init() {
        mLoginPresenter = new LoginPresenter(this);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.button_login:
                onLogin(view);
                break;
            case R.id.button_register:
                onRegister(view);
                break;
        }
    }

    private void onLogin(View view) {
        String emailId = mETxtEmail.getText().toString();

        String password = mETxtPassword.getText().toString();

        // Super shitty fix but it works. Ideally would want to
        // blank out the login button until both fields are filled
        // but we're leaving it like this for now...

        if (!emailId.contains(".") || !emailId.contains("@")) {
            Toast.makeText(getActivity(), "Please enter a valid e-mail", Toast.LENGTH_LONG).show();
        }

        else if (password.equals("")) {
            Toast.makeText(getActivity(), "Please enter a password", Toast.LENGTH_LONG).show();
        }

        else {
            mLoginPresenter.login(getActivity(), emailId, password);
            mProgressDialog.show();
        }
    }

    private void onRegister(View view) {
        RegisterActivity.startActivity(getActivity());
    }

    @Override
    public void onLoginSuccess(String message) {
        mProgressDialog.dismiss();
//        Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_SHORT).show();
        ConvoListingActivity.startActivity(getActivity(),
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onLoginFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), "Error: Incorrect email or password", Toast.LENGTH_SHORT).show();
    }
}