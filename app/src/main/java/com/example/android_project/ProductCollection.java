package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductCollection extends AppCompatActivity implements View.OnClickListener {

    private EditText productName, productPrice, productDescription, productHeight, productWidth, productDepth, productImage;
    public static ArrayList<Product> products;
    private final FirebaseFirestore database = FirebaseFirestore.getInstance();

    // Constants for Firestore field names
    public static final String KEY_PRODUCT_NAME = "productName";
    public static final String KEY_PRODUCT_PRICE = "productPrice";
    public static final String KEY_PRODUCT_DESCRIPTION = "productDescription";
    public static final String KEY_PRODUCT_HEIGHT = "productHeight";
    public static final String KEY_PRODUCT_WIDTH = "productWidth";
    public static final String KEY_PRODUCT_DEPTH = "productDepth";
    public static final String KEY_PRODUCT_IMAGE = "productImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_collection);

        // Initialize UI elements
        Button addProductBtn = findViewById(R.id.add_product_btn);
        Button getAllProductsBtn = findViewById(R.id.get_all_products_btn);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        productHeight = findViewById(R.id.productHeight);
        productWidth = findViewById(R.id.productWidth);
        productDepth = findViewById(R.id.productDepth);
        productImage = findViewById(R.id.productImage);

        // Set up click listeners
        addProductBtn.setOnClickListener(this);
        getAllProductsBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_product_btn:
                createNewProduct();
                System.out.println("productttttt:   "+productName);
                break;
            case R.id.get_all_products_btn:
                getAllProducts();
                break;
        }
    }

    private void createNewProduct() {
        // Retrieve input values
        String pName = productName.getText().toString().trim();
        String pPrice = productPrice.getText().toString().trim();
        String pDescription = productDescription.getText().toString().trim();
        String pHeight = productHeight.getText().toString().trim();
        String pWidth = productWidth.getText().toString().trim();
        String pDepth = productDepth.getText().toString().trim();
        String pImage = productImage.getText().toString().trim();

        // Map input values to a Firestore document
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_PRODUCT_NAME, pName);
        data.put(KEY_PRODUCT_PRICE, "₪" + pPrice);
        data.put(KEY_PRODUCT_DESCRIPTION, pDescription);
        data.put(KEY_PRODUCT_HEIGHT, "H: " + pHeight);
        data.put(KEY_PRODUCT_WIDTH, "W: " + pWidth);
        data.put(KEY_PRODUCT_DEPTH, "D: " + pDepth);
        data.put(KEY_PRODUCT_IMAGE, pImage);

        // Add document to Firestore
        database.collection("products").add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Product created", Toast.LENGTH_LONG).show();
                        // Clear input fields
                        clearInputFields();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void getAllProducts() {
        database.collection("products").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        products = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = document.toObject(Product.class);
                                product.setId(document.getId());
                                products.add(product);
                            }
                            // Pass product list to another activity
                            Intent intent = new Intent(getApplicationContext(), Products.class);
                            intent.putParcelableArrayListExtra("productsList", products);
                            System.out.println("dataaaaaa: "+products);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void clearInputFields() {
        productName.setText("");
        productPrice.setText("");
        productImage.setText("");
        productDescription.setText("");
        productDepth.setText("");
        productHeight.setText("");
        productWidth.setText("");
    }
}
