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
import com.manzoli.quati.adapter.AdapterServicos;
import com.manzoli.quati.helper.ConfiguracaoFirebase;
import com.manzoli.quati.helper.RecyclerItemClickListener;
import com.manzoli.quati.model.Servico;
import dmax.dialog.SpotsDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ServicosActivity extends AppCompatActivity {
  private AdapterServicos adapterServicos;
  
  private FirebaseAuth autenticacao;
  
  private Button botaoVagas;
  
  private Button buttonAtividadeServico;
  
  private Button buttonEstadoServico;
  
  private AlertDialog dialog;
  
  private boolean filtrandoPorAtividade = false;
  
  private boolean filtrandoPorEstado = false;
  
  private String filtroAtividade = "";
  
  private String filtroEstado = "";
  
  private List<Servico> listaServicos = new ArrayList<Servico>();
  
  private RecyclerView recyclerServicosPublicos;
  
  private SearchView searchView;
  
  private DatabaseReference servicosPublicosRef;
  
  private void inicializarComponentes() {
    this.botaoVagas = (Button)findViewById(2131296353);
    this.recyclerServicosPublicos = (RecyclerView)findViewById(2131296575);
    this.buttonEstadoServico = (Button)findViewById(2131296361);
    this.buttonAtividadeServico = (Button)findViewById(2131296359);
    this.searchView = (SearchView)findViewById(2131296595);
  }
  
  public void filtrarPorAtividadeServico(View paramView) {
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
              ServicosActivity.access$402(ServicosActivity.this, spinnerAtividade.getSelectedItem().toString());
              ServicosActivity.this.recuperarServicosPorAtividade();
              ServicosActivity.access$202(ServicosActivity.this, true);
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
  
  public void filtrarPorEstadoServico(View paramView) {
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
            ServicosActivity.access$502(ServicosActivity.this, spinnerEstado.getSelectedItem().toString());
            ServicosActivity.this.recuperarServicosPorEstado();
            ServicosActivity.access$702(ServicosActivity.this, true);
          }
        });
    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {}
        });
    builder.create().show();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492898);
    getSupportActionBar().setTitle("Prestadores de serviços:");
    ((FloatingActionButton)findViewById(2131296357)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (ServicosActivity.this.autenticacao.getCurrentUser() != null) {
              ServicosActivity servicosActivity = ServicosActivity.this;
              servicosActivity.startActivity(new Intent(servicosActivity.getApplicationContext(), CadastrarServicoActivity.class));
              return;
            } 
            Toast.makeText((Context)ServicosActivity.this, "Faça login para cadastrar um serviço na lista de serviços", 0).show();
          }
        });
    this.autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    inicializarComponentes();
    this.servicosPublicosRef = ConfiguracaoFirebase.getFirebase().child("servicos");
    this.botaoVagas.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ServicosActivity servicosActivity = ServicosActivity.this;
            servicosActivity.startActivity(new Intent(servicosActivity.getApplicationContext(), VagasActivity.class));
          }
        });
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context)this);
    linearLayoutManager.setReverseLayout(false);
    linearLayoutManager.setStackFromEnd(false);
    this.recyclerServicosPublicos.setLayoutManager((RecyclerView.LayoutManager)linearLayoutManager);
    this.recyclerServicosPublicos.setHasFixedSize(true);
    this.adapterServicos = new AdapterServicos(this.listaServicos, (Context)this);
    this.recyclerServicosPublicos.setAdapter((RecyclerView.Adapter)this.adapterServicos);
    recuperarServicosPublicos();
    RecyclerView recyclerView = this.recyclerServicosPublicos;
    recyclerView.addOnItemTouchListener((RecyclerView.OnItemTouchListener)new RecyclerItemClickListener((Context)this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View param1View, int param1Int) {
              Servico servico = ServicosActivity.this.listaServicos.get(param1Int);
              Intent intent = new Intent((Context)ServicosActivity.this, DetalhesServicoActivity.class);
              intent.putExtra("servicoSelecionado", (Serializable)servico);
              ServicosActivity.this.startActivity(intent);
            }
            
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {}
            
            public void onLongItemClick(View param1View, int param1Int) {}
          }));
    this.searchView.setQueryHint("Pesquisar em serviços");
    this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          public boolean onQueryTextChange(final String newText) {
            if (ServicosActivity.this.filtrandoPorAtividade == true) {
              ServicosActivity.access$302(ServicosActivity.this, ConfiguracaoFirebase.getFirebase().child("servicos").child(ServicosActivity.this.filtroEstado).child(ServicosActivity.this.filtroAtividade));
              ServicosActivity.this.servicosPublicosRef.addValueEventListener(new ValueEventListener() {
                    public void onCancelled(DatabaseError param2DatabaseError) {
                      ServicosActivity.this.recuperarServicosPublicos();
                      Toast.makeText((Context)ServicosActivity.this, "Selecionar filtros novamente", 0).show();
                    }
                    
                    public void onDataChange(DataSnapshot param2DataSnapshot) {
                      ServicosActivity.this.listaServicos.clear();
                      Iterator<DataSnapshot> iterator = param2DataSnapshot.getChildren().iterator();
                      while (iterator.hasNext()) {
                        Servico servico = (Servico)((DataSnapshot)iterator.next()).getValue(Servico.class);
                        if (servico.getTitulo().toUpperCase().equals(newText.toUpperCase())) {
                          ServicosActivity.this.listaServicos.add(servico);
                          int i = ServicosActivity.this.listaServicos.size();
                          StringBuilder stringBuilder = new StringBuilder();
                          stringBuilder.append("total:    ");
                          stringBuilder.append(i);
                          Log.i("totalServicos", stringBuilder.toString());
                        } 
                      } 
                      Collections.reverse(ServicosActivity.this.listaServicos);
                      ServicosActivity.this.adapterServicos.notifyDataSetChanged();
                    }
                  });
              return true;
            } 
            ServicosActivity.access$302(ServicosActivity.this, ConfiguracaoFirebase.getFirebase().child("servicos"));
            ServicosActivity.this.servicosPublicosRef.addValueEventListener(new ValueEventListener() {
                  public void onCancelled(DatabaseError param2DatabaseError) {
                    ServicosActivity.this.recuperarServicosPublicos();
                  }
                  
                  public void onDataChange(DataSnapshot param2DataSnapshot) {
                    ServicosActivity.this.listaServicos.clear();
                    Iterator<DataSnapshot> iterator = param2DataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()) {
                      Iterator<DataSnapshot> iterator1 = ((DataSnapshot)iterator.next()).getChildren().iterator();
                      while (iterator1.hasNext()) {
                        Iterator<DataSnapshot> iterator2 = ((DataSnapshot)iterator1.next()).getChildren().iterator();
                        while (iterator2.hasNext()) {
                          Servico servico = (Servico)((DataSnapshot)iterator2.next()).getValue(Servico.class);
                          if (servico.getTitulo().toUpperCase().equals(newText.toUpperCase())) {
                            ServicosActivity.this.listaServicos.add(servico);
                            int i = ServicosActivity.this.listaServicos.size();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("total:    ");
                            stringBuilder.append(i);
                            Log.i("totalServicos", stringBuilder.toString());
                          } 
                        } 
                      } 
                    } 
                    Collections.reverse(ServicosActivity.this.listaServicos);
                    ServicosActivity.this.adapterServicos.notifyDataSetChanged();
                  }
                });
            return true;
          }
          
          public boolean onQueryTextSubmit(final String query) {
            if (ServicosActivity.this.filtrandoPorAtividade == true) {
              ServicosActivity.access$302(ServicosActivity.this, ConfiguracaoFirebase.getFirebase().child("servicos").child(ServicosActivity.this.filtroEstado).child(ServicosActivity.this.filtroAtividade));
              ServicosActivity.this.servicosPublicosRef.addValueEventListener(new ValueEventListener() {
                    public void onCancelled(DatabaseError param2DatabaseError) {
                      ServicosActivity.this.recuperarServicosPublicos();
                      Toast.makeText((Context)ServicosActivity.this, "Selecionar filtros novamente", 0).show();
                    }
                    
                    public void onDataChange(DataSnapshot param2DataSnapshot) {
                      ServicosActivity.this.listaServicos.clear();
                      Iterator<DataSnapshot> iterator = param2DataSnapshot.getChildren().iterator();
                      while (iterator.hasNext()) {
                        Servico servico = (Servico)((DataSnapshot)iterator.next()).getValue(Servico.class);
                        if (servico.getTitulo().toUpperCase().equals(query.toUpperCase())) {
                          ServicosActivity.this.listaServicos.add(servico);
                          int i = ServicosActivity.this.listaServicos.size();
                          StringBuilder stringBuilder = new StringBuilder();
                          stringBuilder.append("total:    ");
                          stringBuilder.append(i);
                          Log.i("totalServicos", stringBuilder.toString());
                        } 
                      } 
                      Collections.reverse(ServicosActivity.this.listaServicos);
                      ServicosActivity.this.adapterServicos.notifyDataSetChanged();
                    }
                  });
              return true;
            } 
            ServicosActivity.access$302(ServicosActivity.this, ConfiguracaoFirebase.getFirebase().child("servicos"));
            ServicosActivity.this.servicosPublicosRef.addValueEventListener(new ValueEventListener() {
                  public void onCancelled(DatabaseError param2DatabaseError) {
                    ServicosActivity.this.recuperarServicosPublicos();
                  }
                  
                  public void onDataChange(DataSnapshot param2DataSnapshot) {
                    ServicosActivity.this.listaServicos.clear();
                    Iterator<DataSnapshot> iterator = param2DataSnapshot.getChildren().iterator();
                    while (iterator.hasNext()) {
                      Iterator<DataSnapshot> iterator1 = ((DataSnapshot)iterator.next()).getChildren().iterator();
                      while (iterator1.hasNext()) {
                        Iterator<DataSnapshot> iterator2 = ((DataSnapshot)iterator1.next()).getChildren().iterator();
                        while (iterator2.hasNext()) {
                          Servico servico = (Servico)((DataSnapshot)iterator2.next()).getValue(Servico.class);
                          if (servico.getTitulo().toUpperCase().equals(query.toUpperCase())) {
                            ServicosActivity.this.listaServicos.add(servico);
                            int i = ServicosActivity.this.listaServicos.size();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("total:    ");
                            stringBuilder.append(i);
                            Log.i("totalServicos", stringBuilder.toString());
                          } 
                        } 
                      } 
                    } 
                    Collections.reverse(ServicosActivity.this.listaServicos);
                    ServicosActivity.this.adapterServicos.notifyDataSetChanged();
                  }
                });
            return true;
          }
        });
    this.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
          public boolean onClose() {
            ServicosActivity.this.recuperarServicosPublicos();
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
  
  public void recuperarServicosPorAtividade() {
    this.dialog = (new SpotsDialog.Builder()).setContext((Context)this).setMessage("Filtrando serviços por atividade..").setCancelable(false).build();
    this.dialog.show();
    this.servicosPublicosRef = ConfiguracaoFirebase.getFirebase().child("servicos").child(this.filtroEstado).child(this.filtroAtividade);
    this.servicosPublicosRef.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            ServicosActivity.this.listaServicos.clear();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
              Servico servico = (Servico)((DataSnapshot)iterator.next()).getValue(Servico.class);
              ServicosActivity.this.listaServicos.add(servico);
            } 
            Collections.reverse(ServicosActivity.this.listaServicos);
            ServicosActivity.this.adapterServicos.notifyDataSetChanged();
            ServicosActivity.this.dialog.dismiss();
          }
        });
  }
  
  public void recuperarServicosPorEstado() {
    this.dialog = (new SpotsDialog.Builder()).setContext((Context)this).setMessage("Filtrando serviços por estado..").setCancelable(false).build();
    this.dialog.show();
    this.servicosPublicosRef = ConfiguracaoFirebase.getFirebase().child("servicos").child(this.filtroEstado);
    this.servicosPublicosRef.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            ServicosActivity.this.listaServicos.clear();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
              Iterator<DataSnapshot> iterator1 = ((DataSnapshot)iterator.next()).getChildren().iterator();
              while (iterator1.hasNext()) {
                Servico servico = (Servico)((DataSnapshot)iterator1.next()).getValue(Servico.class);
                ServicosActivity.this.listaServicos.add(servico);
              } 
            } 
            Collections.reverse(ServicosActivity.this.listaServicos);
            ServicosActivity.this.adapterServicos.notifyDataSetChanged();
            ServicosActivity.this.dialog.dismiss();
          }
        });
  }
  
  public void recuperarServicosPublicos() {
    this.dialog = (new SpotsDialog.Builder()).setContext((Context)this).setMessage("Carregando serviços").setCancelable(false).build();
    this.dialog.show();
    this.servicosPublicosRef = ConfiguracaoFirebase.getFirebase().child("servicos");
    this.servicosPublicosRef.addValueEventListener(new ValueEventListener() {
          public void onCancelled(DatabaseError param1DatabaseError) {}
          
          public void onDataChange(DataSnapshot param1DataSnapshot) {
            ServicosActivity.this.listaServicos.clear();
            Iterator<DataSnapshot> iterator = param1DataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
              Iterator<DataSnapshot> iterator1 = ((DataSnapshot)iterator.next()).getChildren().iterator();
              while (iterator1.hasNext()) {
                Iterator<DataSnapshot> iterator2 = ((DataSnapshot)iterator1.next()).getChildren().iterator();
                while (iterator2.hasNext()) {
                  Servico servico = (Servico)((DataSnapshot)iterator2.next()).getValue(Servico.class);
                  ServicosActivity.this.listaServicos.add(servico);
                } 
              } 
            } 
            Collections.reverse(ServicosActivity.this.listaServicos);
            ServicosActivity.this.adapterServicos.notifyDataSetChanged();
            ServicosActivity.this.dialog.dismiss();
          }
        });
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\activity\ServicosActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */