package com.example.anotaesoficial.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.anotaesoficial.R;
import com.example.anotaesoficial.bancoDados.AnotacoesDAO;
import com.example.anotaesoficial.config.DataForm;
import com.example.anotaesoficial.config.Permissoes;
import com.example.anotaesoficial.config.Preferences;
import com.example.anotaesoficial.model.Anotacoes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityAnotacoes extends AppCompatActivity {

    private EditText editTitulo,editCampoText;
    private FloatingActionButton fab;
    private Anotacoes anotacoesAtual;
    private AnotacoesDAO anotacoesDAO;
    private boolean saironBackPressed = true;
    private Preferences preferencesRef;
    private RelativeLayout relativeLayout;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacoes);

        editCampoText = findViewById(R.id.editText);
        editTitulo = findViewById(R.id.editTitulo);
        fab = findViewById(R.id.fabSalvarText);
        relativeLayout = findViewById(R.id.relativeLayout);

        anotacoesDAO =  new AnotacoesDAO(getApplicationContext());
        preferencesRef = new Preferences(getApplicationContext());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                validandocampos();
            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Permissoes.validarPermissoes(permissoesNecessarias,this,1);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    public void anotacaoClidada(){
        anotacoesAtual = (Anotacoes) getIntent().getSerializableExtra("notasSelecionada");

        if (anotacoesAtual != null){
            editTitulo.setText(anotacoesAtual.getTitulo());
            editCampoText.setText(anotacoesAtual.getcampoText());
        }

    }

    private boolean validandocampos(){
        if (!editTitulo.getText().toString().isEmpty() || !editCampoText.getText().toString().isEmpty()){
            processandoAlteracoes();
            return true;
        }else{
            return false;
        }

    }

    public void processandoAlteracoes(){

        if (anotacoesAtual != null){
            atualizandoAnotacoes();
        }else{
            salvandoAnotacoes();
        }
    }

    public void salvandoAnotacoes(){
        if (!editTitulo.getText().toString().isEmpty()) {
            if (!editCampoText.getText().toString().isEmpty()) {
            Anotacoes anotacoes = new Anotacoes();
            anotacoes.setTitulo(editTitulo.getText().toString());
            anotacoes.setcampoText(editCampoText.getText().toString());
            anotacoes.setData(DataForm.dataAtual());
            if (anotacoesDAO.salvar(anotacoes)) {
                finish();
            }
            }else{
                msg("Digite algo antes de salvar!");
            }
        }else{
            msg("Digite algo antes de salvar!");
        }
    }

    public void atualizandoAnotacoes(){

        if (!editTitulo.getText().toString().isEmpty()) {
            if (!editCampoText.getText().toString().isEmpty()) {
                Anotacoes anotacoes = new Anotacoes();
                anotacoes.setTitulo(editTitulo.getText().toString());
                anotacoes.setcampoText(editCampoText.getText().toString());
                //anotacoes.setData(DataForm.dataAtual());
                anotacoes.setId(anotacoesAtual.getId());

                if (anotacoesDAO.atualizar(anotacoes)) {
                    finish();
                } else {
                    msg("Erro ao atualizar");
                }
            }
        }
    }

    public void msg(String txt){

        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_campo_text,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.menu_campoText,true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_excluir:
                editCampoText.setText("");
                editTitulo.setText("");
                break;
            case R.id.menu_config:
                startActivity(new Intent(getApplicationContext(),ConfigColorActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        configCorText();
        configCorFundo();
        configTamanhoFont();
        anotacaoClidada();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configCorText(){
        String corEscolhida  = preferencesRef.recuperarCorTexto();

        if (corEscolhida.equals("1")){
            editCampoText.setTextColor(getColor(R.color.vermelhoCor1));
        }else if (corEscolhida.equals("2")){
            editCampoText.setTextColor(getColor(R.color.padraoCor2));
        }else if (corEscolhida.equals("3")){
            editCampoText.setTextColor(getColor(R.color.roxoCor3));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configCorFundo(){
        String corFundoEscolhida  = preferencesRef.recuperarCorFundo();

        if (corFundoEscolhida.equals("1")){
            relativeLayout.setBackgroundColor(getColor(R.color.amareloFundoCor1));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(188,175,73)));
        }else if (corFundoEscolhida.equals("2")){
            relativeLayout.setBackgroundColor(getColor(R.color.marronFundoCor2));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(102,66,49)));
        }else if (corFundoEscolhida.equals("3")){
            relativeLayout.setBackgroundColor(getColor(R.color.padraoFundoCor3));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));
        }else if (corFundoEscolhida == "" || corFundoEscolhida == null){
            relativeLayout.setBackgroundColor(getColor(R.color.padraoFundoCor3));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));
        }
    }

    private void configTamanhoFont(){
        String size = preferencesRef.recuperarTamanhoFont();

        if (!size.equals("")){
            editCampoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(size));
        }else{
            editCampoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        for (int permissaoResultado:grantResults){
            if (permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar o app é  necessário aceitar as permissões!");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}