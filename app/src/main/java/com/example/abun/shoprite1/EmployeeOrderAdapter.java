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
 * Created by Abun on 7/21/2018.
 */


public class EmployeeOrderAdapter extends RecyclerView.Adapter<EmployeeOrderAdapter.ViewHolder> {
    private ArrayList<orderModified> Data;
    private ArrayList<customerTable> Data1;
    private Context ct1;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView qty;
        public TextView customername;
        public ImageView img;
        public View layout;
        public CardView card;
        public Button button;

        public ViewHolder(View itemView){
            super(itemView);
            layout = itemView;
            qty = (TextView) itemView.findViewById(R.id.quantities);
            customername = (TextView) itemView.findViewById(R.id.customerName);
            img = (ImageView) itemView.findViewById(R.id.customerImage);
            button = (Button) itemView.findViewById(R.id.conf);
            card = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public EmployeeOrderAdapter(ArrayList<orderModified> Data,ArrayList<customerTable> Data1, Context ct)
    {
        this.Data=Data;
        this.ct1 =ct;
        this.Data1=Data1;
    }

    @Override
    public EmployeeOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_employee_page,parent,false);
        // set the view size, margin,padding and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final EmployeeOrderAdapter.ViewHolder holder, final int position) {
        holder.customername.setText(Data1.get(position).getCustomerName()+"");
        holder.qty.setText(Data.get(position).getQuantity()+"");

        try {
            /*InputStream is = Data.get(position).getIsm();
            Bitmap mp = BitmapFactory.decodeStream(is);
            holder.img.setImageBitmap(mp);*/

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ct1,EmployeeConfirmationPage.class);
                    intent.putExtra("name111",Data1.get(position).getCustomerName());
                    ct1.startActivity(intent);
                }
            });
        }catch (Exception e){

        }
    }
    @Override
    public int getItemCount() {
        return Data1.size();
    }
}
