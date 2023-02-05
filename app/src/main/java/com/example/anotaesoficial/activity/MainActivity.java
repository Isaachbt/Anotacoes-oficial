package com.example.anotaesoficial.activity;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.anotaesoficial.R;
import com.example.anotaesoficial.adapter.MyAdapter;
import com.example.anotaesoficial.bancoDados.AnotacoesDAO;
import com.example.anotaesoficial.config.Permissoes;
import com.example.anotaesoficial.config.Preferences;
import com.example.anotaesoficial.config.RecyclerItemClick;
import com.example.anotaesoficial.databinding.ActivityMainBinding;
import com.example.anotaesoficial.model.Anotacoes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floating;
    private RecyclerView recyclerView;
    private List<Anotacoes> list = new ArrayList<>();
    private MyAdapter adapter;
    private Anotacoes anotSelec;
    private Preferences preferencesRef;

    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        deletarVazio();
        config();

    }
    public void carregarListaTarefa(){
        AnotacoesDAO anotacoesDAO = new AnotacoesDAO(getApplicationContext());
        list = anotacoesDAO.listar();

        adapter = new MyAdapter(list);

        binding.recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        Collections.reverse(list);


    }
    @Override
    protected void onStart() {
        super.onStart();
        carregarListaTarefa();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_campo_text,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.menu_telaInical,true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menu_config_branco:

                startActivity(new Intent(getApplicationContext(),ConfigColorActivity.class));
                break;
            case R.id.menu_feedback:
                startActivity(new Intent(getApplicationContext(),FeedbackActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void msg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void deletarVazio(){
        Anotacoes anotacoes = new Anotacoes();
        anotacoes.setcampoText("");
        anotacoes.setTitulo("");
        AnotacoesDAO anotacoesDAO = new AnotacoesDAO(getApplicationContext());
        if (anotacoesDAO.deletar(anotacoes)){

        }
    }

    private void config(){
        binding.recyclerView.addOnItemTouchListener(new RecyclerItemClick(getApplicationContext(), binding.recyclerView,
                new RecyclerItemClick.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Anotacoes notasSelecionada = list.get(position);

                        Intent i = new Intent(getApplicationContext(),ActivityAnotacoes.class);
                        i.putExtra("notasSelecionada", notasSelecionada);
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                        anotSelec = list.get(position);

                        dialog.setTitle("Excluir");
                        dialog.setIcon(R.drawable.ic_delete);
                        dialog.setMessage("Tem certeza que deseja excluir: '"+anotSelec.getTitulo()+"'?" );

                        dialog.setPositiveButton("Sim", (dialog1, which) -> {
                            AnotacoesDAO notasDAO = new AnotacoesDAO(getApplicationContext());
                            if (notasDAO.deletar(anotSelec)){
                                carregarListaTarefa();
                                msg("Apagado com sucesso");
                            }else{

                            }
                        });
                        dialog.setNegativeButton("Não",null);

                        dialog.create();
                        dialog.show();

                    }
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));

        binding.novaAnotacaoFloat.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ActivityAnotacoes.class)));
    }
}