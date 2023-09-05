package com.example.anotaesoficial.config;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DesfazendoTexto {
    private String textoAtual;
    private List<String> refazerTexto;
    private EditText editText;

    public DesfazendoTexto(@NonNull EditText editText)
    {
        this.editText= editText;
        refazerTexto = new ArrayList<>();

       editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String texto = editText.getText().toString();

                if (!texto.isEmpty())
                {
                    addTexto(texto);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void addTexto(@NonNull String texto)
    {
        if (!texto.isEmpty())
        {
            textoAtual = texto;
            //letras.add(texto);
        }else
            System.out.println("=============== não foi possivel add texto");

    }
    public String desfazer()
    {
        if (!textoAtual.isEmpty())
        {
            refazerTexto.add(textoAtual);
            return textoAtual = textoAtual.substring(0,textoAtual.length() - 1);

        }else {
            System.out.println("========== Erro no desfazer");
            return " ";
        }
    }

    public String refazer()
    {
        if (!refazerTexto.isEmpty())
        {
            int indexMax = refazerTexto.size()  - 1;
            String refazendo = refazerTexto.get(indexMax);
            refazerTexto.remove(indexMax);
            return refazendo;

        }
        return "";
    }



}
