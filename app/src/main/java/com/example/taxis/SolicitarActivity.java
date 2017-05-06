package com.example.taxis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SolicitarActivity extends Activity {

    private EditText etlat;
    private EditText etlong;
    private EditText etcobertura;
    private EditText etnombre;
    private EditText etcorreo;
    private Button btnSolicitar;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar);

        etlat = (EditText) findViewById(R.id.etlatitud);
        etlong = (EditText) findViewById(R.id.etlongitud);
        etcobertura = (EditText) findViewById(R.id.etcobertura);
        etnombre = (EditText) findViewById(R.id.etnombre);
        etcorreo = (EditText) findViewById(R.id.etcorreo);

        Bundle bundle = getIntent().getExtras();

        Double latitud = bundle.getDouble("lat");
        Double longitud = bundle.getDouble("long");

        String lat=latitud.toString();
        String longi= longitud.toString();
        etlat.setText(lat);
        etlong.setText(longi);



        btnSolicitar = (Button) findViewById(R.id.btnsolicitar);
        btnCancelar = (Button) findViewById(R.id.btncancelar);

        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etlat.getText().toString().equals("") || etlong.getText().toString().equals(" ") || etcobertura.getText().toString().equals("") || etnombre.getText().toString().equals("") || etcorreo.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Campos Vacios",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Solicitud Enviada",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Se cancelo la solicitud ",Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}
