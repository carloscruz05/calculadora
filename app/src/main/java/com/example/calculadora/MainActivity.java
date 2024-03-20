package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView processTextView;
    private TextView resultTextView;
    private StringBuilder process = new StringBuilder();
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processTextView = findViewById(R.id.processTextView);
        resultTextView = findViewById(R.id.resultTextView);

        findViewById(R.id.button0).setOnClickListener(v -> appendNumber("0"));
        findViewById(R.id.button1).setOnClickListener(v -> appendNumber("1"));
        findViewById(R.id.button2).setOnClickListener(v -> appendNumber("2"));
        findViewById(R.id.button3).setOnClickListener(v -> appendNumber("3"));
        findViewById(R.id.button4).setOnClickListener(v -> appendNumber("4"));
        findViewById(R.id.button5).setOnClickListener(v -> appendNumber("5"));
        findViewById(R.id.button6).setOnClickListener(v -> appendNumber("6"));
        findViewById(R.id.button7).setOnClickListener(v -> appendNumber("7"));
        findViewById(R.id.button8).setOnClickListener(v -> appendNumber("8"));
        findViewById(R.id.button9).setOnClickListener(v -> appendNumber("9"));

        findViewById(R.id.buttonClear).setOnClickListener(v -> clear());
        findViewById(R.id.buttonDivide).setOnClickListener(v -> setOperator("/"));
        findViewById(R.id.buttonMultiply).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.buttonSubtract).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.buttonAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.buttonEquals).setOnClickListener(v -> calculate());
        findViewById(R.id.buttonDecimal).setOnClickListener(v -> {
            if (!process.toString().endsWith(".")) {
                appendNumber(".");
            }
        });
    }

    private void appendNumber(String number) {
        process.append(number);
        processTextView.setText(process.toString());
    }

    private void clear() {
        process.setLength(0);
        operator = "";
        processTextView.setText("");
        resultTextView.setText("");
    }

    private void setOperator(String op) {
        if (process.length() > 0) {
            char lastChar = process.charAt(process.length() - 1);
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {

                process.setCharAt(process.length() - 1, op.charAt(0));
            } else {
                process.append(op);
            }
            processTextView.setText(process.toString());
        }
    }

    private void calculate() {
        if (process.length() > 0) {
            try {
                String[] tokens = process.toString().split("(?<=[-+*/])|(?=[-+*/])");
                double result = Double.parseDouble(tokens[0]);
                for (int i = 1; i < tokens.length; i += 2) {
                    String operator = tokens[i];
                    double operand = Double.parseDouble(tokens[i + 1]);
                    switch (operator) {
                        case "+":
                            result += operand;
                            break;
                        case "-":
                            result -= operand;
                            break;
                        case "*":
                            result *= operand;
                            break;
                        case "/":
                            if (operand != 0) {
                                result /= operand;
                            } else {
                                resultTextView.setText("Error");
                                return;
                            }
                            break;
                    }
                }
                resultTextView.setText(String.valueOf(result));
            } catch (NumberFormatException e) {
                resultTextView.setText("Error");
            }
        }
    }
}

