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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class SearchProductsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String search = "";
    EditText txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton searchProductsButton = (ImageButton) findViewById(R.id.searchProductsButton);

        final EditText txtSearch =(EditText)findViewById(R.id.txtSearch);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout4);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Intent productViewIntent = new Intent(this,
                ProductViewActivity.class);

        searchProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                search = txtSearch.getText().toString();
                startActivity(productViewIntent);
            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_login) {
            Intent loginIntent = new Intent(SearchProductsActivity.this,
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
            Intent shoppingCartIntent = new Intent(this,
                    ShoppingCartActivity.class);
            startActivity(shoppingCartIntent);
        } else if (id == R.id.nav_contact) {
            Intent contactIntent = new Intent(this,
                    ContactActivity.class);
            startActivity(contactIntent);
        } else if (id == R.id.nav_about) {
            Intent aboutIntent = new Intent(this,
                    AboutActivity.class);
            startActivity(aboutIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout4);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
