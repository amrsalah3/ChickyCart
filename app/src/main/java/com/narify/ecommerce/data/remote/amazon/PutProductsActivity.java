package com.narify.ecommerce.data.remote.amazon;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.narify.ecommerce.R;
import com.narify.ecommerce.model.Category;
import com.narify.ecommerce.model.Discount;
import com.narify.ecommerce.model.Price;
import com.narify.ecommerce.model.Product;
import com.narify.ecommerce.model.Rating;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class PutProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_products);
    }

    public void uploadProducts(View view) throws JSONException {
        String jsonProducts = AmazonToProductMapper.readAssetsFile("sample_books.json", this);
        JSONArray jsonArr = new JSONArray(jsonProducts);
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject p = jsonArr.getJSONObject(i);

            JSONArray imagesArr = p.getJSONArray("images");
            List<String> images = new ArrayList<>();
            for (int j = 0; j < imagesArr.length(); j++) {
                images.add(imagesArr.get(j).toString());
            }

            JSONObject jsonCategory = p.getJSONObject("category");
            Category category = new Category(
                    jsonCategory.getString("category_id"),
                    jsonCategory.getString("name")
            );

            JSONObject jsonPrice = p.getJSONObject("price");
            Price price = new Price(
                    Double.parseDouble(jsonPrice.getString("value")),
                    Double.parseDouble(jsonPrice.getString("original_value")),
                    jsonPrice.getString("currency"),
                    jsonPrice.getString("symbol"),
                    new Discount(0, false, "0% OFF"),
                    jsonPrice.getString("raw")
            );


            Product product = new Product(
                    "",
                    p.getString("title"),
                    p.getString("description"),
                    images,
                    category,
                    price,
                    p.getInt("stock"),
                    new Rating((float) p.getDouble("rating"), p.getInt("ratingsCount"))
            );

            FirebaseFirestore.getInstance().collection("products").document().set(product).addOnCompleteListener(task -> {
                Timber.d("GeneralLogKey uploadProducts: %s", task.isSuccessful());
            });
            break;
            /*FirebaseDatabase.getInstance().getReference().child("products").push().setValue(product)
                    .addOnCompleteListener(task -> Timber.d("GeneralLogKey uploadProducts: %s", task.isSuccessful()));*/
        }

/*        firestore.collection("products").add(products.get(0))
                .addOnCompleteListener(task -> Toast.makeText(this, task.isSuccessful()+"", Toast.LENGTH_SHORT).show())
                .addOnCanceledListener(() -> Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show());*/
    }

   /* public void createProductsFromAmazon(View view) {
        List<AmazonProduct> amazonProducts;
        try {
            String jsonSample = AmazonToProductMapper.readAssetsFile("sample_products.json", this);
            String jsonProducts = new JSONObject(jsonSample).getJSONArray("category_results").toString();

            Gson gson = new Gson();
            Type type = new TypeToken<List<AmazonProduct>>() {
            }.getType();
            amazonProducts = gson.fromJson(jsonProducts, type);

            List<Product> products = new ArrayList<>();
            for (AmazonProduct p : amazonProducts) products.add(AmazonToProductMapper.map(p));

            Timber.d("GeneralLogKey products: %s", products);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rainforestapi.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();

        RainforestApi api = retrofit.create(RainforestApi.class);
        api.getProducts(3017941L).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                List<AmazonProduct> amazonProducts = null;
                try {
                    String jsonProducts = new JSONObject(response.body()).getJSONArray("category_results").toString();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<AmazonProduct>>() {
                    }.getType();
                    amazonProducts = gson.fromJson(jsonProducts, type);
                    Timber.d("GeneralLogKey onResponse: %s", amazonProducts);

                    List<Product> products = new ArrayList<>();
                    for (AmazonProduct p : amazonProducts) {
                        products.add(AmazonToProductMapper.map(p));
                    }
                    Timber.d("GeneralLogKey products: %s", products);
                    FirebaseFirestore.getInstance().collection("products").document("admin")
                            .set(new ProductsList(products))
                            .addOnSuccessListener(aVoid -> Toast.makeText(PutProductsActivity.this, "Success", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Log.d("GeneralLogKey", "onResponse: " + e));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PutProductsActivity.this, "Fail", Toast.LENGTH_LONG).show();
                Timber.d("GeneralLogKey Couldn't get data from RainForest onFailure: %s %s", call.request().url(), t.getMessage());
            }
        });
    }
    */


}