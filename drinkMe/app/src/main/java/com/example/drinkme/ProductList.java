package com.example.drinkme;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.drinkme.FourColumn_adapter;
import com.example.drinkme.Product;
import com.example.drinkme.R;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.drinkme.FourColumn_adapter.idproduktu;
import static com.example.drinkme.FourColumn_adapter.iloscproduktu;

public class ProductList extends AppCompatActivity {


    ArrayList<Product> productList;
    ListView listView;
    Product product;
    static String str_idproduktu = "";
    static Integer int_idproduktu =0;
    static String str_iloscproduktu="";
    static Integer int_iloscproduktu =0;
    static Integer ilosc = 0;
    static Integer wybrany = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        final TestAdapter mDbHelper = new TestAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();

        productList = new ArrayList<>();
        Cursor data = mDbHelper.getProducts();

        int numRows = data.getCount();
        if(numRows == 0) {
            Toast.makeText(ProductList.this, "Nie mam nic do pokazania", Toast.LENGTH_LONG).show();
        }
        else {
            while (data.moveToNext()) {
                product = new Product(data.getString(0),
                        data.getString(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4)
                );
                productList.add(product);
            }
            FourColumn_adapter adapter = new FourColumn_adapter(this, R.layout.list_adapter, productList);
            listView = (ListView)findViewById(R.id.listProducts);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    str_idproduktu = (idproduktu.get(position));
                    int_idproduktu = Integer.parseInt(str_idproduktu);

                    str_iloscproduktu = (iloscproduktu.get(position));
                    int_iloscproduktu = Integer.parseInt(str_iloscproduktu);

                  //  int_idproduktu = Integer.parseInt(str_idproduktu);
                    //mDbHelper.updateLiczbaButelek(int_iloscproduktu-1,int_idproduktu);
                    Toast.makeText(ProductList.this, "Wybrane id: "+str_idproduktu,Toast.LENGTH_SHORT).show();
                    openProductView();
                }
            });
                }
            }
            public void openProductView () {
                Intent view = new Intent(this, ProductViewActivity.class);
                startActivity(view);
            }
        }

