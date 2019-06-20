package com.example.drinkme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class FourColumn_adapter extends ArrayAdapter<Product> {
    private LayoutInflater mInflater;
    private ArrayList<Product> products;
    public static ArrayList<String> idproduktu = new ArrayList<String>();
    public static ArrayList<String> iloscproduktu = new ArrayList<String>();
    private int mViewResourceId;


    public FourColumn_adapter(Context context, int textViewResourceId, ArrayList<Product> fishes) {
        super(context, textViewResourceId, fishes);
        this.products = fishes;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {
        convertView = mInflater.inflate(mViewResourceId,null);
        Product product = products.get(position);

        if(product != null) {
            TextView txtId = (TextView) convertView.findViewById(R.id.txtId);
            TextView txtNazwa = (TextView) convertView.findViewById(R.id.txtNazwa);
            TextView txtOpis = (TextView) convertView.findViewById(R.id.txtOpis);
            TextView txtCena = (TextView) convertView.findViewById(R.id.txtCena);
            TextView txtLiczba = (TextView)convertView.findViewById(R.id.txtLiczba);
            if(txtId != null) {
                txtId.setText(product.getIdNum());
                idproduktu.add(product.getIdNum());
            }
            if (txtNazwa != null) {
                txtNazwa.setText((product.getNazwa()));
            }

            if (txtOpis != null) {
                txtOpis.setText((product.getOpis()));
            }

            if (txtCena != null) {
                txtCena.setText((product.getCena()+" zł"));
            }

            if (txtLiczba != null) {
                txtLiczba.setText((product.getLiczba()));
                iloscproduktu.add(product.getLiczba());
            }

        }
        return convertView;
    }
}
