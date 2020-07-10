package com.example.rpg_monster_app;

public class Datos {
    private String TAG;
    private String valor;
    public Datos(String _TAG, String _valor) {
        TAG = _TAG;
        valor = _valor;
    }

    public String getTAG() {
        return TAG;
    }

    public String getValor() {
        return valor;
    }
}
