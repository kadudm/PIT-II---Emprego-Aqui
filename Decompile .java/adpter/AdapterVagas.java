package com.manzoli.quati.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.manzoli.quati.model.Vaga;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterVagas extends RecyclerView.Adapter<AdapterVagas.MyViewHolder> {
  private Context context;
  
  private List<Vaga> vagas;
  
  public AdapterVagas(List<Vaga> paramList, Context paramContext) {
    this.vagas = paramList;
    this.context = paramContext;
  }
  
  public int getItemCount() {
    return this.vagas.size();
  }
  
  public void onBindViewHolder(MyViewHolder paramMyViewHolder, int paramInt) {
    Vaga vaga = this.vagas.get(paramInt);
    paramMyViewHolder.titulo.setText(vaga.getTitulo());
    paramMyViewHolder.valor.setText(vaga.getValor());
    String str = vaga.getFotos().get(0);
    Picasso.get().load(str).into(paramMyViewHolder.foto);
  }
  
  public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    return new MyViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(2131492901, paramViewGroup, false));
  }
  
  public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView foto;
    
    TextView titulo;
    
    TextView valor;
    
    public MyViewHolder(View param1View) {
      super(param1View);
      this.titulo = (TextView)param1View.findViewById(2131296680);
      this.valor = (TextView)param1View.findViewById(2131296684);
      this.foto = (ImageView)param1View.findViewById(2131296488);
    }
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\adapter\AdapterVagas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */