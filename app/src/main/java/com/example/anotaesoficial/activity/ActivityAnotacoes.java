package com.example.anotaesoficial.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
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
import com.example.anotaesoficial.config.ValoresPadroes;
import com.example.anotaesoficial.databinding.ActivityAnotacoesBinding;
import com.example.anotaesoficial.model.Anotacoes;
import com.example.anotaesoficial.service.BancoService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityAnotacoes extends AppCompatActivity {

    private Anotacoes anotacoesAtual;
    private BancoService bancoService;
    private Preferences preferencesRef;
    private String txtRestartCampo = "";
    private ActivityAnotacoesBinding binding;
    private ActionBar actionBar;
    private final String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,};

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnotacoesBinding.inflate(getLayoutInflater());
        View viewbinding = binding.getRoot();
        setContentView(viewbinding);

        bancoService =  new BancoService(getApplicationContext());
        preferencesRef = new Preferences(getApplicationContext());

        binding.includeCampoTexto.fabSalvarText.setOnClickListener(view -> validandocampos());

        setSupportActionBar(binding.toolbar);

        Permissoes.validarPermissoes(permissoesNecessarias,this,1);
        actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (txtRestartCampo.equals("")){
            anotacaoClidada();
        }else{
            binding.includeCampoTexto.editText.setText(txtRestartCampo);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        configCorText();
        configCorFundo();
        configTamanhoFont();
        focoEdit();
        super.onStart();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        focoEdit();
    }

    private void focoEdit()
    {
        binding.includeCampoTexto.editText.setSelection(binding.includeCampoTexto.editText.getText().length());
    }

    public void anotacaoClidada(){
        anotacoesAtual = (Anotacoes) getIntent().getSerializableExtra(ValoresPadroes.NOTA_CLICACDA);

        if (anotacoesAtual != null){
            binding.includeCampoTexto.editTitulo.setText(anotacoesAtual.getTitulo());
            binding.includeCampoTexto.editText.setText(anotacoesAtual.getcampoText());
            binding.includeCampoTexto.ultimaAtualizacao.setText(anotacoesAtual.getUltimaAtualizacao());
        }


    }

    private boolean validandocampos(){
        if (!binding.includeCampoTexto.editTitulo.getText().toString().isEmpty() || !binding.includeCampoTexto.editText.getText().toString().isEmpty()){
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
        boolean validacaoMetodo = valideSave(binding.includeCampoTexto.editTitulo.getText().toString(),binding.includeCampoTexto.editText.getText().toString());
            if (validacaoMetodo){
            Anotacoes anotacoes = new Anotacoes();
            anotacoes.setTitulo(binding.includeCampoTexto.editTitulo.getText().toString());
            anotacoes.setcampoText(binding.includeCampoTexto.editText.getText().toString());
            anotacoes.setData(DataForm.dataAtual());
            anotacoes.setUltimaAtualizacao(DataForm.dataAtual());
            if (bancoService.save(anotacoes)) {
                finish();
            }else {
                msg("Erro ao salvar");
            }
            }else{
                msg("Digite nos dois campos!");
    }
    }

    public void atualizandoAnotacoes(){

        boolean validacaoMetodo = valideSave(binding.includeCampoTexto.editTitulo.getText().toString(),binding.includeCampoTexto.editText.getText().toString());
       if (validacaoMetodo){
                Anotacoes anotacoes = new Anotacoes();
                anotacoes.setTitulo(binding.includeCampoTexto.editTitulo.getText().toString());
                anotacoes.setcampoText(binding.includeCampoTexto.editText.getText().toString());
                anotacoes.setUltimaAtualizacao(DataForm.dataAtual());
                anotacoes.setId(anotacoesAtual.getId());

                if (bancoService.atualizar(anotacoes)) {
                    finish();
                } else {
                    msg("Erro ao atualizar");
                }
        }else{
           msg("Digite nos dois campos!");
       }
    }

    private boolean valideSave(@NonNull String edit1, String edit2)
    {
        if (!edit1.isEmpty())
        {
            if (!edit2.isEmpty())
            {
                return true;
            }
        }
        return false;

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
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.setGroupVisible(R.id.menu_campoText,true);

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_excluir:
                binding.includeCampoTexto.editText.setText("");
                binding.includeCampoTexto.editTitulo.setText("");
                break;
            case R.id.menu_config:
                startActivity(new Intent(getApplicationContext(),ConfigColorActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configCorText(){
        String corEscolhida  = preferencesRef.recuperarCorTexto();

        switch (corEscolhida) {
            case "1":
                binding.includeCampoTexto.editText.setTextColor(getColor(R.color.vermelhoCor1));
                binding.includeCampoTexto.editTitulo.setTextColor(getColor(R.color.vermelhoCor1));
                break;
            case "2":
                binding.includeCampoTexto.editText.setTextColor(getColor(R.color.padraoCor2));
                binding.includeCampoTexto.editTitulo.setTextColor(getColor(R.color.padraoCor2));
                break;
            case "3":
                binding.includeCampoTexto.editText.setTextColor(getColor(R.color.roxoCor3));
                binding.includeCampoTexto.editTitulo.setTextColor(getColor(R.color.roxoCor3));
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configCorFundo(){
        String corFundoEscolhida  = preferencesRef.recuperarCorFundo();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        if (corFundoEscolhida.equals("1")){
            binding.includeCampoTexto.relativeLayout.setBackgroundColor(getColor(R.color.amareloFundoCor1));
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.amareloFundoCor1)));
        }else if (corFundoEscolhida.equals("2")){
            binding.includeCampoTexto.relativeLayout.setBackgroundColor(getColor(R.color.marronFundoCor2));
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.marronFundoCor2)));
        }else if (corFundoEscolhida.equals("3")){
            binding.includeCampoTexto.relativeLayout.setBackgroundColor(getColor(R.color.padraoFundoCor3));
           actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.padraoFundoCor3)));
        }else if (corFundoEscolhida == "" || corFundoEscolhida == null){
            binding.includeCampoTexto.relativeLayout.setBackgroundColor(getColor(R.color.padraoFundoCor3));
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.padraoFundoCor3)));
        }

        }
    }

    private void configTamanhoFont(){
        String size = preferencesRef.recuperarTamanhoFont();

        if (!size.equals("")){
            binding.includeCampoTexto.editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(size));
        }else{
            binding.includeCampoTexto.editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
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
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões!");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", (dialogInterface, i) -> finish());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}