package com.example.fernandez_currency_converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fernandez_currency_converter.Retrofit.RetrofitBuilder;
import com.example.fernandez_currency_converter.Retrofit.RetrofitInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button button_convert;
    EditText text_base_currency,text_total;
    Spinner spin_ihave,spin_iwant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_convert = (Button) findViewById(R.id.btn_convert);
        text_base_currency = (EditText) findViewById(R.id.txt_base_currency);
        text_total = (EditText) findViewById(R.id.txt_total);
        spin_ihave = (Spinner) findViewById(R.id.spn_ihave);
        spin_iwant = (Spinner) findViewById(R.id.spn_iwant);

        String [] dropDownList = {"USD","PHP","EUR","KWD","JPY","KRY","CNY","JOD","AED"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,dropDownList);
        spin_ihave.setAdapter(adapter);
        spin_iwant.setAdapter(adapter);

        button_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);

                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(spin_ihave.getSelectedItem().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("response",String.valueOf(response.body()));
                        Toast.makeText(MainActivity.this, "Computed", Toast.LENGTH_SHORT).show();
                        JsonObject res = response.body();
                        JsonObject rates = res.getAsJsonObject("conversion_rates");
                        Double currency = Double.valueOf(text_base_currency.getText().toString());
                        Double multiplier = Double.valueOf(rates.get(spin_iwant.getSelectedItem().toString()).toString());
                        Double result = currency * multiplier;
                        text_total.setText(String.valueOf(result));
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });
    }
}