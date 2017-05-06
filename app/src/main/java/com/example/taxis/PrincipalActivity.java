package com.example.taxis;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PrincipalActivity extends Activity {

    private ImageButton ibtnprincipal;
    private ImageButton ibtninformacion;
    private ImageButton ibtnbusqueda;
    private ImageButton ibtgrace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ibtnprincipal =(ImageButton)  findViewById(R.id.ibtnprincipal);
        ibtninformacion =(ImageButton)  findViewById(R.id.ibtninformacion);
        ibtnbusqueda =(ImageButton)  findViewById(R.id.ibtnbusqueda);
        ibtgrace = (ImageButton) findViewById(R.id.ibtngrace);

        ibtnprincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            //para cambiar de pantalla
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });

        ibtninformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            //para cambiar de pantalla
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ZahoriActivity.class);
                startActivity(i);

            }
        });
        ibtnbusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            //para cambiar de pantalla
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),BusquedaActivity.class);
                startActivity(i);

            }
        });

        ibtgrace.setOnClickListener(new View.OnClickListener() {
            @Override
            //para cambiar de pantalla
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),GraceActivity.class);
                startActivity(i);

            }
        });

    }



}
