package com.example.anotaesoficial.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.anotaesoficial.R;
import com.example.anotaesoficial.config.Preferences;

public class ConfigColorActivity extends AppCompatActivity{

    private RadioButton btnVermelhoTxt,btnRoxoTxt, btnPadraoTxt;
    private RadioButton amareloFundo,marronClaroFundo,padraoFundo;
    private Spinner spinner;
    private int corEscolhida = 0;
    private int corBackGroudEscolhida = 0;
    private String itemSelecionado;
    private Preferences preferenceRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_color);

        preferenceRef = new Preferences(getApplicationContext());



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configurações");



    }

    public void btnSalvarAlteracoes(View view){
        validandoRdCorText();
        validandoRdFundoCOr();
        salvandoDados();

    }

    private void inicializacao(){
       btnVermelhoTxt = findViewById(R.id.btnVermelhoTxt);
       btnRoxoTxt = findViewById(R.id.btnRoxoTxt);
       btnPadraoTxt = findViewById(R.id.btnPretoTxt);

       amareloFundo = findViewById(R.id.btnAmareloFundo);
       marronClaroFundo = findViewById(R.id.btnMarronClaroFundo);
       padraoFundo = findViewById(R.id.btnPadraoFundo);

       spinner = findViewById(R.id.spinner);
    }

    private void validandoRdCorText(){
        if (btnVermelhoTxt.isChecked()){
            corEscolhida = 1;
        }else if (btnPadraoTxt.isChecked()){
            corEscolhida = 2;
        }else if (btnRoxoTxt.isChecked()){
            corEscolhida = 3;
        }
    }

    private void validandoRdFundoCOr(){

        if (amareloFundo.isChecked()){
            corBackGroudEscolhida = 1;
        }else if (marronClaroFundo.isChecked()){
            corBackGroudEscolhida = 2;
        }else if (padraoFundo.isChecked()){
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

            itemSelecionado = spinner.getSelectedItem().toString();
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

        inicializacao();
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
        spinner.setAdapter(adapterFonteSpinner);

        String sizeRecuperado = preferenceRef.recuperarTamanhoFont();
        String dadosSpinnerRef = String.valueOf(adapterFonteSpinner.getPosition(sizeRecuperado));

        if (sizeRecuperado.equals("")) {
            dadosSpinnerRef = String.valueOf(adapterFonteSpinner.getPosition("18"));
        }
        spinner.setSelection(Integer.parseInt(dadosSpinnerRef));

    }

    private void dadosIniciais(){

        if (corEscolhida == 0) {
            btnPadraoTxt.setChecked(true);
        }

        if (corBackGroudEscolhida == 0) {
            padraoFundo.setChecked(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configCorText(){
        String corEscolhida  = preferenceRef.recuperarCorTexto();

        if (corEscolhida.equals("1")){
            btnVermelhoTxt.setChecked(true);
        }else if (corEscolhida.equals("2")){
            btnPadraoTxt.setChecked(true);
        }else if (corEscolhida.equals("3")){
            btnRoxoTxt.setChecked(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configCorFundo(){
        String corFundoEscolhida  = preferenceRef.recuperarCorFundo();

        if (corFundoEscolhida.equals("1")){
            amareloFundo.setChecked(true);
        }else if (corFundoEscolhida.equals("2")){
            marronClaroFundo.setChecked(true);
        }else if (corFundoEscolhida.equals("3")){
            padraoFundo.setChecked(true);
        }
    }
}