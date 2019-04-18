package com.example.practicasql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnInsert;
    Button btnSelect;
    Button btnDelete;
    Button btnUpdate;
    EditText noControl, nombre, aPaterno, aMaterno;
    boolean NO = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = (Button) findViewById(R.id.insert);
        btnDelete = (Button) findViewById(R.id.delete);
        btnSelect = (Button) findViewById(R.id.select);
        btnUpdate = (Button) findViewById(R.id.update);
        noControl = (EditText) findViewById(R.id.noControl);
        nombre = (EditText) findViewById(R.id.nombre);
        aPaterno = (EditText) findViewById(R.id.aPaterno);
        aMaterno = (EditText) findViewById(R.id.aMaterno);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper myHelper = new DataBaseHelper(MainActivity.this, "Alumnos", null, 1);

                SQLiteDatabase db = myHelper.getWritableDatabase();

                if (noControl.getText().length() == 0 || nombre.getText().length() == 0 || aPaterno.getText().length() == 0
                        || aMaterno.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "Favor de llenar los campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (db != null) {
                        db.execSQL("insert into Alumnos(no_control, nombre, aPaterno, aMaterno) " +
                                "values('" + noControl.getText().toString() + "','" + nombre.getText().toString() + "', '" + aPaterno.getText().toString() + "', '" + aMaterno.getText().toString() + "')");
                        Toast.makeText(MainActivity.this, "Registro guardado", Toast.LENGTH_SHORT).show();
                        db.close();
                        noControl.setText(null);
                        nombre.setText(null);
                        aPaterno.setText(null);
                        aMaterno.setText(null);
                    }
                }
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registros = "";
                DataBaseHelper myHelper = new DataBaseHelper(MainActivity.this, "Alumnos", null, 1);

                SQLiteDatabase db = myHelper.getWritableDatabase();
                if (db != null) {
                    Cursor cursor = db.rawQuery("select * from Alumnos", null);
                    if (cursor.moveToFirst()) {
                        do {
                            registros += cursor.getInt(cursor.getColumnIndex("no_control"));
                            registros += " " + cursor.getString(cursor.getColumnIndex("nombre"));
                            registros += " " + cursor.getString(cursor.getColumnIndex("aPaterno"));
                            registros += " " + cursor.getString(cursor.getColumnIndex("aMaterno"));
                            registros += "\n";
                        } while (cursor.moveToNext());
                    }
                    Toast.makeText(MainActivity.this, registros, Toast.LENGTH_SHORT).show();
                    db.close();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper myHelper = new DataBaseHelper(MainActivity.this, "Alumnos", null, 1);
                SQLiteDatabase db = myHelper.getWritableDatabase();

                if (COMP2())
                {
                    Toast.makeText(MainActivity.this, "Escriba un numero de control", Toast.LENGTH_SHORT).show();
                } else {
                    SELECT2();
                    if (db != null) {
                        if(NO == false){
                            db.execSQL("delete from Alumnos where no_control = '" + noControl.getText().toString() + "'");
                            Toast.makeText(MainActivity.this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                        }
                        else if(NO)
                        {
                            Toast.makeText(getApplicationContext(), "No hay registro con ese numero de control", Toast.LENGTH_SHORT).show();
                        }

                        db.close();
                        noControl.setText(null);
                    }
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper myHelper = new DataBaseHelper(MainActivity.this, "Alumnos", null, 1);
                SQLiteDatabase db = myHelper.getWritableDatabase();

                if (COMP2()) {

                    Toast.makeText(MainActivity.this, "Favor de llenar los campos a actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    SELECT2();
                    if (db != null) {
                        if(NO == false) {
                            db.execSQL("update Alumnos set nombre = '" + nombre.getText().toString() + "', " +
                                    "aPaterno  = '" + aPaterno.getText().toString() + "'," +
                                    "aMaterno = '" + aMaterno.getText().toString() + "'where no_control=" + noControl.getText().toString());
                            Toast.makeText(MainActivity.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                        }
                        else if(NO)
                        {
                            Toast.makeText(MainActivity.this, "No hay registro con ese numero de control", Toast.LENGTH_SHORT).show();
                        }
                        }
                        db.close();
                    noControl.setText(null);
                    nombre.setText(null);
                    aPaterno.setText(null);
                    aMaterno.setText(null);
                }

            }

        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public boolean COMP2() {
        boolean bool = false;

        if (noControl.getText().length() == 0) {
            //  Toast.makeText(getApplicationContext(), "FAVOR DE LLENAR TODS LOS CAMPOS", Toast.LENGTH_SHORT).show();
            bool = true;
        } else {
            bool = false;
        }
        return bool;

    }

    public boolean COMP() {
        boolean bool = false;

        if (noControl.getText().length() == 0 || nombre.equals("") || aPaterno.equals("")) {
            //  Toast.makeText(getApplicationContext(), "FAVOR DE LLENAR TODS LOS CAMPOS", Toast.LENGTH_SHORT).show();
            bool = true;
        } else {
            bool = false;
        }
        return bool;

    }

    public void SELECT2() {
        String registros = "";
        DataBaseHelper helper = new DataBaseHelper(MainActivity.this, "Alumnos", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM ALUMNOS WHERE no_control = " + noControl.getText().toString(), null);
            if (cursor.moveToFirst()) {
                do {
                    registros += cursor.getInt(cursor.getColumnIndex("no_control"));
                    registros += " ";
                    registros += cursor.getString(cursor.getColumnIndex("nombre"));
                    registros += " ";
                    registros += cursor.getString(cursor.getColumnIndex("aPaterno"));
                    registros += "\n";
                } while (cursor.moveToNext());
            }
            if (registros.equals("")) {
                // Toast.makeText(actividad2.this, "NO HAY REGISTROS", Toast.LENGTH_SHORT).show();
                NO = true;
            } else
                // Toast.makeText(actividad2.this, registros, Toast.LENGTH_SHORT).show();
                NO = false;

            db.close();

        }
    }
}