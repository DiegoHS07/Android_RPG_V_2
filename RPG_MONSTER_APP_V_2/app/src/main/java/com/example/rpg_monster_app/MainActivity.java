package com.example.rpg_monster_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables
    private EditText inputNombre;
    private Button btnEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNombre = (EditText) findViewById(R.id.txtNombreUsu);
        btnEmpezar = (Button) findViewById(R.id.btnEmpezar);

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostramos el nombre y vida del usuario
                String nombreUsuario = null;

                nombreUsuario = inputNombre.getText().toString();

                Intent juegoPrincipal = new Intent(MainActivity.this, JuegoPrincipal.class);
                juegoPrincipal.putExtra("nombreUsu",nombreUsuario);
                startActivity(juegoPrincipal);
            }
        });

    }
}