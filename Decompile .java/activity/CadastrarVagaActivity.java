package com.manzoli.quati.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.manzoli.quati.helper.ConfiguracaoFirebase;
import com.manzoli.quati.helper.Permissoes;
import com.manzoli.quati.model.Vaga;
import com.santalu.maskara.widget.MaskEditText;
import dmax.dialog.SpotsDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CadastrarVagaActivity extends AppCompatActivity implements View.OnClickListener {
  private Spinner campoCategoriaVaga;
  
  private EditText campoDescricaoVaga;
  
  private Spinner campoEstadoVaga;
  
  private MaskEditText campoTelefoneVaga;
  
  private EditText campoTituloVaga;
  
  private CurrencyEditText campoValorVaga;
  
  private AlertDialog dialog;
  
  private ImageView imagemVaga1;
  
  private ImageView imagemVaga2;
  
  private ImageView imagemVaga3;
  
  private List<String> listaFotosRecuperadasVaga = new ArrayList<String>();
  
  private List<String> listaUrlFotos = new ArrayList<String>();
  
  private String[] permissoesVaga = new String[] { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA" };
  
  private StorageReference storage;
  
  private Vaga vaga;
  
  private void alertaValidacaoPermissaoVaga() {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setTitle("Permissões negadas");
    builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
    builder.setCancelable(false);
    builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            CadastrarVagaActivity.this.finish();
          }
        });
    builder.create().show();
  }
  
  private void carregarDadosSpinner() {
    ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367048, (Object[])getResources().getStringArray(2130903042));
    arrayAdapter.setDropDownViewResource(17367049);
    this.campoEstadoVaga.setAdapter((SpinnerAdapter)arrayAdapter);
    arrayAdapter = new ArrayAdapter((Context)this, 17367048, (Object[])getResources().getStringArray(2130903040));
    arrayAdapter.setDropDownViewResource(17367049);
    this.campoCategoriaVaga.setAdapter((SpinnerAdapter)arrayAdapter);
  }
  
  private Vaga configurarVaga() {
    String str1 = this.campoEstadoVaga.getSelectedItem().toString();
    String str2 = this.campoCategoriaVaga.getSelectedItem().toString();
    String str3 = this.campoTituloVaga.getText().toString();
    String str4 = this.campoValorVaga.getText().toString();
    String str5 = this.campoTelefoneVaga.getText().toString();
    String str6 = this.campoDescricaoVaga.getText().toString();
    Vaga vaga = new Vaga();
    vaga.setEstado(str1);
    vaga.setCategoria(str2);
    vaga.setTitulo(str3);
    vaga.setValor(str4);
    vaga.setTelefone(str5);
    vaga.setDescricao(str6);
    return vaga;
  }
  
  private void exibirMensagemErro(String paramString) {
    Toast.makeText((Context)this, paramString, 0).show();
  }
  
  private void inicializarComponentes() {
    this.campoTituloVaga = (EditText)findViewById(2131296437);
    this.campoDescricaoVaga = (EditText)findViewById(2131296433);
    this.campoValorVaga = (CurrencyEditText)findViewById(2131296439);
    this.campoTelefoneVaga = (MaskEditText)findViewById(2131296435);
    this.campoEstadoVaga = (Spinner)findViewById(2131296628);
    this.campoCategoriaVaga = (Spinner)findViewById(2131296626);
    this.imagemVaga1 = (ImageView)findViewById(2131296484);
    this.imagemVaga2 = (ImageView)findViewById(2131296485);
    this.imagemVaga3 = (ImageView)findViewById(2131296486);
    this.imagemVaga1.setOnClickListener(this);
    this.imagemVaga2.setOnClickListener(this);
    this.imagemVaga3.setOnClickListener(this);
    Locale locale = new Locale("pt", "BR");
    this.campoValorVaga.setLocale(locale);
  }
  
  private void salvarFotosStorage(String paramString, final int totalFotos, int paramInt2) {
    final StorageReference imagemVaga = this.storage.child("imagens_vaga").child("vagas").child(this.vaga.getIdVaga());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("imagem_vaga ");
    stringBuilder.append(paramInt2);
    storageReference = storageReference.child(stringBuilder.toString());
    storageReference.putFile(Uri.parse(paramString)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          public void onSuccess(UploadTask.TaskSnapshot param1TaskSnapshot) {
            imagemVaga.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                  public void onComplete(Task<Uri> param2Task) {
                    String str = ((Uri)param2Task.getResult()).toString();
                    CadastrarVagaActivity.this.listaUrlFotos.add(str);
                    if (totalFotos == CadastrarVagaActivity.this.listaUrlFotos.size()) {
                      CadastrarVagaActivity.this.vaga.setFotos(CadastrarVagaActivity.this.listaUrlFotos);
                      CadastrarVagaActivity.this.vaga.salvar();
                      CadastrarVagaActivity.this.dialog.dismiss();
                      CadastrarVagaActivity.this.finish();
                    } 
                  }
                });
          }
        }).addOnFailureListener(new OnFailureListener() {
          public void onFailure(Exception param1Exception) {
            CadastrarVagaActivity.this.exibirMensagemErro("Falha ao fazer o upload da imagem");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Falha ao fazer o upload: ");
            stringBuilder.append(param1Exception.getMessage());
            Log.i("INFO", stringBuilder.toString());
          }
        });
  }
  
  public void escolherImagemVaga(int paramInt) {
    startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), paramInt);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1) {
      Uri uri = paramIntent.getData();
      String str = uri.toString();
      if (paramInt1 == 1) {
        this.imagemVaga1.setImageURI(uri);
      } else if (paramInt1 == 2) {
        this.imagemVaga2.setImageURI(uri);
      } else if (paramInt1 == 3) {
        this.imagemVaga3.setImageURI(uri);
      } 
      this.listaFotosRecuperadasVaga.add(str);
    } 
  }
  
  public void onClick(View paramView) {
    switch (paramView.getId()) {
      default:
        return;
      case 2131296486:
        escolherImagemVaga(3);
        return;
      case 2131296485:
        escolherImagemVaga(2);
        return;
      case 2131296484:
        break;
    } 
    escolherImagemVaga(1);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492893);
    getSupportActionBar().setTitle("Cadastre sua vaga:");
    this.storage = ConfiguracaoFirebase.getFirebaseStorage();
    Permissoes.validarPermissoes(this.permissoesVaga, (Activity)this, 1);
    inicializarComponentes();
    carregarDadosSpinner();
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    int i = paramArrayOfint.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      if (paramArrayOfint[paramInt] == -1)
        alertaValidacaoPermissaoVaga(); 
    } 
  }
  
  public void salvarVaga() {
    SpotsDialog.Builder builder = (new SpotsDialog.Builder()).setContext((Context)this).setMessage("Salvando vaga");
    int i = 0;
    this.dialog = builder.setCancelable(false).build();
    this.dialog.show();
    while (i < this.listaFotosRecuperadasVaga.size()) {
      salvarFotosStorage(this.listaFotosRecuperadasVaga.get(i), this.listaFotosRecuperadasVaga.size(), i);
      i++;
    } 
  }
  
  public void validarDadosVaga(View paramView) {
    this.vaga = configurarVaga();
    long l = this.campoValorVaga.getRawValue();
    if (this.listaFotosRecuperadasVaga.size() != 0) {
      if (!this.vaga.getEstado().isEmpty()) {
        if (!this.vaga.getCategoria().isEmpty()) {
          if (!this.vaga.getTitulo().isEmpty()) {
            if (!String.valueOf(l).isEmpty()) {
              if (!this.vaga.getTelefone().isEmpty()) {
                if (!this.vaga.getDescricao().isEmpty()) {
                  salvarVaga();
                  return;
                } 
                exibirMensagemErro("Preencha a descrição!");
                return;
              } 
              exibirMensagemErro("Preencha o telefone!");
              return;
            } 
            exibirMensagemErro("Preencha o valor!");
            return;
          } 
          exibirMensagemErro("Preencha o título!");
          return;
        } 
        exibirMensagemErro("Preencha a categoria!");
        return;
      } 
      exibirMensagemErro("Preencha o estado!");
      return;
    } 
    exibirMensagemErro("Selecione no mínimo uma foto!");
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\activity\CadastrarVagaActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */