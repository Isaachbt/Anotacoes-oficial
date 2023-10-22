package com.example.anotaesoficial.activity.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.anotaesoficial.R;
import com.example.anotaesoficial.activity.MainActivity;
import com.example.anotaesoficial.bancoFirebase.config.ConfiguracaoFirebase;
import com.example.anotaesoficial.bancoFirebase.controller.ControllerFirebase;
import com.example.anotaesoficial.config.Base64Custom;
import com.example.anotaesoficial.databinding.ActivityCadastroBinding;
import com.example.anotaesoficial.model.FolderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();


        binding.btnValidar.setOnClickListener(view -> {
            if (binding.formaCadastro.isChecked())
            {
                cadastrandoUser();
            }else{
               login();
            }
        });
        binding.formaCadastro.setOnClickListener(view -> {
            String txt;
            if (binding.formaCadastro.isChecked())
            {
               txt = "Cadastrar";
            }else{
                txt = "Login";
            }
            binding.btnValidar.setText(txt);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarusuarioLogado();
    }

    private void login() {

        String email = Objects.requireNonNull(binding.editEmail.getText()).toString();
        String senha = Objects.requireNonNull(binding.editSenha.getText()).toString();
        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                if (isValidEmail(email)) {
                    auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                String execao = "";

                                try {
                                    throw Objects.requireNonNull(task.getException());
                                } catch (FirebaseAuthInvalidUserException e) {
                                    execao = "Usuari não cadastrado";
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    execao = "Não corresponde a um usuario";
                                } catch (Exception e) {
                                    Log.e("ERRO", Objects.requireNonNull(e.getMessage()));
                                    e.printStackTrace();
                                }
                                Toast.makeText(this, execao, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        }

    }

    private void cadastrandoUser()
    {
        String email = Objects.requireNonNull(binding.editEmail.getText()).toString();
        String senha = Objects.requireNonNull(binding.editSenha.getText()).toString();

            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {
                    if (isValidEmail(email)) {
                    auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            salvandoUser(email);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            String execao = "";
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (FirebaseAuthWeakPasswordException e) {
                                binding.txtInputSenhaLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.RED));
                                execao = "Digite uma senha forte.";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                binding.txtInputEmailLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.RED));
                                execao = "Digite um email valido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                execao = "Conta ja cadastrada";
                            } catch (Exception e) {
                                Log.e("ERRO", "Erro ao tentar cadastrar: " + e.getMessage());
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), execao, Toast.LENGTH_SHORT).show();
                        }

                    });

                }

            } else binding.txtInputSenhaLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.RED));

        }else binding.txtInputEmailLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.RED));
    }

    private void salvandoUser(String email)
    {
        String codificado = Base64Custom.codificarBase64(email);
        ControllerFirebase.salvarUsuario(codificado);
    }

    private boolean isValidEmail(String email) {
        Pattern patternGmail = Pattern.compile("@gmail\\.com$");
        Pattern patternHotmail = Pattern.compile("@hotmail\\.com$");

        Matcher matcherGmail = patternGmail.matcher(email);
        Matcher matcherHotmail = patternHotmail.matcher(email);

        if (matcherGmail.find()) {
            return true;
        } else if (matcherHotmail.find()) {
            return true;
        } else {
            Toast.makeText(this, "E-mail invalido!", Toast.LENGTH_SHORT).show();
           return false;
        }
    }
    public void verificarusuarioLogado(){
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }
}