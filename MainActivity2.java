package com.example.hpp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    private Button btnEnglish;
    private Button btnGreek;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);


            btnEnglish = findViewById(R.id.btnEnglish);
            btnGreek = findViewById(R.id.btnGreek);

            btnEnglish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLanguage("en");
                    startActivity(new Intent(MainActivity2.this, MainActivity.class));

                }
            });

            btnGreek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLanguage("el");
                    startActivity(new Intent(MainActivity2.this, MainActivity.class));
                }
            });


    }
    public void setLanguage(String languageCode) {
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(languageCode);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}