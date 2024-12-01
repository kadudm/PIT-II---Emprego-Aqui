package com.manzoli.quati.helper;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
  GestureDetector mGestureDetector;
  
  private OnItemClickListener mListener;
  
  public RecyclerItemClickListener(Context paramContext, final RecyclerView recyclerView, OnItemClickListener paramOnItemClickListener) {
    this.mListener = paramOnItemClickListener;
    this.mGestureDetector = new GestureDetector(paramContext, (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener() {
          public void onLongPress(MotionEvent param1MotionEvent) {
            View view = recyclerView.findChildViewUnder(param1MotionEvent.getX(), param1MotionEvent.getY());
            if (view != null && RecyclerItemClickListener.this.mListener != null)
              RecyclerItemClickListener.this.mListener.onLongItemClick(view, recyclerView.getChildAdapterPosition(view)); 
          }
          
          public boolean onSingleTapUp(MotionEvent param1MotionEvent) {
            return true;
          }
        });
  }
  
  public boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent) {
    View view = paramRecyclerView.findChildViewUnder(paramMotionEvent.getX(), paramMotionEvent.getY());
    if (view != null && this.mListener != null && this.mGestureDetector.onTouchEvent(paramMotionEvent)) {
      this.mListener.onItemClick(view, paramRecyclerView.getChildAdapterPosition(view));
      return true;
    } 
    return false;
  }
  
  public void onRequestDisallowInterceptTouchEvent(boolean paramBoolean) {}
  
  public void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent) {}
  
  public static interface OnItemClickListener extends AdapterView.OnItemClickListener {
    void onItemClick(View param1View, int param1Int);
    
    void onLongItemClick(View param1View, int param1Int);
  }
}


/* Location:              C:\Users\Manzoli\Desktop\Projetos\classes-dex2jar.jar!\com\manzoli\quati\helper\RecyclerItemClickListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */