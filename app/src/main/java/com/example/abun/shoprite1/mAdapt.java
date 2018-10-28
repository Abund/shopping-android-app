package com.example.abun.shoprite1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

/**
 * Created by Abun on 7/9/2018.
 */

public class mAdapt extends BaseAdapter {
    public class ViewHolder{
        TextView textView;
        ImageView imageView;
    }

    public List<ProductPageRetrive> list;
    public Context context;
    public ArrayList<ProductPageRetrive> arrayList;
    public mAdapt(List<ProductPageRetrive> apps,Context cont){
        this.list=apps;
        this.context=cont;
        arrayList = new ArrayList<ProductPageRetrive>();
        arrayList.addAll(list);
    }
    @Override
    public int getCount(){
        return list.size();
    }
    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        final ViewHolder viewHolder= new ViewHolder();
        if(rowView == null){
            //LayoutInflater inflater = getLayoutInflater();
            rowView= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contain,parent,false);
            //rowView = inflater.inflate(R.layout.activity_product_catergory,parent,false);
            //viewHolder = new ViewHolder();
            viewHolder.imageView=(ImageView) rowView.findViewById(R.id.imaging);
            viewHolder.textView=(TextView) rowView.findViewById(R.id.mtitle);
        }else{
            //viewHolder =(ViewHolder) convertView.getTag();
        }
        //viewHolder.textView.setText(list.get(position).getName());
        try {
            InputStream is = list.get(position).getIsm();
            Bitmap mp = BitmapFactory.decodeStream(is);
            viewHolder.imageView.setImageBitmap(mp);
            viewHolder.textView.setText(""+list.get(position).getName());
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,productDetails.class);

                    viewHolder.imageView.buildDrawingCache();
                    Bitmap bitmap = viewHolder.imageView.getDrawingCache();
                    intent.putExtra("bitmap",bitmap);
                    intent.putExtra("name",list.get(position).getName());
                    //intent.putExtra("Image",Data.get(position).getIsm());
                    intent.putExtra("Description",list.get(position).getDescription());
                    intent.putExtra("Price",list.get(position).getPrice());
                    intent.putExtra("Stock",list.get(position).getStock());
                    intent.putExtra("proid",list.get(position).getProdid());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }
            });
        }catch (Exception e){

        }
        return rowView;
    }
}