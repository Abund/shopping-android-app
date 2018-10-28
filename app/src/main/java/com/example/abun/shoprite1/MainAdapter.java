package com.example.abun.shoprite1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import java.io.FileOutputStream;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
//import com.squareup.picasso.Picasso;
import android.content.Context;

/**
 * Created by Abun on 6/13/2018.
 */


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<String> mData;
    private ArrayList<ProductPageRetrive> Data;
    private Context ct1;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mtitle;
        public TextView mtitle1;
        public ImageView img;
        public View layout;
        public CardView card;

        public ViewHolder(View itemView){
            super(itemView);
            layout = itemView;
            mtitle = (TextView) itemView.findViewById(R.id.mtitle);
            mtitle1 = (TextView) itemView.findViewById(R.id.mtitle1);
            img = (ImageView) itemView.findViewById(R.id.imaging);
            card = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public MainAdapter(ArrayList<String> Data1){

        this.mData=Data1;
    }

    public MainAdapter(ArrayList<ProductPageRetrive> Data, Context ct)
    {
        this.Data=Data;
        this.ct1 =ct;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contain,parent,false);
        // set the view size, margin,padding and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MainAdapter.ViewHolder holder, final int position) {
        /*int py =Data.get(position).getPrice();
        String pp = py +"";*/
        holder.mtitle.setText(Data.get(position).getName()+"");
        holder.mtitle1.setText(Data.get(position).getPrice()+"");


        try {

            InputStream is = Data.get(position).getIsm();
            Bitmap mp = BitmapFactory.decodeStream(is);

            holder.img.setImageBitmap(mp);
            /*OutputStream os = new FileOutputStream(new File("photo1.jpg"));
            byte[] content = new byte[1024];
            int size = 0;
            while((size=is.read(content))!=-1){
                os.write(content,0,size);
            }
            os.close();
            is.close();
            //Picasso.get().load("file:photo1.jpg").into(holder.img);

            Picasso.get().load("file:photo1.jpg").into(holder.img);*/


            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ct1,productDetails.class);

                    holder.img.buildDrawingCache();
                    Bitmap bitmap = holder.img.getDrawingCache();
                    intent.putExtra("bitmap",bitmap);
                    intent.putExtra("name",Data.get(position).getName());
                    //intent.putExtra("Image",Data.get(position).getIsm());
                    intent.putExtra("Description",Data.get(position).getDescription());
                    intent.putExtra("Price",Data.get(position).getPrice());
                    intent.putExtra("Stock",Data.get(position).getStock());
                    intent.putExtra("proid",Data.get(position).getProdid());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    ct1.startActivity(intent);
                }
            });
        }catch (Exception e){

        }
        /*byte[] decorde = Base64.decode(Data.get(position).getImg1(),Base64.DEFAULT);
        Bitmap mp = BitmapFactory.decodeByteArray(decorde,0,decorde.length);
        holder.img.setImageBitmap(mp);*/

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }


}
