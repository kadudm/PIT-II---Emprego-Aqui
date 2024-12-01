package com.manzoli.quati.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.manzoli.quati.helper.ConfiguracaoFirebase;

public class CadastroActivity extends AppCompatActivity {
  private FirebaseAuth autenticacao;
  
  private Button botaoAcessar;
  
  private EditText campoEmail;
  
  private EditText campoSenha;
  
  private Switch tipoAcesso;
  
  private void inicializarComponentes() {
    this.campoEmail = (EditText)findViewById(2131296430);
    this.campoSenha = (EditText)findViewById(2131296431);
    this.tipoAcesso = (Switch)findViewById(2131296651);
    this.botaoAcessar = (Button)findViewById(2131296355);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492894);
    getSupportActionBar().setTitle("Faça login ou seu cadastro:");
    inicializarComponentes();
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setMessage("Bem vindo ao Emprego aqui\n\n Bom, uma rápida introdução:\n 1- Na primera página estão os anúncios dos trabalhadores disponíveis\n 2- E na segunda página estão os anúncios das vagas de trabalho disponíveis\n 3- Você pode criar anúcios de serviços e vagas de trabalho em suas respectivas páginas\n\n Faça bom proveito, espero que essa ferramenta seja útil... \nObrigado :) ");
    builder.setPositiveButton("Contato para mais informações e ajuda", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)CadastroActivity.this);
            builder.setMessage("WhatsApp e ligaçõs: \n(61) 996317992\nEmail:\n empregoaq@gmail.com");
            builder.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                });
            builder.setCancelable(true);
            builder.show();
          }
        });
    builder.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {}
        });
    builder.setCancelable(true);
    builder.show();
    this.autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    this.botaoAcessar.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            String str1 = CadastroActivity.this.campoEmail.getText().toString();
            String str2 = CadastroActivity.this.campoSenha.getText().toString();
            if (!str1.isEmpty()) {
              if (!str2.isEmpty()) {
                if (CadastroActivity.this.tipoAcesso.isChecked()) {
                  CadastroActivity.this.autenticacao.createUserWithEmailAndPassword(str1, str2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> param2Task) {
                          String str;
                          if (param2Task.isSuccessful()) {
                            Toast.makeText((Context)CadastroActivity.this, "Cadastro realizado com sucesso", 0).show();
                            return;
                          } 
                          try {
                            throw param2Task.getException();
                          } catch (FirebaseAuthWeakPasswordException firebaseAuthWeakPasswordException) {
                            str = "Digite uma senha mais forte";
                          } catch (FirebaseAuthInvalidCredentialsException firebaseAuthInvalidCredentialsException) {
                            str = "Digite um E-mail válido!";
                          } catch (FirebaseAuthUserCollisionException firebaseAuthUserCollisionException) {
                            str = "Essa conta já foi cadastrada";
                          } catch (Exception exception) {
                            StringBuilder stringBuilder1 = new StringBuilder();
                            stringBuilder1.append("ao cadastrar o usuário: ");
                            stringBuilder1.append(exception.getMessage());
                            str = stringBuilder1.toString();
                            exception.printStackTrace();
                          } 
                          CadastroActivity cadastroActivity = CadastroActivity.this;
                          StringBuilder stringBuilder = new StringBuilder();
                          stringBuilder.append("Erro: ");
                          stringBuilder.append(str);
                          Toast.makeText((Context)cadastroActivity, stringBuilder.toString(), 0).show();
                        }
                      });
                  return;
                } 
                CadastroActivity.this.autenticacao.signInWithEmailAndPassword(str1, str2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      public void onComplete(Task<AuthResult> param2Task) {
                        if (param2Task.isSuccessful()) {
                          Toast.makeText((Context)CadastroActivity.this, "Logado com sucesso!", 0).show();
                          CadastroActivity.this.startActivity(new Intent(CadastroActivity.this.getApplicationContext(), ServicosActivity.class));
                          return;
                        } 
                        CadastroActivity cadastroActivity = CadastroActivity.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Erro ao fazer o login: ");
                        stringBuilder.append(param2Task.getException());
                        Toast.makeText((Context)cadastroActivity, stringBuilder.toString(), 0).show();
                      }
                    });
                return;
              } 
              Toast.makeText((Context)CadastroActivity.this, "Preencha a senha!", 0);
              return;
            } 
            Toast.makeText((Context)CadastroActivity.this, "Preencha o E-mail!", 0);
          }
        });
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\activity\CadastroActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */