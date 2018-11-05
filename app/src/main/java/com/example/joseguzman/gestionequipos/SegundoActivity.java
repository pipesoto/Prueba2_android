package com.example.joseguzman.gestionequipos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.joseguzman.gestionequipos.Data.BaseDatos;
import com.example.joseguzman.gestionequipos.Modelo.Equipo;
import com.example.joseguzman.gestionequipos.Modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SegundoActivity extends AppCompatActivity {
    TextView tvDptoUsuario;
    TextView tvNombreUsuario;
    TextView tvTotalMonto;
    ListView lvEquiposCargo;
    Button btnCargarEquipo;
    Button btnDescargarEquipo;
    AutoCompleteTextView autoTvNroSerie;
    TextView tvDescEquipo;
    TextView tvValorEquipo;
    Button btnFinalizar;

    List<Equipo> equiposAsig;
    List<String> seriesDisp = new ArrayList<>();

    ArrayAdapter adapter;
    ArrayAdapter adapterAutoComplete;

    AlertDialog.Builder builder;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);

        tvDptoUsuario = (TextView) findViewById(R.id.tvDptoUsuario);
        tvNombreUsuario = (TextView) findViewById(R.id.tvNombreUsuario);
        tvTotalMonto = (TextView) findViewById(R.id.tvTotalMonto);
        lvEquiposCargo = (ListView) findViewById(R.id.lvEquiposCargo);
        btnCargarEquipo = (Button) findViewById(R.id.btnCargarEquipo);
        btnDescargarEquipo = (Button) findViewById(R.id.btnDescargarEquipo);
        autoTvNroSerie = (AutoCompleteTextView) findViewById(R.id.autoTvNroSerie);
        tvDescEquipo = (TextView) findViewById(R.id.tvDescEquipo);
        tvValorEquipo = (TextView) findViewById(R.id.tvValorEquipo);
        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);

        //TOMANDO LOS DATOS DEL USUARIO QUE HIZO LOGIN
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        tvNombreUsuario.setText(usuario.getNombre() + " " + usuario.getApellido());
        tvDptoUsuario.setText(usuario.getDepartamento());

        actualizarVistas(usuario);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(getResources().getString(R.string.alertDescargaTitulo))
                .setMessage(getResources().getString(R.string.alertDescargaMensaje))
                .setIcon(android.R.drawable.ic_dialog_alert);

        autoTvNroSerie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipo e = BaseDatos.buscarEquipo(autoTvNroSerie.getText().toString());
                tvDescEquipo.setText(e.getDescripción());
                tvValorEquipo.setText(e.getValor()+"");
            }
        });

        btnCargarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Equipo e = BaseDatos.buscarEquipo(autoTvNroSerie.getText().toString());

                if (e != null) { //Si la variable e no es NULL es porque el Equipo sí existe y se procede.
                    boolean disponible = false;
                    //Se busca número de Serie proporcionado entre los equipos disponibles
                    for (Equipo equipo : BaseDatos.equiposDisponibles()
                            ) {
                        if (equipo.getSerie().equals(e.getSerie())) {
                            disponible = true;
                        }
                    }
                    if (disponible) {
                        BaseDatos.cargarEquipoAlUsuario(usuario.getUsuario(), e.getSerie());
                        actualizarVistas(usuario);
                        autoTvNroSerie.setText("");
                        tvDescEquipo.setText("");
                        tvValorEquipo.setText("");
                    } else {
                        autoTvNroSerie.setError(getResources().getString(R.string.autoTvSerieErrorYaAsignado));
                    }
                } else { //En caso de ser NULL significa que el Equipo no existe, y se le asigna el error correpondiente!
                    autoTvNroSerie.setError(getResources().getString(R.string.autoTvSerieErrorNoExiste));
                }
            }
        });

        lvEquiposCargo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
                final Equipo e = (Equipo) lvEquiposCargo.getItemAtPosition(position);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Eliminar
                        BaseDatos.descargarEquipoAlUsuario(usuario.getUsuario(),e.getSerie());
                       actualizarVistas(usuario);
                    }
                })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Nada
                            }
                        }).show();
                return false;
            }
        });

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void actualizarVistas(Usuario usuario){
        //CREANDO ADAPTADOR PARA MOSTRAR LISTA DE EQUIPOS ASIGNADOS A UN USUARIO
        lvEquiposCargo.setAdapter(null);
        equiposAsig = BaseDatos.equiposPorUsuario(usuario.getUsuario());
        adapter = new ArrayAdapter<Equipo>(this, android.R.layout.simple_list_item_1,equiposAsig);
        lvEquiposCargo.setAdapter(adapter);

        //ACTUALIZAR EL MONTO TOTAL DE EQUIPOS CARGADOS A USUARIO
        double total = 0;
        for (Equipo equipo : equiposAsig
                ) {
            total = total + equipo.getValor();
        }
        tvTotalMonto.setText("$ " + total);

        //ADAPTANDO LOS EQUIPOS DISPONIBLES PARA QUE SOLO MUESTRE LOS NUMEROS DE SERIE EN EL AUTOCOMPLETETEXTVIEW
        seriesDisp.clear();
        for (Equipo e : BaseDatos.equiposDisponibles()
                ) {
            seriesDisp.add(e.getSerie());
        }
        adapterAutoComplete = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, seriesDisp);
        autoTvNroSerie.setAdapter(adapterAutoComplete);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
