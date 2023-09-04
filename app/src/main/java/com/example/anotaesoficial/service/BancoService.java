package com.example.anotaesoficial.service;

import android.content.Context;
import com.example.anotaesoficial.bancoDados.AnotacoesDAO;
import com.example.anotaesoficial.model.Anotacoes;
import java.util.List;

public class BancoService {
    private AnotacoesDAO anotacoesDAO;

    public BancoService(Context context)
    {
        anotacoesDAO = new AnotacoesDAO(context);
    }


    public  boolean save(Anotacoes notas)
    {
        if (anotacoesDAO.salvar(notas))
        {
            return true;
        }

        return false;
    }

    public boolean atualizar(Anotacoes notas)
    {
        if (!anotacoesDAO.atualizar(notas))
        {
            return false;
        }
        return true;
    }

    public boolean deletar(Anotacoes notas)
    {
        if (anotacoesDAO.deletar(notas))
        {
            return true;
        }
        return false;
    }

    public List<Anotacoes> listar()
    {
        if (!anotacoesDAO.listar().isEmpty())
        {
            return anotacoesDAO.listar();
        }
        return null;
    }
}
