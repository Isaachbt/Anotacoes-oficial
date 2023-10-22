package com.example.anotaesoficial.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.anotaesoficial.R;
import com.example.anotaesoficial.adapter.AdpterFolderNome;
import com.example.anotaesoficial.adapter.MyAdapter;
import com.example.anotaesoficial.bancoFirebase.config.ConfiguracaoFirebase;
import com.example.anotaesoficial.bancoFirebase.config.ReferenciaFirebase;
import com.example.anotaesoficial.bancoFirebase.config.UsuarioFirebase;
import com.example.anotaesoficial.bancoFirebase.controller.ControllerFirebase;
import com.example.anotaesoficial.config.Base64Custom;
import com.example.anotaesoficial.config.Logs;
import com.example.anotaesoficial.config.RecyclerItemClick;
import com.example.anotaesoficial.config.ValoresPadroes;
import com.example.anotaesoficial.databinding.ActivityMainBinding;
import com.example.anotaesoficial.databinding.AlertDalogBinding;
import com.example.anotaesoficial.model.Anotacoes;
import com.example.anotaesoficial.model.FolderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private List<Anotacoes> listAnotacoes;
    private List<FolderModel> listFolder;
    private Anotacoes anotSelec;
    private MyAdapter adapter;
    private FolderModel folderModel;
    private AdpterFolderNome adpterFolder;
    private ActivityMainBinding binding;
    private android.app.AlertDialog alertDialog;
    private AlertDalogBinding alertDalogBinding;
    private ValueEventListener valueEventListenerAnotacoes;
    private ValueEventListener valueEventListenerFolder;
    private DatabaseReference ref;
    private DatabaseReference refAnotaces;
    private DatabaseReference database;
    private DatabaseReference dataFolder;
    private FirebaseAuth autenticacao;
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ref = ConfiguracaoFirebase.getFirebaseDatabase();
        refAnotaces = ConfiguracaoFirebase.getFirebaseDatabase();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        listAnotacoes = new ArrayList<>();
        folderModel = new FolderModel();

        listFolder = new ArrayList<>();

        recuperandoFolder();
        carregarListaFolderRecycler();
        confiRecyclerAnotacoes();
        clickRecyclerFolder();
        binding.imgBtnNewFolder.setOnClickListener(view1 -> {
            configAlertDialog();
        });

        binding.novaAnotacaoFloat.setOnClickListener(view1 -> {
            startActivity(new Intent(this, ActivityAnotacoes.class));
        });

    }

    private void confiRecyclerAnotacoes()
    {
        if (listAnotacoes != null) {
            adapter = new MyAdapter(listAnotacoes);
            binding.recyclerView.setAdapter(adapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            binding.recyclerView.setLayoutManager(layoutManager);
            binding.recyclerView.setVerticalScrollBarEnabled(false);
            binding.recyclerView.setHasFixedSize(true);
            Collections.reverse(listAnotacoes);
        }
    }

    private void recuperandoAnotacoes()
    {
        //ref = UsuarioFirebase.getRetornarAnotacoes();
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String emailCodificado = Base64Custom.codificarBase64(emailUsuario);
        database = refAnotaces.child(ReferenciaFirebase.CHAVE_ANOTACOES).child(emailCodificado);
        valueEventListenerAnotacoes = database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAnotacoes.clear();
                for (DataSnapshot dados: snapshot.getChildren()){
                    Anotacoes anot = dados.getValue(Anotacoes.class);
                    Log.i("INFO", "==========="+Objects.requireNonNull(anot).getCampo_Text()+"=========");
                    listAnotacoes.add(anot);
                }
                Log.i("INFO", String.valueOf(listAnotacoes.size()));
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Logs.erro(String.valueOf(error));
                Log.e("ERRO","Não foi possivel recuperar as anotacoes");
            }
        });

    }

    private void recuperandoFolder()
    {
        ref = UsuarioFirebase.getRetornarFolder();
        dataFolder = ref;

        valueEventListenerFolder = dataFolder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFolder.clear();
                for (DataSnapshot dados: snapshot.getChildren()){
                    FolderModel folderRcu;
                    folderRcu = dados.getValue(FolderModel.class);

                    listFolder.add(folderRcu);
                }
                adpterFolder.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Logs.erro(String.valueOf(error));
            }
        });
    }

    private void configAlertDialog()
    {
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this,R.style.TemaAlertdialo);
        alertDalogBinding = AlertDalogBinding.inflate(LayoutInflater.from(this));
        alert.setView(alertDalogBinding.getRoot());
        salvarPastaAlertDialog();
        alertDialog = alert.create();
        alertDialog.show();
    }

    private void salvarPastaAlertDialog() {

        alertDalogBinding.btnSalvarNovaPasta.setOnClickListener(view -> {
            if (!alertDalogBinding.editNovaPasta.getText().toString().isEmpty())
            {
                if (!alertDalogBinding.editNovaPasta.getText().toString().isEmpty()){
                    ControllerFirebase.salvarFolder(alertDalogBinding.editNovaPasta.getText().toString());
                    carregarListaFolderRecycler();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(this, "Digite um nome antes.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void carregarListaFolderRecycler(){

            if (listFolder != null) {
                adpterFolder = new AdpterFolderNome(listFolder);
                binding.recyclerViewFolder.setAdapter(adpterFolder);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                binding.recyclerViewFolder.setLayoutManager(layoutManager);
                binding.recyclerViewFolder.setHorizontalScrollBarEnabled(false);
                binding.recyclerViewFolder.setHasFixedSize(true);
                adpterFolder.setSelectedButtonPosition(0);
            }

    }
    @Override
    protected void onStart() {
        super.onStart();
        recuperandoAnotacoes();
        recuperandoFolder();
    }
    @Override
    protected void onStop() {
        super.onStop();
        database.removeEventListener(valueEventListenerAnotacoes);
        dataFolder.removeEventListener(valueEventListenerFolder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_campo_text,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {
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

    private void clickRecyclerFolder(){
        binding.recyclerViewFolder.addOnItemTouchListener(new RecyclerItemClick(getApplicationContext(), binding.recyclerViewFolder,
                new RecyclerItemClick.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        FolderModel folder = listFolder.get(position);

                        //carregarListaAnotacoces();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                        folderModel = listFolder.get(position);

                        dialog.setTitle("Excluir");
                        dialog.setIcon(R.drawable.ic_delete);
                        dialog.setMessage("Tem certeza que deseja excluir essa pasta: '"+folderModel.getNomeFolder()+"'?" );

                        dialog.setPositiveButton("Sim", (dialog1, which) -> {

//                            if (controllerBase.deletarFolder(folderModel.getIdFolder())){
//                                carregarListaFolder();
//                                msg("Apagado com sucesso");
//                            }else{
//
//                            }
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