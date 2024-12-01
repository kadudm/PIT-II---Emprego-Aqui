package com.manzoli.quati.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.manzoli.quati.adapter.AdapterVagas;
import com.manzoli.quati.helper.ConfiguracaoFirebase;
import com.manzoli.quati.helper.RecyclerItemClickListener;
import com.manzoli.quati.model.Vaga;
import dmax.dialog.SpotsDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class VagasActivity extends AppCompatActivity {
  private AdapterVagas adapterVagas;
  
  private FirebaseAuth autenticacao;
  
  private Button botaoServicos;
  
  private Button buttonAtividadeVaga;
  
  private Button buttonEstadoVaga;
  
  private AlertDialog dialog;
  
  private boolean filtrandoPorAtividade = false;
  
  private boolean filtrandoPorEstado = false;
  
  private String filtroAtividade = "";
  
  private String filtroEstado = "";
  
  private List<Vaga> listaVagas = new ArrayList<Vaga>();
  
  private RecyclerView recyclerVagasPublicas;
  
  private SearchView searchView;
  
  private DatabaseReference vagasPublicasRef;
  
  private DatabaseReference vagasPublicasRefP;
  
  private void recuperarVagasPublicas() {
    this.dialog = (new SpotsDialog.Builder()).setContext((Context)this).setMessage("Carregando vagas").setCancelable(false).build();
    this.dialog.show();
    this.vagasPublicasRef = ConfiguracaoFirebase.getFirebase().child("vagas");
    this.vagasPublicasRef.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            VagasActivity.this.listaVagas.clear();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
              Iterator<DataSnapshot> iterator1 = ((DataSnapshot)iterator.next()).getChildren().iterator();
              while (iterator1.hasNext()) {
                Iterator<DataSnapshot> iterator2 = ((DataSnapshot)iterator1.next()).getChildren().iterator();
                while (iterator2.hasNext()) {
                  Vaga vaga = (Vaga)((DataSnapshot)iterator2.next()).getValue(Vaga.class);
                  VagasActivity.this.listaVagas.add(vaga);
                } 
              } 
            } 
            Collections.reverse(VagasActivity.this.listaVagas);
            VagasActivity.this.adapterVagas.notifyDataSetChanged();
            VagasActivity.this.dialog.dismiss();
          }
        });
  }
  
  public void filtrarPorAtividadeVaga(View paramView) {
    if (this.filtrandoPorEstado == true) {
      AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
      builder.setTitle("Selecione a atividade desejada");
      View view = getLayoutInflater().inflate(2131492921, null);
      final Spinner spinnerAtividade = (Spinner)view.findViewById(2131296629);
      ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367048, (Object[])getResources().getStringArray(2130903040));
      arrayAdapter.setDropDownViewResource(17367049);
      spinner.setAdapter((SpinnerAdapter)arrayAdapter);
      builder.setView(view);
      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
              VagasActivity.access$402(VagasActivity.this, spinnerAtividade.getSelectedItem().toString());
              VagasActivity.this.recuperarVagasPorAtividade();
              VagasActivity.access$202(VagasActivity.this, true);
            }
          });
      builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {}
          });
      builder.create().show();
      return;
    } 
    Toast.makeText((Context)this, "Selecione primeiro um estado", 0).show();
  }
  
  public void filtrarPorEstadoVaga(View paramView) {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setTitle("Selecione o estado desejado");
    View view = getLayoutInflater().inflate(2131492921, null);
    final Spinner spinnerEstado = (Spinner)view.findViewById(2131296629);
    ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367048, (Object[])getResources().getStringArray(2130903042));
    arrayAdapter.setDropDownViewResource(17367049);
    spinner.setAdapter((SpinnerAdapter)arrayAdapter);
    builder.setView(view);
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            VagasActivity.access$502(VagasActivity.this, spinnerEstado.getSelectedItem().toString());
            VagasActivity.this.recuperarVagasPorEstado();
            VagasActivity.access$802(VagasActivity.this, true);
          }
        });
    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {}
        });
    builder.create().show();
  }
  
  public void inicializarComponentes() {
    this.botaoServicos = (Button)findViewById(2131296356);
    this.recyclerVagasPublicas = (RecyclerView)findViewById(2131296577);
    this.buttonEstadoVaga = (Button)findViewById(2131296362);
    this.buttonAtividadeVaga = (Button)findViewById(2131296360);
    this.searchView = (SearchView)findViewById(2131296596);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492899);
    getSupportActionBar().setTitle("Vagas de trabalho:");
    ((FloatingActionButton)findViewById(2131296358)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (VagasActivity.this.autenticacao.getCurrentUser() != null) {
              VagasActivity vagasActivity = VagasActivity.this;
              vagasActivity.startActivity(new Intent(vagasActivity.getApplicationContext(), CadastrarVagaActivity.class));
              return;
            } 
            Toast.makeText((Context)VagasActivity.this, "Faça login para cadastrar uma vaga na lista de vagas", 0).show();
          }
        });
    this.autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    inicializarComponentes();
    this.vagasPublicasRef = ConfiguracaoFirebase.getFirebase().child("vagas");
    this.botaoServicos.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            VagasActivity vagasActivity = VagasActivity.this;
            vagasActivity.startActivity(new Intent(vagasActivity.getApplicationContext(), ServicosActivity.class));
          }
        });
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context)this);
    linearLayoutManager.setReverseLayout(false);
    linearLayoutManager.setStackFromEnd(false);
    this.recyclerVagasPublicas.setLayoutManager((RecyclerView.LayoutManager)linearLayoutManager);
    this.recyclerVagasPublicas.setHasFixedSize(true);
    this.adapterVagas = new AdapterVagas(this.listaVagas, (Context)this);
    this.recyclerVagasPublicas.setAdapter((RecyclerView.Adapter)this.adapterVagas);
    recuperarVagasPublicas();
    RecyclerView recyclerView = this.recyclerVagasPublicas;
    recyclerView.addOnItemTouchListener((RecyclerView.OnItemTouchListener)new RecyclerItemClickListener((Context)this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View param1View, int param1Int) {
              Vaga vaga = VagasActivity.this.listaVagas.get(param1Int);
              Intent intent = new Intent((Context)VagasActivity.this, DetalhesVagaActivity.class);
              intent.putExtra("vagaSelecionada", (Serializable)vaga);
              VagasActivity.this.startActivity(intent);
            }
            
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {}
            
            public void onLongItemClick(View param1View, int param1Int) {}
          }));
    this.searchView.setQueryHint("Pesquisar em vagas");
    this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          public boolean onQueryTextChange(final String newText) {
            if (VagasActivity.this.filtrandoPorAtividade == true) {
              VagasActivity.access$302(VagasActivity.this, ConfiguracaoFirebase.getFirebase().child("vagas").child(VagasActivity.this.filtroEstado).child(VagasActivity.this.filtroAtividade));
              VagasActivity.this.vagasPublicasRef.addValueEventListener(new ValueEventListener() {
                    public void onCancelled(DatabaseError param2DatabaseError) {
                      VagasActivity.this.recuperarVagasPublicas();
                      Toast.makeText((Context)VagasActivity.this, "Selecionar filtros novamente", 0).show();
                    }
                    
                    public void onDataChange(DataSnapshot param2DataSnapshot) {
                      VagasActivity.this.listaVagas.clear();
                      Iterator<DataSnapshot> iterator = param2DataSnapshot.getChildren().iterator();
                      while (iterator.hasNext()) {
                        Vaga vaga = (Vaga)((DataSnapshot)iterator.next()).getValue(Vaga.class);
                        if (vaga.getTitulo().toUpperCase().equals(newText.toUpperCase())) {
                          VagasActivity.this.listaVagas.add(vaga);
                          int i = VagasActivity.this.listaVagas.size();
                          StringBuilder stringBuilder = new StringBuilder();
                          stringBuilder.append("total:    ");
                          stringBuilder.append(i);
                          Log.i("totalVagas", stringBuilder.toString());
                        } 
                      } 
                      Collections.reverse(VagasActivity.this.listaVagas);
                      VagasActivity.this.adapterVagas.notifyDataSetChanged();
                    }
                  });
              return true;
            } 
            VagasActivity.access$302(VagasActivity.this, ConfiguracaoFirebase.getFirebase().child("vagas"));
            VagasActivity.this.vagasPublicasRef.addValueEventListener(new ValueEventListener() {
                  public void onCancelled(DatabaseError param2DatabaseError) {
                    VagasActivity.this.recuperarVagasPublicas();
                  }
                  
                  public void onDataChange(DataSnapshot param2DataSnapshot) {
                    VagasActivity.this.listaVagas.clear();
                    Iterator<DataSnapshot> iterator = param2DataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()) {
                      Iterator<DataSnapshot> iterator1 = ((DataSnapshot)iterator.next()).getChildren().iterator();
                      while (iterator1.hasNext()) {
                        Iterator<DataSnapshot> iterator2 = ((DataSnapshot)iterator1.next()).getChildren().iterator();
                        while (iterator2.hasNext()) {
                          Vaga vaga = (Vaga)((DataSnapshot)iterator2.next()).getValue(Vaga.class);
                          if (vaga.getTitulo().toUpperCase().equals(newText.toUpperCase())) {
                            VagasActivity.this.listaVagas.add(vaga);
                            int i = VagasActivity.this.listaVagas.size();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("total:    ");
                            stringBuilder.append(i);
                            Log.i("totalVagas", stringBuilder.toString());
                          } 
                        } 
                      } 
                    } 
                    Collections.reverse(VagasActivity.this.listaVagas);
                    VagasActivity.this.adapterVagas.notifyDataSetChanged();
                  }
                });
            return true;
          }
          
          public boolean onQueryTextSubmit(final String query) {
            if (VagasActivity.this.filtrandoPorAtividade == true) {
              VagasActivity.access$302(VagasActivity.this, ConfiguracaoFirebase.getFirebase().child("vagas").child(VagasActivity.this.filtroEstado).child(VagasActivity.this.filtroAtividade));
              VagasActivity.this.vagasPublicasRef.addValueEventListener(new ValueEventListener() {
                    public void onCancelled(DatabaseError param2DatabaseError) {
                      VagasActivity.this.recuperarVagasPublicas();
                      Toast.makeText((Context)VagasActivity.this, "Selecionar filtros novamente", 0).show();
                    }
                    
                    public void onDataChange(DataSnapshot param2DataSnapshot) {
                      VagasActivity.this.listaVagas.clear();
                      Iterator<DataSnapshot> iterator = param2DataSnapshot.getChildren().iterator();
                      while (iterator.hasNext()) {
                        Vaga vaga = (Vaga)((DataSnapshot)iterator.next()).getValue(Vaga.class);
                        if (vaga.getTitulo().toUpperCase().equals(query.toUpperCase())) {
                          VagasActivity.this.listaVagas.add(vaga);
                          int i = VagasActivity.this.listaVagas.size();
                          StringBuilder stringBuilder = new StringBuilder();
                          stringBuilder.append("total:    ");
                          stringBuilder.append(i);
                          Log.i("totalVagas", stringBuilder.toString());
                        } 
                      } 
                      Collections.reverse(VagasActivity.this.listaVagas);
                      VagasActivity.this.adapterVagas.notifyDataSetChanged();
                    }
                  });
              return true;
            } 
            VagasActivity.access$302(VagasActivity.this, ConfiguracaoFirebase.getFirebase().child("vagas"));
            VagasActivity.this.vagasPublicasRef.addValueEventListener(new ValueEventListener() {
                  public void onCancelled(DatabaseError param2DatabaseError) {
                    VagasActivity.this.recuperarVagasPublicas();
                  }
                  
                  public void onDataChange(DataSnapshot param2DataSnapshot) {
                    VagasActivity.this.listaVagas.clear();
                    Iterator<DataSnapshot> iterator = param2DataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()) {
                      Iterator<DataSnapshot> iterator1 = ((DataSnapshot)iterator.next()).getChildren().iterator();
                      while (iterator1.hasNext()) {
                        Iterator<DataSnapshot> iterator2 = ((DataSnapshot)iterator1.next()).getChildren().iterator();
                        while (iterator2.hasNext()) {
                          Vaga vaga = (Vaga)((DataSnapshot)iterator2.next()).getValue(Vaga.class);
                          if (vaga.getTitulo().toUpperCase().equals(query.toUpperCase())) {
                            VagasActivity.this.listaVagas.add(vaga);
                            int i = VagasActivity.this.listaVagas.size();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("total:    ");
                            stringBuilder.append(i);
                            Log.i("totalVagas", stringBuilder.toString());
                          } 
                        } 
                      } 
                    } 
                    Collections.reverse(VagasActivity.this.listaVagas);
                    VagasActivity.this.adapterVagas.notifyDataSetChanged();
                  }
                });
            return true;
          }
        });
    this.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
          public boolean onClose() {
            VagasActivity.this.recuperarVagasPublicas();
            return false;
          }
        });
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu) {
    getMenuInflater().inflate(2131558400, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    switch (paramMenuItem.getItemId()) {
      default:
        return super.onOptionsItemSelected(paramMenuItem);
      case 2131296510:
        this.autenticacao.signOut();
        invalidateOptionsMenu();
      case 2131296509:
        startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
      case 2131296508:
        break;
    } 
    startActivity(new Intent(getApplicationContext(), MeusAnunciosActivity.class));
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu) {
    if (this.autenticacao.getCurrentUser() == null) {
      paramMenu.setGroupVisible(2131296464, true);
    } else {
      paramMenu.setGroupVisible(2131296466, true);
    } 
    return super.onPrepareOptionsMenu(paramMenu);
  }
  
  public void recuperarVagasPorAtividade() {
    this.dialog = (new SpotsDialog.Builder()).setContext((Context)this).setMessage("Filtrando vagas por atividade...").setCancelable(false).build();
    this.dialog.show();
    this.vagasPublicasRef = ConfiguracaoFirebase.getFirebase().child("vagas").child(this.filtroEstado).child(this.filtroAtividade);
    this.vagasPublicasRef.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            VagasActivity.this.listaVagas.clear();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
              Vaga vaga = (Vaga)((DataSnapshot)iterator.next()).getValue(Vaga.class);
              VagasActivity.this.listaVagas.add(vaga);
            } 
            Collections.reverse(VagasActivity.this.listaVagas);
            VagasActivity.this.adapterVagas.notifyDataSetChanged();
            VagasActivity.this.dialog.dismiss();
          }
        });
  }
  
  public void recuperarVagasPorEstado() {
    this.dialog = (new SpotsDialog.Builder()).setContext((Context)this).setMessage("Filtrando vagas por estado...").setCancelable(false).build();
    this.dialog.show();
    this.vagasPublicasRef = ConfiguracaoFirebase.getFirebase().child("vagas").child(this.filtroEstado);
    this.vagasPublicasRef.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            VagasActivity.this.listaVagas.clear();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
              Iterator<DataSnapshot> iterator1 = ((DataSnapshot)iterator.next()).getChildren().iterator();
              while (iterator1.hasNext()) {
                Vaga vaga = (Vaga)((DataSnapshot)iterator1.next()).getValue(Vaga.class);
                VagasActivity.this.listaVagas.add(vaga);
              } 
            } 
            Collections.reverse(VagasActivity.this.listaVagas);
            VagasActivity.this.adapterVagas.notifyDataSetChanged();
            VagasActivity.this.dialog.dismiss();
          }
        });
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\activity\VagasActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */