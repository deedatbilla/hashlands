package com.deedat.landsystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.deedat.landsystem.Adapter.suggestions_adapter;
import com.deedat.landsystem.Model.LandInfo;
import com.deedat.landsystem.R;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterActivity extends AppCompatActivity  implements View.OnClickListener{
    private List<LandInfo> landInfoList;
    private MaterialSearchBar searchBar;

    private SuggestionsAdapter customSuggestionsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter);
        landInfoList = new ArrayList<>();
//        landInfoList.add(new LandInfo("GH-242324334","1200 by 800 sq.ft","CR-Kasoa","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750"));
//        landInfoList.add(new LandInfo("GH-977B8284","1320 by 300 sq.ft","GR-Achimota","Deedat Idriss Billa","https://horizon-media.s3-eu-west-1.amazonaws.com/s3fs-public/styles/large/public/field/image/Kenyan%20landscape%20cropped%20-%20shutterstock_216892456%20-%20Maciej%20Czekajewski.jpg?itok=7LOkfAm1"));
//        landInfoList.add(new LandInfo("GH-98676763","1100 by 800 sq.ft","CR-Winneba","Paul Dwamena","https://content.magicbricks.com/images/uploads/2018/3/lands1.jpg"));
//        landInfoList.add(new LandInfo("GH-242324334","1200 by 800 sq.ft","CR-Kasoa","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750"));
//        landInfoList.add(new LandInfo("GH-977B8284","1320 by 300 sq.ft","GR-Achimota","Deedat Idriss Billa","https://horizon-media.s3-eu-west-1.amazonaws.com/s3fs-public/styles/large/public/field/image/Kenyan%20landscape%20cropped%20-%20shutterstock_216892456%20-%20Maciej%20Czekajewski.jpg?itok=7LOkfAm1"));
//        landInfoList.add(new LandInfo("GH-98676763","1100 by 800 sq.ft","CR-Winneba","Paul Dwamena","https://content.magicbricks.com/images/uploads/2018/3/lands1.jpg"));


        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        customSuggestionsAdapter = new suggestions_adapter(inflater);

        Button addProductBtn = (Button) findViewById(R.id.button);
        addProductBtn.setOnClickListener(this);

        searchBar.setMaxSuggestionCount(2);
        searchBar.setHint("Find Product..");

       // for (int i = 1; i < 11; i++) {
         //   suggestions.add(new Product(products[i -1], i * 10));
        //}

        customSuggestionsAdapter.setSuggestions(landInfoList);
        searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
                // send the entered text to our filter and let it manage everything
                customSuggestionsAdapter.getFilter().filter(searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    @Override
    public void onClick(View view) {
       //customSuggestionsAdapter.addSuggestion(new LandInfo("GH-242324334","1200 by 800 sq.ft","CR-Kasoa","Ivar Boneless","https://lh3.googleusercontent.com/Ivt7imnUp4Jp7Oe_PzxNnOZAOtU6tVcwUG-ylEJ6-uCWFAYEQ9F2-atNyLWgTjq-LG2_BTPHPz2brpY_7QYVYRZhgBXBCL5w=s750"));
    }
}
