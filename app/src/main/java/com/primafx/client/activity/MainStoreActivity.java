package com.primafx.client.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.primafx.client.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_store);


        final List<HashMap<String, String>> aList = new ArrayList<>();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", "Baju");
        hm.put("image", "http://asdasdadasd.com/asdgagdad.jpg");
        aList.add(hm);

        String[] from = {"name"};
        int[] to = {R.id.textProductName};
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_store_items, from, to) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                /*
                ImageView imagePerson = (ImageView)v.findViewById(R.id.imagePerson);
                Picasso.with(MainManageActivity.this).load(transactions.get(position).getPicture())
                        .error(MainManageActivity.this.getResources().getDrawable(R.mipmap.no_image_square))
                        .into(imagePerson);
                        */
                return super.getView(position, convertView, parent);
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);
    }
}
