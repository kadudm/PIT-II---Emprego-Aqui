package com.manzoli.quati.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.manzoli.quati.helper.Permissoes;
import com.manzoli.quati.model.Vaga;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class DetalhesVagaActivity extends AppCompatActivity {
  private CarouselView carouselView;
  
  private TextView descricao;
  
  private TextView estado;
  
  private String[] permissoes = new String[] { "android.permission.CALL_PHONE" };
  
  private TextView telefone;
  
  private TextView titulo;
  
  private Vaga vagaSelecionada;
  
  private TextView valor;
  
  private void alertaValidacaoPermissaoServico() {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setTitle("Permissões negadas");
    builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
    builder.setCancelable(false);
    builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            DetalhesVagaActivity.this.finish();
          }
        });
    builder.create().show();
  }
  
  private void inicializarComponenetes() {
    this.carouselView = (CarouselView)findViewById(2131296369);
    this.titulo = (TextView)findViewById(2131296678);
    this.descricao = (TextView)findViewById(2131296668);
    this.estado = (TextView)findViewById(2131296671);
    this.valor = (TextView)findViewById(2131296682);
    this.telefone = (TextView)findViewById(2131296676);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492896);
    getSupportActionBar().setTitle("Detalhes da vaga");
    Permissoes.validarPermissoes(this.permissoes, (Activity)this, 1);
    inicializarComponenetes();
    this.vagaSelecionada = (Vaga)getIntent().getSerializableExtra("vagaSelecionada");
    Vaga vaga = this.vagaSelecionada;
    if (vaga != null) {
      this.titulo.setText(vaga.getTitulo());
      this.descricao.setText(this.vagaSelecionada.getDescricao());
      this.estado.setText(this.vagaSelecionada.getEstado());
      this.valor.setText(this.vagaSelecionada.getValor());
      this.telefone.setText(this.vagaSelecionada.getTelefone());
      ImageListener imageListener = new ImageListener() {
          public void setImageForPosition(int param1Int, ImageView param1ImageView) {
            String str = DetalhesVagaActivity.this.vagaSelecionada.getFotos().get(param1Int);
            Picasso.get().load(str).into(param1ImageView);
          }
        };
      this.carouselView.setPageCount(this.vagaSelecionada.getFotos().size());
      this.carouselView.setImageListener(imageListener);
    } 
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    int i = paramArrayOfint.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      if (paramArrayOfint[paramInt] == -1)
        alertaValidacaoPermissaoServico(); 
    } 
  }
  
  public void visualizarTelefoneVaga(View paramView) {
    startActivity(new Intent("android.intent.action.DIAL", Uri.fromParts("tel", this.vagaSelecionada.getTelefone(), null)));
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\activity\DetalhesVagaActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */