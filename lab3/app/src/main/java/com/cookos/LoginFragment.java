package com.cookos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 0;


    private EditText emailField;
    private EditText passwordField;
    private TextView loginMessage;
    private Button loginButton;
    private Button registerButton;

    private SignInButton googleButton;
    private GoogleSignInClient googleSignInClient;

    private MainActivity activity;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
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
            Toast.makeText(getActivity(), "Signed in", Toast.LENGTH_SHORT).show();
            startCalculator();

        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void initRegisterButton() {
        registerButton.setOnClickListener(v -> {
            activity.replaceFragments(RegisterFragment.class);
        });
    }

    private void initLoginButton() {
        loginButton.setOnClickListener(v -> {
            var correctPassword = activity.getAccounts().get(emailField.getText().toString());

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
        activity.replaceFragments(GalleryFragment.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        emailField = view.findViewById(R.id.emailField);
        passwordField = view.findViewById(R.id.passwordField);
        loginMessage = view.findViewById(R.id.loginMessage);
        registerButton = view.findViewById(R.id.registerButton);
        loginButton = view.findViewById(R.id.loginButton);
        googleButton = view.findViewById(R.id.googleButton);


        initLoginButton();
        initRegisterButton();

        var gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(activity, gso);

        googleButton.setOnClickListener(v -> {
            if (v.getId() == R.id.googleButton)
                googleSignIn();
        });
    }
}