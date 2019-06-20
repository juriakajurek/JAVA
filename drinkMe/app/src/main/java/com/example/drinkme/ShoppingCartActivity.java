package com.example.drinkme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ListView productsListView = (ListView) findViewById(R.id.productsListView);

        ArrayList<String> values = new ArrayList<String>();

        values.add("Pierwszy napój");
        values.add("Drugi napój");
        values.add("Trzeci napój");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        productsListView.setAdapter(adapter);
        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(ShoppingCartActivity.this, "Wybrałeś pozycję nr: "+position, Toast.LENGTH_SHORT).show();


            }
        });


















        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout5);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_login) {
            Intent loginIntent = new Intent(ShoppingCartActivity.this,
                    LoginActivity.class);
            startActivity(loginIntent);
        } else if (id == R.id.nav_register) {
            Intent registerIntent = new Intent(this,
                    RegisterActivity.class);
            startActivity(registerIntent);
        } else if (id == R.id.nav_search_products) {
            Intent searchProductsIntent = new Intent(this,
                    SearchProductsActivity.class);
            startActivity(searchProductsIntent);
        } else if (id == R.id.nav_shopping_cart) {
            /*Intent shoppingCartIntent = new Intent(this,
                    ShoppingCartActivity.class);
            startActivity(shoppingCartIntent);*/
        } else if (id == R.id.nav_contact) {
            Intent contactIntent = new Intent(this,
                    ContactActivity.class);
            startActivity(contactIntent);
        } else if (id == R.id.nav_about) {
            Intent aboutIntent = new Intent(this,
                    AboutActivity.class);
            startActivity(aboutIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout5);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
