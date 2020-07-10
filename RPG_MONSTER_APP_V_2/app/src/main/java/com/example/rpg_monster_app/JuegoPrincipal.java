package com.example.rpg_monster_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class JuegoPrincipal extends AppCompatActivity {
    //Declaro las variables
    private  TextView lblTitulo;
    private TextView lblNombreUsu;
    private TextView lblVidaUsu;
    private TextView lblNombreMons;
    private TextView lblVidaMons;
    private ProgressBar prbVidaUsu;
    private ProgressBar prbVidaMons;
    private Button btnAtacar;
    private Button btnCurar;
    private Button btnRendirse;
    private Button btnReiniciar;

    private Boolean jugadorGana = false;
    private Boolean monstruoGana = false;
    private Boolean jugadorRendido = false;
    private Boolean turnoJugador = true;

    private RecyclerView historialMovimientos;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Datos> listDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_principal);

        historialMovimientos = (RecyclerView) findViewById(R.id.revHistorialMovi);
        historialMovimientos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        historialMovimientos.setLayoutManager(layoutManager);

        listDatos = new ArrayList<Datos>();


        lblTitulo = (TextView) findViewById(R.id.txtPrueba);

        /***************************** USUARIO **************************************/

        String nombreUsuario = null;
        String vidaUsu = null;

        nombreUsuario = recibirDatosUsu();

        //Muestro el nombre y la vida del usuario
        lblNombreUsu = (TextView) findViewById(R.id.lblNombreUsu);
        prbVidaUsu = (ProgressBar) findViewById(R.id.prbVidaUsu);
        lblVidaUsu = (TextView) findViewById(R.id.lblVidaUsu);

        vidaUsu = String.valueOf(prbVidaUsu.getProgress());

        lblNombreUsu.setText(nombreUsuario);
        lblVidaUsu.setText(vidaUsu + "%");


        /***************************** MONSTRUO **************************************/

        String nombreMonstruo = null;
        String vidaMonstruo = null;

        int numeroMonstruo = (int) (Math.random() * 3) + 1;

        switch (numeroMonstruo){
            case 1:
                nombreMonstruo = "Fordty";
                break;
            case 2:
                nombreMonstruo = "Voldy";
                break;
            case 3:
                nombreMonstruo = "Sammy";
                break;
        }

        //Muestro el nombre y la vida del monstruo
        lblNombreMons = (TextView) findViewById(R.id.lblNombreMons);
        prbVidaMons = (ProgressBar) findViewById(R.id.prbVidaMons);
        lblVidaMons = (TextView) findViewById(R.id.lblVidaMons);

        vidaMonstruo = String.valueOf(prbVidaMons.getProgress());

        lblNombreMons.setText(nombreMonstruo);
        lblVidaMons.setText(vidaMonstruo + "%");


        /***************************** EMPIEZO EL JUEGO *******************************/

        btnAtacar = (Button) findViewById(R.id.btnAtacar);
        btnCurar = (Button) findViewById(R.id.btnCurar);
        btnRendirse = (Button) findViewById(R.id.btnRendirse);
        btnReiniciar = (Button) findViewById(R.id.btnReiniciar);

        btnAtacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarJuego(1);
            }
        });

        btnCurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarJuego(2);
            }
        });

        btnRendirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarJuego(0);
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent juegoPrincipal = new Intent(JuegoPrincipal.this, MainActivity.class);
                startActivity(juegoPrincipal);
            }
        });

    }

    //Funcion que me captura el nombre del usuario
    public String recibirDatosUsu(){
        Bundle extras = getIntent().getExtras();
        return extras.getString("nombreUsu");
    }

    //Funcion que permite analizar el estado del juego
    public void actualizarJuego(int accionJugador){
        switch (accionJugador) {
            case 1:
                jugadorAtaca();
                break;
            case 2:
                jugadorCura();
                break;
            default:
                rendirse();
                break;
        }
        AdapterDatos adapter =  new AdapterDatos(listDatos);
        historialMovimientos.setAdapter(adapter);
    }



    public void jugadorAtaca(){
        int dañoJugador = generaNumeroAleatorio(5,15);
        int vidaMonstruo = prbVidaMons.getProgress();
        int vidaNuevaMons = vidaMonstruo - dañoJugador;
        prbVidaMons.setProgress(vidaNuevaMons);
        String lblVidaMonstruo = String.valueOf(prbVidaMons.getProgress());
        lblVidaMons.setText(lblVidaMonstruo + "%");
        listDatos.add(new Datos("Ataca", lblNombreUsu.getText().toString() + " golpeó al mounstro por " + dañoJugador));
        if(vidaNuevaMons > 0){
            accionMonstruo();
        }
        if(vidaNuevaMons <= 0){
            jugadorGana = true;
            btnAtacar.setVisibility(View.INVISIBLE);
            btnCurar.setVisibility(View.INVISIBLE);
            btnRendirse.setVisibility(View.INVISIBLE);
            btnReiniciar.setVisibility(View.VISIBLE);
            lblTitulo.setText(lblNombreUsu.getText().toString() + " Gana");
        }

    }

    public void jugadorCura(){
        int vidaJugador = prbVidaUsu.getProgress();
        int curaJugador = generaNumeroAleatorio(10,25);
        int vidaNuevaJugador = vidaJugador + curaJugador;
        prbVidaUsu.setProgress(vidaNuevaJugador);
        String lblVidaJugador = String.valueOf(prbVidaUsu.getProgress());
        lblVidaUsu.setText(lblVidaJugador + "%");
        listDatos.add(new Datos("Cura", lblNombreUsu.getText().toString() + " se curo por " + curaJugador));
        accionMonstruo();
    }

    public void rendirse(){
        monstruoGana = true;
        listDatos.add(new Datos ("Rendir",lblNombreUsu.getText().toString() + " se rinde"));
        btnAtacar.setVisibility(View.INVISIBLE);
        btnCurar.setVisibility(View.INVISIBLE);
        btnRendirse.setVisibility(View.INVISIBLE);
        btnReiniciar.setVisibility(View.VISIBLE);

        lblTitulo.setText(lblNombreMons.getText().toString() + " Gana");
    }

    public void accionMonstruo(){
        int dañoMonstruo = generaNumeroAleatorio(5,20);
        int vidaJugador = prbVidaUsu.getProgress();
        int vidaNuevaJugador = vidaJugador - dañoMonstruo;
        prbVidaUsu.setProgress(vidaNuevaJugador);
        String lblVidaJugador = String.valueOf(prbVidaUsu.getProgress());
        lblVidaUsu.setText(lblVidaJugador + "%");
        listDatos.add(new Datos ("Monstruo",lblNombreMons.getText().toString() + " golpeó por " + dañoMonstruo));
        if(vidaNuevaJugador <= 0){
            monstruoGana = true;
            btnAtacar.setVisibility(View.INVISIBLE);
            btnCurar.setVisibility(View.INVISIBLE);
            btnRendirse.setVisibility(View.INVISIBLE);
            btnReiniciar.setVisibility(View.VISIBLE);

            lblTitulo.setText(lblNombreMons.getText().toString() + " Gana");
        }

    }

    public int generaNumeroAleatorio(int minimo,int maximo){
        int num=(int)Math.floor(Math.random()*(maximo-minimo+1)+(minimo));
        return num;
    }

}