package com.example.laxmayatask.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laxmayatask.activity.MainActivity;
import com.example.laxmayatask.R;
import com.example.laxmayatask.activity.NewsDetailActivty;
import com.example.laxmayatask.model.NewsApiResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<NewsApiResponse.Article> articleArrayList;
    ArrayList<NewsApiResponse.Article> filterArticleList;

    public RecyclerAdapter(Context context, ArrayList<NewsApiResponse.Article> articleArrayList) {
        this.context = context;
        this.articleArrayList = articleArrayList;
        this.filterArticleList = new ArrayList<NewsApiResponse.Article>();
        this.filterArticleList.addAll(MainActivity.articlesListResponse);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_layout,
                parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String strTitle = "";
        String strDescription = "";
        String strImageUrl = "";
        if (articleArrayList.get(position).getUrlToImage() != null) {
            Picasso.get().load(articleArrayList.get(position).getUrlToImage()).into(holder.imageArticle);
            strImageUrl = articleArrayList.get(position).getUrlToImage();
        }

        if (articleArrayList.get(position).getSource().getName() != null) {
            holder.textName.setText( articleArrayList.get(position).getSource().getName());
        } else {
            holder.textName.setText("News ");
        }

        if (articleArrayList.get(position).getAuthor() != null) {
            holder.textAuthorName.setText("Author Name: " + articleArrayList.get(position).getAuthor());
        } else {
            holder.textAuthorName.setText("Author Name: ");
        }

        if (articleArrayList.get(position).getTitle() != null) {
            holder.textTitle.setText("Title: " + articleArrayList.get(position).getTitle());
            strTitle = articleArrayList.get(position).getTitle();
        } else {
            holder.textTitle.setText("Title: ");
            strTitle = "";
        }

        if (articleArrayList.get(position).getDescription() != null) {
            strDescription = articleArrayList.get(position).getDescription();
        } else {
            strDescription = "";
        }

        String finalStrTitle = strTitle;
        String finalStrDescription = strDescription;
        String finalStrImageUrl = strImageUrl;
        holder.articleItemRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewsDetailActivity(finalStrTitle, finalStrDescription, finalStrImageUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout articleItemRL;
        ImageView imageArticle;
        TextView textName, textTitle, textAuthorName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            articleItemRL = itemView.findViewById(R.id.articleItemRL);
            imageArticle = itemView.findViewById(R.id.imageArticle);
            textName = itemView.findViewById(R.id.textName);
            textTitle = itemView.findViewById(R.id.textTitle);
            textAuthorName = itemView.findViewById(R.id.textAuthorName);
        }
    }

    public void openNewsDetailActivity(String strTitle, String strDescription, String strImageUrl) {
        Intent iNewsDetail = new Intent(context, NewsDetailActivty.class);
        iNewsDetail.putExtra("Title", strTitle );
        iNewsDetail.putExtra("Description", strDescription );
        iNewsDetail.putExtra("ImageUrl", strImageUrl );
        context.startActivity(iNewsDetail);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text)
    {
        articleArrayList.clear();
        if (text.length()==0)
        {
            MainActivity.articlesListResponse.addAll(filterArticleList);
        }
        else
        {
            for (NewsApiResponse.Article newsApiResponse : filterArticleList)
            {
                if (newsApiResponse.getSource().getName() != null) {
                    if (newsApiResponse.getSource().getName().toLowerCase().contains(text.toLowerCase())) {
                        MainActivity.articlesListResponse.add(newsApiResponse);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
