package com.codepath.richard_huang.nyt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.richard_huang.nyt.R;
import com.codepath.richard_huang.nyt.models.Article;
import com.codepath.richard_huang.nyt.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by richard_huang on 4/3/17.
 */


public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private List<Article> articles;
    private Context context;
    private ArticleItemClickListener listener;

    public void setOnItemClickListener(ArticleItemClickListener listener) {
        this.listener = listener;
    }

    public ArticleAdapter(Context context, List<Article> articles) {
        this.articles = articles;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    public void addItem(Article article) {
        articles.add(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View articleView = inflater.inflate(R.layout.item_article, parent, false);

        return new ArticleViewHolder(articleView, listener);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder viewHolder, int position) {
        Article article = articles.get(position);
        viewHolder.titleView.setText(article.getHeadline().getMain());
        String imageUrl = article.getImageUrl();

        if (!imageUrl.isEmpty()) {
            Picasso.with(context)
                    .load(Utils.getFullImageURL(imageUrl))
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(viewHolder.imageView);
        }
    }
}