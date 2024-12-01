package com.manzoli.quati.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.manzoli.quati.adapter.AdapterServicos;
import com.manzoli.quati.adapter.AdapterVagas;
import com.manzoli.quati.helper.ConfiguracaoFirebase;
import com.manzoli.quati.helper.RecyclerItemClickListener;
import com.manzoli.quati.model.Servico;
import com.manzoli.quati.model.Vaga;
import dmax.dialog.SpotsDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeusAnunciosActivity extends AppCompatActivity {
  private AdapterServicos adapterServicos;
  
  private AdapterVagas adapterVagas;
  
  private AlertDialog dialog;
  
  private RecyclerView recyclerServicos;
  
  private RecyclerView recyclerVagas;
  
  private DatabaseReference servicoUsuarioRef;
  
  private List<Servico> servicos = new ArrayList<Servico>();
  
  private DatabaseReference vagaUsuarioRef;
  
  private List<Vaga> vagas = new ArrayList<Vaga>();
  
  private void recuperarAnuncios() {
    this.dialog = (new SpotsDialog.Builder()).setContext((Context)this).setMessage("Carregando anúncios").setCancelable(false).build();
    this.dialog.show();
    this.servicoUsuarioRef.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            MeusAnunciosActivity.this.servicos.clear();
            for (DataSnapshot dataSnapshot : param1DataSnapshot.getChildren()) {
              MeusAnunciosActivity.this.servicos.add(dataSnapshot.getValue(Servico.class));
              Collections.reverse(MeusAnunciosActivity.this.servicos);
              MeusAnunciosActivity.this.adapterServicos.notifyDataSetChanged();
              MeusAnunciosActivity.this.dialog.dismiss();
            } 
          }
        });
    this.vagaUsuarioRef.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            MeusAnunciosActivity.this.vagas.clear();
            for (DataSnapshot dataSnapshot : param1DataSnapshot.getChildren()) {
              MeusAnunciosActivity.this.vagas.add(dataSnapshot.getValue(Vaga.class));
              Collections.reverse(MeusAnunciosActivity.this.vagas);
              MeusAnunciosActivity.this.adapterVagas.notifyDataSetChanged();
              MeusAnunciosActivity.this.dialog.dismiss();
            } 
          }
        });
    this.dialog.dismiss();
  }
  
  public void inicializarComponentes() {
    this.recyclerServicos = (RecyclerView)findViewById(2131296574);
    this.recyclerVagas = (RecyclerView)findViewById(2131296576);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492897);
    this.servicoUsuarioRef = ConfiguracaoFirebase.getFirebase().child("meus_servicos").child(ConfiguracaoFirebase.getIdUsuario());
    this.vagaUsuarioRef = ConfiguracaoFirebase.getFirebase().child("minhas_vagas").child(ConfiguracaoFirebase.getIdUsuario());
    inicializarComponentes();
    setSupportActionBar((Toolbar)findViewById(2131296701));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    this.recyclerServicos.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager((Context)this));
    this.recyclerServicos.setHasFixedSize(true);
    this.adapterServicos = new AdapterServicos(this.servicos, (Context)this);
    this.recyclerServicos.setAdapter((RecyclerView.Adapter)this.adapterServicos);
    this.recyclerVagas.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager((Context)this));
    this.recyclerVagas.setHasFixedSize(true);
    this.adapterVagas = new AdapterVagas(this.vagas, (Context)this);
    this.recyclerVagas.setAdapter((RecyclerView.Adapter)this.adapterVagas);
    recuperarAnuncios();
    Toast.makeText((Context)this, "Para excluir um anúncio basta segurar apertado nele", 1).show();
    RecyclerView recyclerView = this.recyclerServicos;
    recyclerView.addOnItemTouchListener((RecyclerView.OnItemTouchListener)new RecyclerItemClickListener((Context)this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View param1View, int param1Int) {
              Servico servico = MeusAnunciosActivity.this.servicos.get(param1Int);
              Intent intent = new Intent((Context)MeusAnunciosActivity.this, DetalhesServicoActivity.class);
              intent.putExtra("servicoSelecionado", (Serializable)servico);
              MeusAnunciosActivity.this.startActivity(intent);
            }
            
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {}
            
            public void onLongItemClick(View param1View, int param1Int) {
              AlertDialog.Builder builder = new AlertDialog.Builder((Context)MeusAnunciosActivity.this);
              builder.setTitle("Deseja excluir o serviço anunciado?");
              builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                      servicoSelecionado.removerServico();
                      MeusAnunciosActivity.this.adapterServicos.notifyDataSetChanged();
                      MeusAnunciosActivity.this.startActivity(new Intent(MeusAnunciosActivity.this.getApplicationContext(), MeusAnunciosActivity.class));
                    }
                  });
              builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                  });
              MeusAnunciosActivity.this.adapterServicos.notifyDataSetChanged();
              builder.create().show();
            }
          }));
    recyclerView = this.recyclerVagas;
    recyclerView.addOnItemTouchListener((RecyclerView.OnItemTouchListener)new RecyclerItemClickListener((Context)this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View param1View, int param1Int) {
              Vaga vaga = MeusAnunciosActivity.this.vagas.get(param1Int);
              Intent intent = new Intent((Context)MeusAnunciosActivity.this, DetalhesVagaActivity.class);
              intent.putExtra("vagaSelecionada", (Serializable)vaga);
              MeusAnunciosActivity.this.startActivity(intent);
            }
            
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {}
            
            public void onLongItemClick(View param1View, int param1Int) {
              AlertDialog.Builder builder = new AlertDialog.Builder((Context)MeusAnunciosActivity.this);
              builder.setTitle("Deseja excluir a vaga anunciada?");
              builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                      vagaSelecionada.removerVaga();
                      MeusAnunciosActivity.this.adapterVagas.notifyDataSetChanged();
                      MeusAnunciosActivity.this.startActivity(new Intent(MeusAnunciosActivity.this.getApplicationContext(), MeusAnunciosActivity.class));
                    }
                  });
              builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                  });
              MeusAnunciosActivity.this.adapterVagas.notifyDataSetChanged();
              builder.create().show();
            }
          }));
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\activity\MeusAnunciosActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */