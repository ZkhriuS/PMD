package com.cookos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.License;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView expressionField;
    private TextView answerField;
    private double memory = 0d;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onDestroy() {
        googleSignInClient.signOut();

        super.onDestroy();
    }

    private interface Function {
        double calculate(double arg);
    }

    static {
        License.iConfirmNonCommercialUse("cookos");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expressionField = findViewById(R.id.expression);
        answerField = findViewById(R.id.answer);

        initClearButton();
        initClearAllButton();
        initEqualsButton();

        initFuncButton(R.id.sin, Math::sin);
        initFuncButton(R.id.cos, Math::cos);

        initTextButton(R.id.zero,         "0");
        initTextButton(R.id.one,          "1");
        initTextButton(R.id.two,          "2");
        initTextButton(R.id.three,        "3");
        initTextButton(R.id.four,         "4");
        initTextButton(R.id.five,         "5");
        initTextButton(R.id.six,          "6");
        initTextButton(R.id.seven,        "7");
        initTextButton(R.id.eight,        "8");
        initTextButton(R.id.nine,         "9");
        initTextButton(R.id.plus,         "+");
        initTextButton(R.id.minus,        "-");
        initTextButton(R.id.multiply,     "*");
        initTextButton(R.id.divide,       "/");
        initTextButton(R.id.dot,          ".");
        initTextButton(R.id.leftBracket,  "(");
        initTextButton(R.id.rightBracket, ")");

        Button memoryClear = findViewById(R.id.memoryClear);
        memoryClear.setOnClickListener(v -> memory = 0d);

        Button memoryReturn = findViewById(R.id.memoryReturn);
        memoryReturn.setOnClickListener(v -> {
            var text = withoutTrailingZeroes(String.format(Locale.US, "%f", memory));

            expressionField.append(text);
            calculate();
        });

        Button memoryPlus = findViewById(R.id.memoryPlus);
        memoryPlus.setOnClickListener(v -> {
            var e = new Expression(answerField.getText().toString());

            memory += e.calculate();
        });

        Button memoryMinus = findViewById(R.id.memoryMinus);
        memoryMinus.setOnClickListener(v -> {
            var e = new Expression(answerField.getText().toString());

            memory -= e.calculate();
        });

        Button memorySave = findViewById(R.id.memorySave);
        memorySave.setOnClickListener(v -> {
            var e = new Expression(answerField.getText().toString());

            memory = e.calculate();
        });

        var gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initFuncButton(int id, Function function) {
        Button sin = findViewById(id);
        sin.setOnClickListener(v -> {
            var e = new Expression(expressionField.getText().toString());

            var answer = function.calculate(e.calculate());
            var text = String.format(Locale.US, "%f", answer);

            expressionField.setText(text);
            answerField.setText(text);
        });
    }

    private void initEqualsButton() {
        Button equals = findViewById(R.id.equals);
        equals.setOnClickListener(v -> {
            var e = new Expression(answerField.getText().toString());
            var answer = e.calculate();

            if (Double.isNaN(answer))
                return;

            var text = withoutTrailingZeroes(String.format(Locale.US, "%f", answer));

            answerField.setText("");
            expressionField.setText(text);
        });
    }

    private void initClearAllButton() {
        Button clearAll = findViewById(R.id.clearAll);
        clearAll.setOnClickListener(v -> {
            expressionField.setText("");
            answerField.setText("");
        });
    }

    private void initClearButton() {
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(v -> {

            var oldText = expressionField.getText();

            if (oldText.length() == 0)
                return;

            var newText = oldText.subSequence(0, oldText.length() - 1);

            expressionField.setText(newText);

            calculate();
        });
    }

    private void initTextButton(int id, String text) {
        Button button = findViewById(id);
        button.setOnClickListener(v -> {
            expressionField.append(text);
            calculate();
        });
    }

    private void calculate() {
        if (expressionField.getText().length() == 0) {
            answerField.setText("");
            return;
        }

        var e = new Expression(expressionField.getText().toString());
        var text = String.format(Locale.US, "%f", e.calculate());
        text = withoutTrailingZeroes(text);

        answerField.setText(text);
    }

    @NonNull
    private String withoutTrailingZeroes(@NonNull String text) {
        return text.contains(".") ? text.replaceAll("0*$", "").replaceAll("\\.$", "") : text;
    }


}