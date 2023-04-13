package com.cookos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String LOGIN = "admin@gmail.com";
    private static final String PASSWORD = "admin";

    private HashMap<String, byte[]> accounts;
    public HashMap<String, byte[]> getAccounts() {
        return accounts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {

            accounts = new HashMap<>();
            accounts.put(LOGIN, HashPassword.getHash(PASSWORD));

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, LoginFragment.class, null)
                    .commit();
        }
    }

    public void replaceFragments(Class<? extends Fragment> fragmentClass) {

        // Insert the fragment by replacing any existing fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragmentClass, null)
                .addToBackStack(null)
                .commit();
    }
}