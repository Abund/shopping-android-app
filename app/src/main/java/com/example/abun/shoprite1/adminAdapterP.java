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

public class adminAdapterP extends RecyclerView.Adapter<adminAdapterP.ViewHolder>{

    private ArrayList<ProductPageRetrive> Data;
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

    public adminAdapterP(ArrayList<ProductPageRetrive> pData, Context ct)
    {
        this.Data=pData;
        this.ct1 =ct;
    }

    @Override
    public adminAdapterP.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detailsforadminproducts,parent,false);
        // set the view size, margin,padding and layout parameters
        adminAdapterP.ViewHolder vh = new adminAdapterP.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final adminAdapterP.ViewHolder holder, final int position) {
        holder.productN.setText(Data.get(position).getName()+"");
        holder.delety.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ConnectionClass cont = new ConnectionClass();
                cont.deleteProduct(Data.get(position).getProdid());

            }
        });
        holder.editty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(ct1, EditionPageForProduct.class);
                ct1.startActivity(at);
            }
        });
        try {

            InputStream is = Data.get(position).getIsm();
            Bitmap mp = BitmapFactory.decodeStream(is);

            holder.img.setImageBitmap(mp);
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return Data.size() ;
    }

}
