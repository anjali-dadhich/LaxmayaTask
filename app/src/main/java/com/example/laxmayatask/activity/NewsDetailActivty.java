package com.example.laxmayatask.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laxmayatask.R;
import com.squareup.picasso.Picasso;

public class NewsDetailActivty extends AppCompatActivity {

    ImageView imageArticle;
    TextView textTitle, textDescription;
    String strTitle = "";
    String strDescription = "";
    String strImageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_activty);

        initView();
    }

    private void initView() {
        imageArticle = findViewById(R.id.imageArticle);
        textDescription = findViewById(R.id.textDescription);
        textTitle = findViewById(R.id.textTitle);

        strTitle = getIntent().getStringExtra("Title");
        strDescription = getIntent().getStringExtra("Description");
        strImageUrl = getIntent().getStringExtra("strImageUrl");

        setData();
    }

    private void setData() {
        Picasso.get().load(strImageUrl).into(imageArticle);
        textTitle.setText(strTitle);
        textDescription.setText(strDescription);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}