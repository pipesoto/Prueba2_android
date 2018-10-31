package com.example.joseguzman.gestionequipos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginUsuario;
    EditText etLoginClave;
    Button btnLoginIngresar;
    Button btnLoginCancelar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //REFERENCIAS
        etLoginUsuario = (EditText) findViewById(R.id.etLoginUsuario);
        etLoginClave = (EditText) findViewById(R.id.etLoginClave);
        btnLoginIngresar = (Button) findViewById(R.id.btnLoginIngresar);
        btnLoginCancelar = (Button) findViewById(R.id.btnLoginCancelar);




        btnLoginCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
