package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class EditProduct extends AppCompatActivity {

    private String id;
    private TextView pName, pDesc, pHeight, pWidth, pDepth, pPrice, pImage;
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();
    public static ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Button updateProductBtn = findViewById(R.id.update_product_btn);
        pName = findViewById(R.id.productName);
        pDesc = findViewById(R.id.productDescription);
        pHeight = findViewById(R.id.productHeight);
        pWidth = findViewById(R.id.productWidth);
        pDepth = findViewById(R.id.productDepth);
        pPrice = findViewById(R.id.productPrice);
        pImage = findViewById(R.id.productImage);

        Intent content = getIntent();
        pName.setText(content.getStringExtra("pName"));
        pPrice.setText(content.getStringExtra("pPrice"));
        id = content.getStringExtra("pId");
        pImage.setText(content.getStringExtra("pImage"));
        pDesc.setText(content.getStringExtra("pDesc"));
        pHeight.setText(content.getStringExtra("pHeight"));
        pWidth.setText(content.getStringExtra("pWidth"));
        pDepth.setText(content.getStringExtra("pDepth"));

        updateProductBtn.setOnClickListener(v -> {
            editProductById(id);
            getAllProducts();
        });
    }

    private void editProductById(String pid) {
        DocumentReference document = database.collection("products").document(pid);
        document
                .update(
                        "productName", pName.getText().toString(),
                        "productPrice", pPrice.getText().toString(),
                        "productDescription", pDesc.getText().toString(),
                        "productHeight", pHeight.getText().toString(),
                        "productWidth", pWidth.getText().toString(),
                        "productDepth", pDepth.getText().toString(),
                        "productImage", pImage.getText().toString()
                )
                .addOnSuccessListener(unused ->
                        Toast.makeText(getApplicationContext(), "Product updated", Toast.LENGTH_LONG).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private void getAllProducts() {
        database
                .collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    products = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            product.setId(document.getId());
                            products.add(product);
                        }

                        Intent i = new Intent(getApplicationContext(), Products.class);
                        i.putParcelableArrayListExtra("productsList", products);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
