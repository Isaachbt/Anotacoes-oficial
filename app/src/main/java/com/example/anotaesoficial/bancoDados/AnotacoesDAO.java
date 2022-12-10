package com.example.anotaesoficial.bancoDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.anotaesoficial.model.Anotacoes;

import java.util.ArrayList;
import java.util.List;

public class AnotacoesDAO implements Inotas{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private Context context;


    public AnotacoesDAO(Context context){
        this.context = context;
        BancoDados bd = new BancoDados(context);
        escreve = bd.getWritableDatabase();
        le = bd.getReadableDatabase();

    }

    @Override
    public boolean salvar(Anotacoes notas) {
        ContentValues cv = new ContentValues();
        cv.put("titulo",notas.getTitulo());
        cv.put("campoText",notas.getcampoText());
        cv.put("data",notas.getData());

        try{
            escreve.insert(BancoDados.NOME_TABELA,null,cv);
            Log.i("INFO","salvo com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao salvar");
            return false;
        }
        return true;
    }

    public boolean salvarDadosTemporarios(Anotacoes notas){
        ContentValues cv = new ContentValues();
        cv.put("titulo",notas.getTitulo());
        cv.put("campoText",notas.getcampoText());
        cv.put("data",notas.getData());

        try{
            escreve.insert(BancoDados.NOME_TABELA,null,cv);
            Log.i("INFO","salvo com sucesso tt");
        }catch (Exception e){
            Log.e("INFO","Erro ao salvar ttt");
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Anotacoes notas) {
        ContentValues cv = new ContentValues();
        cv.put("titulo",notas.getTitulo());
        cv.put("campoText",notas.getcampoText());
        //cv.put("data",notas.getData());
        String[] argas = {String.valueOf(notas.getId())};
        try{
            escreve.update(BancoDados.NOME_TABELA,cv,"id=?",argas);

        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Anotacoes notas) {
        String[] argas = {String.valueOf(notas.getId())};
        try{
            escreve.delete(BancoDados.NOME_TABELA,"id=?",argas);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public List<Anotacoes> listar() {
        List<Anotacoes> anotacoesList = new ArrayList<>();
        //recuperando os dados da tabela
             String sql = "SELECT * FROM "+BancoDados.NOME_TABELA+" ;";
        Cursor c = le.rawQuery(sql,null);

        while (c.moveToNext()){
            Anotacoes notas = new Anotacoes();
            int columnIndex = c.getColumnIndex("id");
            String titulocolumnIndex = String.valueOf(c.getColumnIndex("titulo"));
            String campoTextcolumnIndex = String.valueOf(c.getColumnIndex("campoText"));
            String datacolumnIndex = String.valueOf(c.getColumnIndex("data"));

            String tituloNota = c.getString(Integer.parseInt(titulocolumnIndex));
            String campoText = c.getString(Integer.parseInt(campoTextcolumnIndex));
            String data = c.getString(Integer.parseInt(datacolumnIndex));
            Long id = c.getLong(columnIndex);

            notas.setId(id);
            notas.setTitulo(tituloNota);
            notas.setcampoText(campoText);
            notas.setData(data);
            anotacoesList.add(notas);
        }
        return anotacoesList;
    }
}
