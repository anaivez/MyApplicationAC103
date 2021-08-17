package com.gdu.myapplicationac103;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Description:
 * Created by Czm on 2021/7/28 13:58.
 */
public class YZ extends AppCompatActivity {

    public static int PORT = 8190;

    EditText et;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yz);
        et = findViewById(R.id.et);
        btn = findViewById(R.id.btnSave);
        btn.setText("" + PORT);
    }

    public void save(View view) {
        if (et.length() == 4) {
            PORT = Integer.valueOf(et.getText().toString());
            btn.setText(et.getText().toString());
            et.setText("");
        } else {
            Toast.makeText(YZ.this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick1(View view) {
        startActivity(new Intent(YZ.this, WiFiActivity.class));
    }

    public void onClick2(View view) {
        startActivity(new Intent(YZ.this, WiFiEtActivity.class));

    }
}
