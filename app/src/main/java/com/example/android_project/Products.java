package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Products extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        ListView products_lv = findViewById(R.id.products_lv);

        Intent i = getIntent();
        ArrayList<Product> productsList = i.getParcelableArrayListExtra("productsList");

        if (productsList != null) {
            ProductAdapter productAdapter = new ProductAdapter(productsList, getApplicationContext());
            products_lv.setAdapter(productAdapter);

            products_lv.setOnItemClickListener((parent, view, position, id) -> {
                Product selectedProduct = productAdapter.getItem(position);
                if (selectedProduct != null) {
                    Intent goToProduct = new Intent(getApplicationContext(), ProductInfo.class);
                    goToProduct.putExtra("pId", selectedProduct.getId());
                    goToProduct.putExtra("pName", selectedProduct.getProductName());
                    goToProduct.putExtra("pPrice", selectedProduct.getProductPrice());
                    goToProduct.putExtra("pImage", selectedProduct.getProductImage());
                    goToProduct.putExtra("pDesc", selectedProduct.getProductDescription());
                    goToProduct.putExtra("pHeight", selectedProduct.getProductHeight());
                    goToProduct.putExtra("pWidth", selectedProduct.getProductWidth());
                    goToProduct.putExtra("pDepth", selectedProduct.getProductDepth());
                    startActivity(goToProduct);
                }
            });
        }
    }
}
