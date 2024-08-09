package com.example.goangola;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;

public class CadastroActivity extends AppCompatActivity {
    private EditText nascimento;
    private AutoCompleteTextView select_geneero;
    private AutoCompleteTextView select_civil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciaComponentes();
    }

    private void iniciaComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo_janela);
        text_titulo.setText("Cadastrar Empregador");

        ImageButton ib_voltar = findViewById(R.id.ib_voltar);
        ib_voltar.setOnClickListener(view -> finish());

        Button btn_continuar = findViewById(R.id.btn_continuar);
        btn_continuar.setOnClickListener(view -> {
            startActivity(new Intent(this,FuncionarioCadastro.class));
        });

        nascimento = findViewById(R.id.nascimento);
        select_geneero =  findViewById(R.id.select_geneero);
        select_civil =  findViewById(R.id.select_civil);

        setDropdownOptions(select_geneero,R.array.genero);
        setDropdownOptions(select_civil,R.array.civil);
        setDropdownOptions(findViewById(R.id.select_provincia),R.array.provincias);


        showDatePickerNascimento(nascimento);
        showDatePickerNascimento(findViewById(R.id.emissao_bi));
        showDatePickerNascimento(findViewById(R.id.validade_bi));

    }

    private void showDatePickerNascimento(EditText alvo) {

        alvo.setKeyListener(null);

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Selecionar");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        alvo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                }
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPositiveButtonClick(Object selection) {
                alvo.setText(materialDatePicker.getHeaderText());
            }
        });
    }

    private void setDropdownOptions(AutoCompleteTextView alvo,int options){
        String[] optionsstr = getResources().getStringArray(options);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.genero_item,optionsstr);
        alvo.setAdapter(adapter);
    }

}