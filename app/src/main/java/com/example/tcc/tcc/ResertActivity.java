package com.example.tcc.tcc;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ResertActivity extends Activity {
    private Button voltar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resert);

        voltar_login = (Button) findViewById(R.id.voltar_login);
        voltar_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResertActivity.this, LoginActivity.class));
            }
        });
    }
}
