package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ProductInfo extends AppCompatActivity {

    String id, img;
    TextView pName, pDesc, pHeight, pWidth, pDepth, pPrice;
    ImageView pImage;
    Button btnDelete, btnEdit, btnBack;

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    public static ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        pName = findViewById(R.id.pName);
        pPrice = findViewById(R.id.pPrice);
        pImage = findViewById(R.id.pImage);
        pDesc = findViewById(R.id.pDesc);
        pHeight = findViewById(R.id.pHeight);
        pWidth = findViewById(R.id.pWidth);
        pDepth = findViewById(R.id.pDepth);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        // btnBack = findViewById(R.id.btnBack);

        Intent content = getIntent();

        String product_name, product_image, product_price, product_desc, product_height, product_width, product_depth;

        product_name = content.getStringExtra("pName");
        pName.setText(product_name);

        product_price = content.getStringExtra("pPrice");
        pPrice.setText(product_price);

        id = content.getStringExtra("pId");

        product_image = content.getStringExtra("pImage");
        Picasso.get().load(product_image).into(pImage);
        img = product_image;

        product_desc = content.getStringExtra("pDesc");
        pDesc.setText(product_desc);

        product_height = content.getStringExtra("pHeight");
        pHeight.setText(product_height);

        product_width = content.getStringExtra("pWidth");
        pWidth.setText(product_width);

        product_depth = content.getStringExtra("pDepth");
        pDepth.setText(product_depth);

        // btnBack.setOnClickListener(v -> {
        //     Intent i = new Intent(getApplicationContext(), Products.class);
        //     startActivity(i);
        // });

        btnDelete.setOnClickListener(v -> {
            deleteProductById(id);
            getAllProducts();
        });

        btnEdit.setOnClickListener(v -> {
            Intent goToEditProduct = new Intent(getApplicationContext(), EditProduct.class);
            goToEditProduct.putExtra("pId", id);
            goToEditProduct.putExtra("pName", pName.getText().toString());
            goToEditProduct.putExtra("pPrice", pPrice.getText().toString());
            goToEditProduct.putExtra("pImage", img);
            goToEditProduct.putExtra("pDesc", pDesc.getText().toString());
            goToEditProduct.putExtra("pHeight", pHeight.getText().toString());
            goToEditProduct.putExtra("pWidth", pWidth.getText().toString());
            goToEditProduct.putExtra("pDepth", pDepth.getText().toString());

            startActivity(goToEditProduct);
        });
    }

    private void deleteProductById(String pid) {
        database.collection("products").document(pid)
                .delete()
                .addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "Product deleted", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show());
    }

    private void getAllProducts() {
        database.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    products = new ArrayList<>();

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            product.setId(document.getId());
                            products.add(product);

                            Log.d("ProductInfo", "Product ID: " + product.getId());
                            Log.d("ProductInfo", "Name: " + product.getProductName());
                            Log.d("ProductInfo", "Price: " + product.getProductPrice());
                            Log.d("ProductInfo", "Description: " + product.getProductDescription());
                            Log.d("ProductInfo", "Height: " + product.getProductHeight());
                            Log.d("ProductInfo", "Width: " + product.getProductWidth());
                            Log.d("ProductInfo", "Depth: " + product.getProductDepth());
                            Log.d("ProductInfo", "Image URL: " + product.getProductImage());
                            Log.d("ProductInfo", "---------------------------------");
                        }

                        Intent i = new Intent(getApplicationContext(), Products.class);
                        i.putParcelableArrayListExtra("productsList", products);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), "Error:" + task.getException(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
