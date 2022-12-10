package com.example.anotaesoficial.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anotaesoficial.R;

public class FeedbackActivity extends AppCompatActivity {
    private EditText editFeedback;
    private Button btnEnviarFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        
        editFeedback = findViewById(R.id.editFeedback);
        btnEnviarFeed = findViewById(R.id.btnEnviarFeed);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback/Avaliação");


        btnEnviarFeed.setEnabled(false);

        editFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnEnviarFeed.setEnabled(true);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!editFeedback.getText().toString().isEmpty()) {
                    btnEnviarFeed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            validandoFeedback();

                        }
                    });
                }else{
                    btnEnviarFeed.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void validandoFeedback(){
        
        String feedback = editFeedback.getText().toString();
        
        if (!feedback.isEmpty()){
            enviarEmail();

            editFeedback.setText("");
        }else{
            Toast.makeText(this, "Hummm, seu campo de texto esta vazio.", Toast.LENGTH_SHORT).show();
        }
    }

    private void enviarEmail(){

        Intent intent = new Intent(Intent.ACTION_SEND);

        try {

            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"isaacsuellen8@Gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Anotaões");
            intent.putExtra(Intent.EXTRA_TEXT, editFeedback.getText().toString());

            intent.setType("plain/text");
            intent.setType("message/rfc822");

            startActivity(Intent.createChooser(intent, "Escolha o app para enviar email"));
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "Nenhum aplicativo de email encontrado, baixe na loja de apps!", Toast.LENGTH_SHORT).show();
        }
    }

}