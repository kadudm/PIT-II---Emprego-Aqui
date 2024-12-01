package com.manzoli.quati.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {
  private static FirebaseDatabase pesquisa;
  
  private static FirebaseAuth referenciaAutenticacao;
  
  private static DatabaseReference referenciaFirebase;
  
  private static StorageReference referenciaStorage;
  
  public static DatabaseReference getFirebase() {
    if (referenciaFirebase == null)
      referenciaFirebase = FirebaseDatabase.getInstance().getReference(); 
    return referenciaFirebase;
  }
  
  public static FirebaseAuth getFirebaseAutenticacao() {
    if (referenciaAutenticacao == null)
      referenciaAutenticacao = FirebaseAuth.getInstance(); 
    return referenciaAutenticacao;
  }
  
  public static StorageReference getFirebaseStorage() {
    if (referenciaStorage == null)
      referenciaStorage = FirebaseStorage.getInstance().getReference(); 
    return referenciaStorage;
  }
  
  public static String getIdUsuario() {
    return getFirebaseAutenticacao().getCurrentUser().getUid();
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\helper\ConfiguracaoFirebase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */