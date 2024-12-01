package com.manzoli.quati.model;

import com.manzoli.quati.helper.ConfiguracaoFirebase;
import java.io.Serializable;
import java.util.List;

public class Vaga implements Serializable {
  private String categoria;
  
  private String descricao;
  
  private String estado;
  
  private List<String> fotos;
  
  private String idVaga;
  
  private String telefone;
  
  private String titulo;
  
  private String valor;
  
  public Vaga() {
    setIdVaga(ConfiguracaoFirebase.getFirebase().child("minhas_vagas").push().getKey());
  }
  
  public String getCategoria() {
    return this.categoria;
  }
  
  public String getDescricao() {
    return this.descricao;
  }
  
  public String getEstado() {
    return this.estado;
  }
  
  public List<String> getFotos() {
    return this.fotos;
  }
  
  public String getIdVaga() {
    return this.idVaga;
  }
  
  public String getTelefone() {
    return this.telefone;
  }
  
  public String getTitulo() {
    return this.titulo;
  }
  
  public String getValor() {
    return this.valor;
  }
  
  public void removerVaga() {
    String str = ConfiguracaoFirebase.getIdUsuario();
    ConfiguracaoFirebase.getFirebase().child("minhas_vagas").child(str).child(getIdVaga()).removeValue();
    removerVagaPublica();
  }
  
  public void removerVagaPublica() {
    ConfiguracaoFirebase.getFirebase().child("vagas").child(getEstado()).child(getCategoria()).child(getIdVaga()).removeValue();
  }
  
  public void salvar() {
    String str = ConfiguracaoFirebase.getIdUsuario();
    ConfiguracaoFirebase.getFirebase().child("minhas_vagas").child(str).child(getIdVaga()).setValue(this);
    salvarVagasPublicas();
  }
  
  public void salvarVagasPublicas() {
    ConfiguracaoFirebase.getFirebase().child("vagas").child(getEstado()).child(getCategoria()).child(getIdVaga()).setValue(this);
  }
  
  public void setCategoria(String paramString) {
    this.categoria = paramString;
  }
  
  public void setDescricao(String paramString) {
    this.descricao = paramString;
  }
  
  public void setEstado(String paramString) {
    this.estado = paramString;
  }
  
  public void setFotos(List<String> paramList) {
    this.fotos = paramList;
  }
  
  public void setIdVaga(String paramString) {
    this.idVaga = paramString;
  }
  
  public void setTelefone(String paramString) {
    this.telefone = paramString;
  }
  
  public void setTitulo(String paramString) {
    this.titulo = paramString;
  }
  
  public void setValor(String paramString) {
    this.valor = paramString;
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\model\Vaga.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */