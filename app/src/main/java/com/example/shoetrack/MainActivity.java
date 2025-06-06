package com.example.shoetrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Esperar 2.5 segundos
        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, MainDashboardActivity.class));
            finish(); // Cierra splash
        }, 2500);
    }


//    public void Navegar(View view) {
//        Intent intent = new Intent(this, MainDashboardActivity.class);
//        startActivity(intent);
//    }

}