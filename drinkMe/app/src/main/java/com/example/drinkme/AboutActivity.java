package com.example.drinkme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class AboutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout8);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        TestAdapter mDbHelper = new TestAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();

        Cursor testdata = mDbHelper.getProducts();

        StringBuffer buffer = new StringBuffer();
        while (testdata.moveToNext()) {
            buffer.append("Id: " + testdata.getString(0) + "\n");
            buffer.append("Nazwa: " + testdata.getString(1) + "\n");
            buffer.append("Liczba butelek: " + testdata.getString(2) + "\n");
            buffer.append("Opis: " + testdata.getString(3) + "\n");
            buffer.append("Cena: " + testdata.getString(4) + "\n");
            buffer.append("Numer seryjny: " + testdata.getString(5) + "\n");
            buffer.append("Miejsce: " + testdata.getString(6) + "\n");
            buffer.append("Rodzaj: " + testdata.getString(7) + "\n"+"\n");
        }

                showMessage("Test", buffer.toString());





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout8);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_login) {
            Intent loginIntent = new Intent(AboutActivity.this,
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout8);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}
