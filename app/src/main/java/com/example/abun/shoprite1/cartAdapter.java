package com.example.abun.shoprite1;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Abun on 7/18/2018.
 */


public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder> {

    private ArrayList<OrderTable> mData;
    private ArrayList<ProductPageRetrive> Data;
    private Context ct1;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView prices;
        public TextView quantity;
        public TextView productN;
        public TextView delete;
        public ImageView img;
        public View layout;
        public CardView card;

        public ViewHolder(View itemView){
            super(itemView);
            layout = itemView;
            productN  = (TextView) itemView.findViewById(R.id.productN);
            quantity = (TextView) itemView.findViewById(R.id.quantities);
            prices  = (TextView) itemView.findViewById(R.id.price);
            delete  = (TextView) itemView.findViewById(R.id.delete);
            img = (ImageView) itemView.findViewById(R.id.imaging);
            card = (CardView) itemView.findViewById(R.id.cartCardView);
        }
    }

    public cartAdapter(ArrayList<OrderTable> pData,ArrayList<ProductPageRetrive> Data, Context ct)
    {
        this.Data=Data;
        this.ct1 =ct;
        this.mData=pData;
    }

    @Override
    public cartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartminpage,parent,false);
        // set the view size, margin,padding and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final cartAdapter.ViewHolder holder, final int position) {
        holder.quantity.setText(mData.get(position).getQty()+"");
        holder.productN.setText(Data.get(position).getName()+"");
        //holder.delete.setText(Data.get(position).getName()+"");
        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //ConnectionClass ct = new ConnectionClass();
                //ct.deleteItemFromCart(mData.get(position).getCustomerid(),Data.get(position).getProdid());
                //FragmentTransaction ft = getFragmentManager().

            }
        });
        double pricing=mData.get(position).getAmount()*mData.get(position).getQty();
        holder.prices.setText(pricing+"");

        try {
            InputStream is = Data.get(position).getIsm();
            Bitmap mp = BitmapFactory.decodeStream(is);
            holder.img.setImageBitmap(mp);
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}

