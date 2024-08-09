package com.example.goangola;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.goangola.helpers.MoneyTextWatcher;

import java.text.NumberFormat;
import java.util.Locale;

public class FuncionarioCadastro extends AppCompatActivity {

    private EditText ordenado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_funcionario_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        iniciaComponentes();
    }

    private void iniciaComponentes(){
        TextView text_titulo = findViewById(R.id.text_titulo_janela);
        text_titulo.setText("Cadastrar Empregado/a");

        ImageButton ib_voltar = findViewById(R.id.ib_voltar);
        ib_voltar.setOnClickListener(view -> finish());

        ordenado = findViewById(R.id.ordenado);
        AutoCompleteTextView select_profissao =  findViewById(R.id.select_profissao);

        setDropdownOptions(select_profissao,R.array.profissao);
        MoneyTextWatcher ordenadotextWatcher = new MoneyTextWatcher(ordenado);
        ordenado.addTextChangedListener(ordenadotextWatcher);
        TextView total_txt_view = findViewById(R.id.total_txt_view);
        EditText contri_empregador = findViewById(R.id.contri_empregador);
        EditText contri_empregado = findViewById(R.id.contri_empregado);


        ordenado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não necessário para a maioria dos casos
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Não necessário para a maioria dos casos
            }
            @Override
            public void afterTextChanged(Editable s) {
                // Chama a função sempre que o texto mudar
                total_txt_view.setText(ordenado.getText());
                double valor = ordenadotextWatcher.getIntegerFromFormattedText();

                contri_empregador.setText(RetornaPercentagem(valor,0.08));
                contri_empregado.setText(RetornaPercentagem(valor,0.03));
            }
        });

    }

    private void setDropdownOptions(AutoCompleteTextView alvo, int options){
        String[] optionsstr = getResources().getStringArray(options);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.genero_item,optionsstr);
        alvo.setAdapter(adapter);
    }

    private double getValueWithoutCurrency(String formattedValue) {
        String cleanString = formattedValue.replaceAll("[^\\d]", "");
        return Double.parseDouble(cleanString) / 100; // Converte centavos para reais
    }

    private String RetornaPercentagem(double valor,double percentagem){
        double number = valor / 100;
        double percentage = percentagem * number;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "AO"));
        String formattedPercentageValue = currencyFormat.format(percentage);
        Log.i("Valor",""+formattedPercentageValue);
        return formattedPercentageValue.replaceAll("Kz", "");
    }
}