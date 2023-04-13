package com.cookos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final String LOGIN = "admin@gmail.com";
    private static final String PASSWORD = "admin";
    private static final int RC_SIGN_IN = 0;

    private HashMap<String, byte[]> accounts;

    private EditText emailField;
    private EditText passwordField;
    private TextView loginMessage;

    private SignInButton googleButton;
    private GoogleSignInClient googleSignInClient;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginMessage = findViewById(R.id.loginMessage);

        initLoginButton();
        initRegisterButton();

        var extra = getIntent().getSerializableExtra(getString(R.string.accountsMap));

        if (extra == null) {
            accounts = new HashMap<>();
            accounts.put(LOGIN, HashPassword.getHash(PASSWORD));
        } else {
            accounts = (HashMap<String, byte[]>) extra;
        }

        googleButton = findViewById(R.id.googleButton);

        var gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleButton.setOnClickListener(v -> {
            if (v.getId() == R.id.googleButton)
                googleSignIn();
        });
    }

    private void googleSignIn() {
        var signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
            startCalculator();

        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void initRegisterButton() {
        Button b = findViewById(R.id.registerButton);
        b.setOnClickListener(v -> {
            var intent = new Intent(this, RegisterActivity.class);
            intent.putExtra(getString(R.string.accountsMap), accounts);
            startActivity(intent);
        });
    }

    private void initLoginButton() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            var correctPassword = accounts.get(emailField.getText().toString());

            if (correctPassword == null) {
                loginMessage.setText(R.string.wrongEmail);
                return;
            }

            var enteredPassword = HashPassword.getHash(passwordField.getText().toString());

            if (!Arrays.equals(correctPassword, enteredPassword)) {
                loginMessage.setText(R.string.wrongPassword);
                return;
            }

            startCalculator();
        });
    }

    private void startCalculator() {
        var intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}