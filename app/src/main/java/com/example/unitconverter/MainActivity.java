package com.example.unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner fromUnit, toUnit;
    private Button swapButton, clearButton;
    private TextView resultText;

    private final String[] units = {"Feet", "Inches", "Centimeters", "Meters", "Yards", "Millimeters", "Kilometers", "Miles"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        swapButton = findViewById(R.id.swapButton);
        clearButton = findViewById(R.id.clearButton);
        resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        // Listener for real-time conversion
        inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                convertUnits();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Listener for Spinner changes
        fromUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertUnits();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        toUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertUnits();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Swap button functionality
        swapButton.setOnClickListener(view -> {
            int fromPosition = fromUnit.getSelectedItemPosition();
            int toPosition = toUnit.getSelectedItemPosition();
            fromUnit.setSelection(toPosition);
            toUnit.setSelection(fromPosition);
        });

        // Clear button functionality
        clearButton.setOnClickListener(view -> {
            inputValue.setText("");
            resultText.setText("Result: ");
        });
    }

    private void convertUnits() {
        if (inputValue.getText().toString().isEmpty()) {
            resultText.setText("Result: ");
            return;
        }

        String from = fromUnit.getSelectedItem().toString();
        String to = toUnit.getSelectedItem().toString();
        double input = Double.parseDouble(inputValue.getText().toString());
        double result = convert(input, from, to);
        resultText.setText(String.format("Result: %.4f %s", result, to));
    }

    private double convert(double value, String from, String to) {
        double meters = 0;

        // Convert input to meters
        switch (from) {
            case "Feet": meters = value * 0.3048; break;
            case "Inches": meters = value * 0.0254; break;
            case "Centimeters": meters = value * 0.01; break;
            case "Meters": meters = value; break;
            case "Yards": meters = value * 0.9144; break;
            case "Millimeters": meters = value * 0.001; break;
            case "Kilometers": meters = value * 1000; break;
            case "Miles": meters = value * 1609.34; break;
        }

        // Convert meters to target unit
        switch (to) {
            case "Feet": return meters / 0.3048;
            case "Inches": return meters / 0.0254;
            case "Centimeters": return meters / 0.01;
            case "Meters": return meters;
            case "Yards": return meters / 0.9144;
            case "Millimeters": return meters / 0.001;
            case "Kilometers": return meters / 1000;
            case "Miles": return meters / 1609.34;
        }
        return 0;
    }
}
