package com.manzoli.quati.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.manzoli.quati.model.Servico;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterServicos extends RecyclerView.Adapter<AdapterServicos.MyViewHolder> {
  private Context context;
  
  private List<Servico> servicos;
  
  public AdapterServicos(List<Servico> paramList, Context paramContext) {
    this.servicos = paramList;
    this.context = paramContext;
  }
  
  public int getItemCount() {
    return this.servicos.size();
  }
  
  public void onBindViewHolder(MyViewHolder paramMyViewHolder, int paramInt) {
    Servico servico = this.servicos.get(paramInt);
    paramMyViewHolder.titulo.setText(servico.getTitulo());
    paramMyViewHolder.valor.setText(servico.getValor());
    String str = servico.getFotos().get(0);
    Picasso.get().load(str).into(paramMyViewHolder.foto);
  }
  
  public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    return new MyViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(2131492900, paramViewGroup, false));
  }
  
  public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView foto;
    
    TextView titulo;
    
    TextView valor;
    
    public MyViewHolder(View param1View) {
      super(param1View);
      this.titulo = (TextView)param1View.findViewById(2131296679);
      this.valor = (TextView)param1View.findViewById(2131296683);
      this.foto = (ImageView)param1View.findViewById(2131296487);
    }
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\adapter\AdapterServicos.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */