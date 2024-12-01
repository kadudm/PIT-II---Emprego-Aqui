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
import com.manzoli.quati.model.Servico;
import com.santalu.maskara.widget.MaskEditText;
import dmax.dialog.SpotsDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CadastrarServicoActivity extends AppCompatActivity implements View.OnClickListener {
  private Spinner campoCategoriaServico;
  
  private EditText campoDescricaoServico;
  
  private Spinner campoEstadoServico;
  
  private MaskEditText campoTelefoneServico;
  
  private EditText campoTituloServico;
  
  private CurrencyEditText campoValorServico;
  
  private AlertDialog dialog;
  
  private ImageView imagemServico1;
  
  private ImageView imagemServico2;
  
  private ImageView imagemServico3;
  
  private List<String> listaFotosRecuperadasServico = new ArrayList<String>();
  
  private List<String> listaUrlFotos = new ArrayList<String>();
  
  private String[] permissoesServico = new String[] { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA" };
  
  private Servico servico;
  
  private StorageReference storage;
  
  private void alertaValidacaoPermissaoServico() {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setTitle("Permissões negadas");
    builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
    builder.setCancelable(false);
    builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            CadastrarServicoActivity.this.finish();
          }
        });
    builder.create().show();
  }
  
  private void carregarDadosSpinner() {
    ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367048, (Object[])getResources().getStringArray(2130903042));
    arrayAdapter.setDropDownViewResource(17367049);
    this.campoEstadoServico.setAdapter((SpinnerAdapter)arrayAdapter);
    arrayAdapter = new ArrayAdapter((Context)this, 17367048, (Object[])getResources().getStringArray(2130903041));
    arrayAdapter.setDropDownViewResource(17367049);
    this.campoCategoriaServico.setAdapter((SpinnerAdapter)arrayAdapter);
  }
  
  private Servico configurarServico() {
    String str1 = this.campoEstadoServico.getSelectedItem().toString();
    String str2 = this.campoCategoriaServico.getSelectedItem().toString();
    String str3 = this.campoTituloServico.getText().toString();
    String str4 = this.campoValorServico.getText().toString();
    String str5 = this.campoTelefoneServico.getText().toString();
    String str6 = this.campoDescricaoServico.getText().toString();
    Servico servico = new Servico();
    servico.setEstado(str1);
    servico.setCategoria(str2);
    servico.setTitulo(str3);
    servico.setValor(str4);
    servico.setTelefone(str5);
    servico.setDescricao(str6);
    return servico;
  }
  
  private void exibirMensagemErro(String paramString) {
    Toast.makeText((Context)this, paramString, 0).show();
  }
  
  private void inicializarComponentes() {
    this.campoTituloServico = (EditText)findViewById(2131296436);
    this.campoDescricaoServico = (EditText)findViewById(2131296432);
    this.campoValorServico = (CurrencyEditText)findViewById(2131296438);
    this.campoTelefoneServico = (MaskEditText)findViewById(2131296434);
    this.campoEstadoServico = (Spinner)findViewById(2131296627);
    this.campoCategoriaServico = (Spinner)findViewById(2131296625);
    this.imagemServico1 = (ImageView)findViewById(2131296481);
    this.imagemServico2 = (ImageView)findViewById(2131296482);
    this.imagemServico3 = (ImageView)findViewById(2131296483);
    this.imagemServico1.setOnClickListener(this);
    this.imagemServico2.setOnClickListener(this);
    this.imagemServico3.setOnClickListener(this);
    Locale locale = new Locale("pt", "BR");
    this.campoValorServico.setLocale(locale);
  }
  
  private void salvarFotosStorage(String paramString, final int totalFotos, int paramInt2) {
    final StorageReference imagemServico = this.storage.child("imagens_servico").child("servicos").child(this.servico.getIdServico());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("imagem_servico ");
    stringBuilder.append(paramInt2);
    storageReference = storageReference.child(stringBuilder.toString());
    storageReference.putFile(Uri.parse(paramString)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          public void onSuccess(UploadTask.TaskSnapshot param1TaskSnapshot) {
            imagemServico.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                  public void onComplete(Task<Uri> param2Task) {
                    String str = ((Uri)param2Task.getResult()).toString();
                    CadastrarServicoActivity.this.listaUrlFotos.add(str);
                    if (totalFotos == CadastrarServicoActivity.this.listaUrlFotos.size()) {
                      CadastrarServicoActivity.this.servico.setFotos(CadastrarServicoActivity.this.listaUrlFotos);
                      CadastrarServicoActivity.this.servico.salvar();
                      CadastrarServicoActivity.this.dialog.dismiss();
                      CadastrarServicoActivity.this.finish();
                    } 
                  }
                });
          }
        }).addOnFailureListener(new OnFailureListener() {
          public void onFailure(Exception param1Exception) {
            CadastrarServicoActivity.this.exibirMensagemErro("Falha ao fazer o upload da imagem");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Falha ao fazer o upload: ");
            stringBuilder.append(param1Exception.getMessage());
            Log.i("INFO", stringBuilder.toString());
          }
        });
  }
  
  public void escolherImagemServico(int paramInt) {
    startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), paramInt);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1) {
      Uri uri = paramIntent.getData();
      String str = uri.toString();
      if (paramInt1 == 1) {
        this.imagemServico1.setImageURI(uri);
      } else if (paramInt1 == 2) {
        this.imagemServico2.setImageURI(uri);
      } else if (paramInt1 == 3) {
        this.imagemServico3.setImageURI(uri);
      } 
      this.listaFotosRecuperadasServico.add(str);
    } 
  }
  
  public void onClick(View paramView) {
    switch (paramView.getId()) {
      default:
        return;
      case 2131296483:
        escolherImagemServico(3);
        return;
      case 2131296482:
        escolherImagemServico(2);
        return;
      case 2131296481:
        break;
    } 
    escolherImagemServico(1);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492892);
    getSupportActionBar().setTitle("Cadastre seu serviço: ");
    this.storage = ConfiguracaoFirebase.getFirebaseStorage();
    Permissoes.validarPermissoes(this.permissoesServico, (Activity)this, 1);
    inicializarComponentes();
    carregarDadosSpinner();
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    int i = paramArrayOfint.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      if (paramArrayOfint[paramInt] == -1)
        alertaValidacaoPermissaoServico(); 
    } 
  }
  
  public void salvarServico() {
    SpotsDialog.Builder builder = (new SpotsDialog.Builder()).setContext((Context)this).setMessage("Salvando serviço");
    int i = 0;
    this.dialog = builder.setCancelable(false).build();
    this.dialog.show();
    while (i < this.listaFotosRecuperadasServico.size()) {
      salvarFotosStorage(this.listaFotosRecuperadasServico.get(i), this.listaFotosRecuperadasServico.size(), i);
      i++;
    } 
  }
  
  public void validarDadosServico(View paramView) {
    this.servico = configurarServico();
    long l = this.campoValorServico.getRawValue();
    if (this.listaFotosRecuperadasServico.size() != 0) {
      if (!this.servico.getEstado().isEmpty()) {
        if (!this.servico.getCategoria().isEmpty()) {
          if (!this.servico.getTitulo().isEmpty()) {
            if (!String.valueOf(l).isEmpty()) {
              if (!this.servico.getTelefone().isEmpty()) {
                if (!this.servico.getDescricao().isEmpty()) {
                  salvarServico();
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
    exibirMensagemErro("Adicione pelo menos uma foto ao anúncio!");
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\activity\CadastrarServicoActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */