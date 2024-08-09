package com.example.goangola.helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MoneyTextWatcher implements TextWatcher {

    private final EditText editText;
    private String current = "";

    public MoneyTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().equals(current)) {
            editText.removeTextChangedListener(this);

            // Remove todos os caracteres não numéricos
            String cleanString = s.toString().replaceAll("[^\\d]", "");

            try {
                double parsed = cleanString.isEmpty() ? 0 : Double.parseDouble(cleanString);

                // Configura o formato decimal para moeda sem o símbolo de moeda
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "AO")));
                String formatted = decimalFormat.format(parsed / 100);

                current = formatted;

                // Mantém a posição do cursor antes da mudança
                int cursorPosition = editText.getSelectionStart() + (formatted.length() - s.length());

                editText.setText(formatted);

                // Reposiciona o cursor na posição correta
                editText.setSelection(Math.max(0, Math.min(cursorPosition, formatted.length())));
            } catch (NumberFormatException e) {
                current = "";
            }

            editText.addTextChangedListener(this);
        }
    }

    public int getIntegerFromFormattedText() {
        // Obtém o texto do EditText e remove caracteres não numéricos
        String text = editText.getText().toString().replaceAll("[^\\d]", "");
        // Converte o texto limpo para um inteiro
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }
}


