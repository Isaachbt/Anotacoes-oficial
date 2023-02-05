package com.example.anotaesoficial.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.anotaesoficial.R;
import com.example.anotaesoficial.config.Preferences;
import com.example.anotaesoficial.databinding.ActivityConfigColorBinding;

public class ConfigColorActivity extends AppCompatActivity{

    private int corEscolhida = 0;
    private int corBackGroudEscolhida = 0;
    private String itemSelecionado;
    private Preferences preferenceRef;
    private ActivityConfigColorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfigColorBinding.inflate(getLayoutInflater());
        View viewBind = binding.getRoot();
        setContentView(viewBind);

        preferenceRef = new Preferences(getApplicationContext());



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configurações");



    }

    public void btnSalvarAlteracoesOnclick(View view){
        validandoRdCorText();
        validandoRdFundoCOr();
        salvandoDados();

    }

    private void validandoRdCorText(){
        if (binding.btnVermelhoTxt.isChecked()){
            corEscolhida = 1;
        }else if (binding.btnPretoTxt.isChecked()){
            corEscolhida = 2;
        }else if (binding.btnRoxoTxt.isChecked()){
            corEscolhida = 3;
        }
    }

    private void validandoRdFundoCOr(){

        if (binding.btnAmareloFundo.isChecked()){
            corBackGroudEscolhida = 1;
        }else if (binding.btnMarronClaroFundo.isChecked()){
            corBackGroudEscolhida = 2;
        }else if (binding.btnPadraoFundo.isChecked()){
            corBackGroudEscolhida = 3;
        }
    }

    private void salvandoDados(){

        if (corEscolhida != 0){
            preferenceRef.salvarCorTexto(String.valueOf(corEscolhida));
        }else{
            msg("Erro ao alterar cor");
            return;
        }

        if (corBackGroudEscolhida != 0){
            preferenceRef.salvarCorFundo(String.valueOf(corBackGroudEscolhida));
        }else{
            msg("Erro ao salvar cor de fundo");
            return;
        }

            itemSelecionado = binding.spinner.getSelectedItem().toString();
        if (!itemSelecionado.equals("")){
            preferenceRef.salvarTamanhoText(itemSelecionado);
        }else{
            msg("Erro ao alterar tamanho da fonte: "+itemSelecionado);
            //return;
        }

        finish();
    }

    public void msg(String txt){

        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {

        dadosIniciais();
        configSpinner();
        configCorFundo();
        configCorText();

        super.onStart();
    }

    private void configSpinner(){

        String[] fontesSpinnes = getResources().getStringArray(R.array.tamanhoFonte);
        ArrayAdapter<String> adapterFonteSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,fontesSpinnes);
        adapterFonteSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapterFonteSpinner);

        String sizeRecuperado = preferenceRef.recuperarTamanhoFont();
        String dadosSpinnerRef = String.valueOf(adapterFonteSpinner.getPosition(sizeRecuperado));

        if (sizeRecuperado.equals("")) {
            dadosSpinnerRef = String.valueOf(adapterFonteSpinner.getPosition("18"));
        }
        binding.spinner.setSelection(Integer.parseInt(dadosSpinnerRef));

    }

    private void dadosIniciais(){

        if (corEscolhida == 0) {
            binding.btnPretoTxt.setChecked(true);
        }

        if (corBackGroudEscolhida == 0) {
            binding.btnPadraoFundo.setChecked(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configCorText(){
        String corEscolhida  = preferenceRef.recuperarCorTexto();

        if (corEscolhida.equals("1")){
            binding.btnVermelhoTxt.setChecked(true);
        }else if (corEscolhida.equals("2")){
            binding.btnPretoTxt.setChecked(true);
        }else if (corEscolhida.equals("3")){
            binding.btnRoxoTxt.setChecked(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configCorFundo(){
        String corFundoEscolhida  = preferenceRef.recuperarCorFundo();

        if (corFundoEscolhida.equals("1")){
            binding.btnAmareloFundo.setChecked(true);
        }else if (corFundoEscolhida.equals("2")){
            binding.btnMarronClaroFundo.setChecked(true);
        }else if (corFundoEscolhida.equals("3")){
            binding.btnPadraoFundo.setChecked(true);
        }
    }
}