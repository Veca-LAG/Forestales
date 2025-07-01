package com.example.forestales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.forestales.databinding.ActivityRegisterBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    DatabaseHelper databaseHelper;

    ArrayAdapter<String> adapterJob;
    ArrayList<String> opcionesJob = new ArrayList<>(Arrays.asList("Alumno", "Maestro", "Otros"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        // Spinners

        Spinner spinnerJob = findViewById(R.id.registerJob);

        adapterJob = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesJob);
        spinnerJob.setAdapter(adapterJob);

        spinnerJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedJob = parent.getItemAtPosition(position).toString();
                if (selectedJob.equals("Otros")) {
                    binding.otherJob.setVisibility(View.VISIBLE);
                } else {
                    binding.otherJob.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });


        binding.RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.registerEmail.getText().toString();
                String password = binding.registerPassword.getText().toString();
                String confirmPassword = binding.registerConfirm.getText().toString();
                String selectedJob = binding.registerJob.getSelectedItem().toString();
                String finalJob;

                // Si es "Otros", usa el texto del EditText
                if (selectedJob.equals("Otros")) {
                    String otherJob = binding.otherJob.getText().toString().trim();
                    if (otherJob.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Por favor especifica tu trabajo", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Verifica si ya existe
                    boolean exists = false;
                    for (String item : opcionesJob) {
                        if (item.equalsIgnoreCase(otherJob)) {
                            exists = true;
                            break;
                        }
                    }

                    // Si no existe, agrégalo
                    if (!exists) {
                        opcionesJob.add(opcionesJob.size() - 1, otherJob); // Insertar antes de "Otros"
                        adapterJob.notifyDataSetChanged();
                        Toast.makeText(RegisterActivity.this, "Nuevo trabajo agregado al spinner", Toast.LENGTH_SHORT).show();
                    }

                    finalJob = otherJob;

                } else {
                    finalJob = selectedJob;
                }

                if(email.equals("")||password.equals("")||confirmPassword.equals("")||finalJob.equals(""))
                    Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);
                        if(checkUserEmail == false){
                            Boolean insert = databaseHelper.insertUser(email, password, finalJob);
                            if(insert == true){
                                Toast.makeText(RegisterActivity.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Registro no exitoso!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "El usuario ya existe! Porfavor de iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Contraseña invalida!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}