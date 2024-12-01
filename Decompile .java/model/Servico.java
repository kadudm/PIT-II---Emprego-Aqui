package com.manzoli.quati.model;

import com.manzoli.quati.helper.ConfiguracaoFirebase;
import java.io.Serializable;
import java.util.List;

public class Servico implements Serializable {
  private String categoria;
  
  private String descricao;
  
  private String estado;
  
  private List<String> fotos;
  
  private String idServico;
  
  private String telefone;
  
  private String titulo;
  
  private String valor;
  
  public Servico() {
    setIdServico(ConfiguracaoFirebase.getFirebase().child("meus_servicos").push().getKey());
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
  
  public String getIdServico() {
    return this.idServico;
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
  
  public void removerServico() {
    String str = ConfiguracaoFirebase.getIdUsuario();
    ConfiguracaoFirebase.getFirebase().child("meus_servicos").child(str).child(getIdServico()).removeValue();
    removerServicoPublico();
  }
  
  public void removerServicoPublico() {
    ConfiguracaoFirebase.getFirebase().child("servicos").child(getEstado()).child(getCategoria()).child(getIdServico()).removeValue();
  }
  
  public void salvar() {
    String str = ConfiguracaoFirebase.getIdUsuario();
    ConfiguracaoFirebase.getFirebase().child("meus_servicos").child(str).child(getIdServico()).setValue(this);
    salvarServicosPublicos();
  }
  
  public void salvarServicosPublicos() {
    ConfiguracaoFirebase.getFirebase().child("servicos").child(getEstado()).child(getCategoria()).child(getIdServico()).setValue(this);
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
  
  public void setIdServico(String paramString) {
    this.idServico = paramString;
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


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\model\Servico.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */