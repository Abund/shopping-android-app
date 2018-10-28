package com.example.abun.shoprite1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Abun on 7/25/2018.
 */

public class adminAdapter extends RecyclerView.Adapter<adminAdapter.ViewHolder>  {
    private ArrayList<customerTable> Data;
    private Context ct1;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public Button editty;
        public Button delety;
        public TextView productN;
        public ImageView img;
        public View layout;
        public CardView card;

        public ViewHolder(View itemView){
            super(itemView);
            layout = itemView;
            productN  = (TextView) itemView.findViewById(R.id.productN1);
            editty = (Button) itemView.findViewById(R.id.productN11);
            delety  = (Button) itemView.findViewById(R.id.productN111);
            img = (ImageView) itemView.findViewById(R.id.imaging1);
            card = (CardView) itemView.findViewById(R.id.cartCardView);
        }
    }

    public adminAdapter(ArrayList<customerTable> pData, Context ct)
    {
        this.Data=pData;
        this.ct1 =ct;
    }

    @Override
    public adminAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detailsforadmin,parent,false);
        // set the view size, margin,padding and layout parameters
        adminAdapter.ViewHolder vh = new adminAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final adminAdapter.ViewHolder holder, final int position) {
        holder.productN.setText(Data.get(position).getCustomerName()+"");
        holder.delety.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ConnectionClass cont = new ConnectionClass();
                cont.deleteCustomer(Data.get(position).getCustom());

            }
        });
        holder.editty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(ct1, EditCEP.class);
                at.putExtra("name",Data.get(position).getCustom());
                ct1.startActivity(at);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.size() ;
    }

}
