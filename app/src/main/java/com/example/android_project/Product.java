package com.example.android_project;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {

    private String id;
    private String productName;
    private String productPrice;
    private String productDescription;
    private String productImage;
    private String productHeight;
    private String productWidth;
    private String productDepth;

    // Parameterized constructor
    public Product(String id, String productName, String productPrice, String productDescription, String productImage, String productHeight, String productWidth, String productDepth) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productHeight = productHeight;
        this.productWidth = productWidth;
        this.productDepth = productDepth;
    }

    // Default constructor
    public Product() {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(String productHeight) {
        this.productHeight = productHeight;
    }

    public String getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(String productWidth) {
        this.productWidth = productWidth;
    }

    public String getProductDepth() {
        return productDepth;
    }

    public void setProductDepth(String productDepth) {
        this.productDepth = productDepth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(productName);
        parcel.writeString(productPrice);
        parcel.writeString(productDescription);
        parcel.writeString(productImage);
        parcel.writeString(productHeight);
        parcel.writeString(productWidth);
        parcel.writeString(productDepth);
    }

    // Parcelable.Creator implementation
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // Constructor for Parcelable
    protected Product(Parcel in) {
        id = in.readString();
        productName = in.readString();
        productPrice = in.readString();
        productDescription = in.readString();
        productImage = in.readString();
        productHeight = in.readString();
        productWidth = in.readString();
        productDepth = in.readString();
    }
}

