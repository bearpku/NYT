package com.codepath.richard_huang.nyt.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.richard_huang.nyt.R;

/**
 * Created by richard_huang on 4/3/17.
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    public TextView titleView;
    public ImageView imageView;

    public ArticleViewHolder(final View view, final ArticleItemClickListener listener) {
        super(view);

        titleView = (TextView) view.findViewById(R.id.title);
        imageView = (ImageView) view.findViewById(R.id.image);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(view, position);
                    }
                }
            }
        });

    }
}
