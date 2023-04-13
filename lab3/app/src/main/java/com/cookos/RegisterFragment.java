package com.cookos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


public class RegisterFragment extends Fragment {

    private EditText registerEmailField;
    private EditText registerPasswordField;
    private EditText repeatPasswordField;
    private Button registerButton;

    private MainActivity activity;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        registerEmailField = view.findViewById(R.id.registerEmailField);
        registerPasswordField = view.findViewById(R.id.registerPasswordField);
        repeatPasswordField = view.findViewById(R.id.repeatPasswordField);
        registerButton = view.findViewById(R.id.registerButton);

        initRegisterButton();
    }

    private void initRegisterButton() {

        registerButton.setOnClickListener(v -> {
            var email = registerEmailField.getText().toString();

            if (!EmailValidator.validate(email)) {
                registerError(R.string.emailNotValid);
                return;
            }

            if (activity.getAccounts().containsKey(email)) {
                registerError(R.string.usedEmail);
                return;
            }

            var password = registerPasswordField.getText().toString();
            var repeatPassword = repeatPasswordField.getText().toString();

            if (!PasswordValidator.validate(password)) {
                registerError(R.string.passwordNotValid);
                return;
            }

            if (!password.equals(repeatPassword)) {
                registerError(R.string.differentPasswords);
                return;
            }

            activity.getAccounts().put(email, HashPassword.getHash(password));
            registerSuccess();

            ((MainActivity)getActivity()).replaceFragments(LoginFragment.class);
        });
    }

    private void registerError(int messageId) {
        Toast.makeText(getActivity(), getString(messageId), Toast.LENGTH_SHORT).show();
    }

    private void registerSuccess() {
        Toast.makeText(getActivity(), getString(R.string.registered), Toast.LENGTH_SHORT).show();
    }
}