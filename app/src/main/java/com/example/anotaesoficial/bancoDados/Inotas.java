package com.example.anotaesoficial.bancoDados;

import com.example.anotaesoficial.model.Anotacoes;

import java.util.List;

public interface Inotas {

    public boolean salvar(Anotacoes notas);
    public boolean atualizar(Anotacoes notas);
    public boolean deletar(Anotacoes notas);
    public List<Anotacoes> listar();
}
