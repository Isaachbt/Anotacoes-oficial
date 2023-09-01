package com.example.anotaesoficial.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.anotaesoficial.R;
import com.example.anotaesoficial.databinding.ActivityFeedbackBinding;

public class FeedbackActivity extends AppCompatActivity {

    private ActivityFeedbackBinding bindingInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingInfo = ActivityFeedbackBinding.inflate(getLayoutInflater());
        View viewInfo = bindingInfo.getRoot();
        setContentView(viewInfo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback/Avaliação");


        bindingInfo.btnEnviarFeed.setEnabled(false);

        bindingInfo.editFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int total = charSequence.length() + 1;
                if (total >= 1)
                {
                    bindingInfo.btnEnviarFeed.setEnabled(true);
                }else
                {
                    bindingInfo.btnEnviarFeed.setEnabled(false);
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!bindingInfo.editFeedback.getText().toString().isEmpty()) {
                    bindingInfo.btnEnviarFeed.setOnClickListener(view -> validandoFeedback());
                }else{
                    bindingInfo.btnEnviarFeed.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dandoAvalicao();
    }

    private void validandoFeedback(){
        
        String feedback = bindingInfo.editFeedback.getText().toString();
        
        if (!feedback.isEmpty()){
            enviarEmail();

            bindingInfo.editFeedback.setText("");
        }else{
            Toast.makeText(this, "Hummm, seu campo de texto esta vazio!", Toast.LENGTH_SHORT).show();
        }
    }

    private void dandoAvalicao()
    {
        bindingInfo.rantingAvaliacao.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            int totalEstrelas = (int) v;
            String packageName = "com.android.chrome";
            String url = "market://details?id=" + packageName + "&reviewId=" + totalEstrelas;

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Play Store não encontrada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarEmail(){

        Intent intent = new Intent(Intent.ACTION_SEND);

        try {

            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"dev.comoponyi.saac@Gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Anotaões");
            intent.putExtra(Intent.EXTRA_TEXT, bindingInfo.editFeedback.getText().toString());

            intent.setType("plain/text");
            intent.setType("message/rfc822");

            startActivity(Intent.createChooser(intent, "Escolha o app para enviar email!"));
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "Nenhum aplicativo de email encontrado, baixe na loja de apps!", Toast.LENGTH_SHORT).show();
        }
    }

}