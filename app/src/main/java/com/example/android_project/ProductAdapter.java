package com.example.android_project;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private ArrayList<Product> productsList;
    Context context;

    public ProductAdapter(ArrayList<Product> data,Context context){
        super(context,R.layout.product_item,data);
        this.productsList = data;
        this.context= context;
    }

    private static class viewHolder{
        TextView pName,pDesc,pPrice,pHeight,pWidth,pDepth;
        ImageView pImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Product product = getItem(position);
        viewHolder viewHolder;

        if(convertView == null){
            viewHolder = new viewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.product_item,parent,false);

            viewHolder.pName = convertView.findViewById(R.id.pName);
            viewHolder.pPrice = convertView.findViewById(R.id.pPrice);
            viewHolder.pDesc = convertView.findViewById(R.id.pDesc);
            viewHolder.pHeight = convertView.findViewById(R.id.pHeight);
            viewHolder.pWidth = convertView.findViewById(R.id.pWidth);
            viewHolder.pDepth = convertView.findViewById(R.id.pDepth);
            viewHolder.pImage = convertView.findViewById(R.id.pImage);

            convertView.setTag(viewHolder);


        }else {
            viewHolder = (viewHolder) convertView.getTag();
        }

        viewHolder.pName.setText(product.getProductName());
        viewHolder.pPrice.setText(product.getProductPrice());
//        viewHolder.pDesc.setText(product.getProductDescription());
//        viewHolder.pHeight.setText(product.getProductHeight());
//        viewHolder.pWidth.setText(product.getProductWidth());
//        viewHolder.pDepth.setText(product.getProductDepth());
        if(product.getProductImage().length() >15){
            Picasso.get().load(product.getProductImage()).into(viewHolder.pImage);
        }else {
            viewHolder.pImage.setImageResource(R.drawable.ic_launcher_foreground);
        }
        return convertView;

    }
}


