package com.example.unitconverter;

import android.os.Bundle;
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
    private Button convertButton;
    private TextView resultText;

    private final String[] units = {"Feet", "Inches", "Centimeters", "Meters", "Yards"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Set up Spinner Adapters
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        convertButton.setOnClickListener(view -> convertUnits());
    }

    private void convertUnits() {
        String from = fromUnit.getSelectedItem().toString();
        String to = toUnit.getSelectedItem().toString();

        if (inputValue.getText().toString().isEmpty()) {
            resultText.setText("Please enter a value.");
            return;
        }

        double input = Double.parseDouble(inputValue.getText().toString());
        double result = convert(input, from, to);
        resultText.setText(String.format("Result: %.4f %s", result, to));
    }

    private double convert(double value, String from, String to) {
        double meters = 0;

        // Convert input value to meters first
        switch (from) {
            case "Feet": meters = value * 0.3048; break;
            case "Inches": meters = value * 0.0254; break;
            case "Centimeters": meters = value * 0.01; break;
            case "Meters": meters = value; break;
            case "Yards": meters = value * 0.9144; break;
        }

        // Convert meters to target unit
        switch (to) {
            case "Feet": return meters / 0.3048;
            case "Inches": return meters / 0.0254;
            case "Centimeters": return meters / 0.01;
            case "Meters": return meters;
            case "Yards": return meters / 0.9144;
        }
        return 0;
    }
}
