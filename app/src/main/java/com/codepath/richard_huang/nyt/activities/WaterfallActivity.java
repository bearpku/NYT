package com.codepath.richard_huang.nyt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.richard_huang.nyt.R;
import com.codepath.richard_huang.nyt.adapters.ArticleAdapter;
import com.codepath.richard_huang.nyt.adapters.ArticleItemClickListener;
import com.codepath.richard_huang.nyt.fragments.FilterFragment;
import com.codepath.richard_huang.nyt.models.Article;
import com.codepath.richard_huang.nyt.utils.AutoPaginationListener;
import com.codepath.richard_huang.nyt.utils.Utils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by richard_huang on 4/3/17.
 */

public class WaterfallActivity extends AppCompatActivity implements FilterFragment.EditFilterListener {
    private List<Article> articles;
    private ArticleAdapter articleAdapter;
    private RecyclerView recyclerView;
    private String beginDate;
    private AutoPaginationListener scrollListener;
    private String sort = "Newest";
    private boolean arts = false, fashion = false, sports = false;
    private RequestParams queryParams = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);

        // load initial data
        articles = new ArrayList<>();
        articleAdapter = new ArticleAdapter(this, articles);
        Utils.fetchArticles(queryParams, articleAdapter);

        recyclerView = (RecyclerView) findViewById(R.id.articlesView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(articleAdapter);
        scrollListener = new AutoPaginationListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                queryParams.put("page", page + "");
                Utils.fetchArticles(queryParams, articleAdapter);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        articleAdapter.setOnItemClickListener(new ArticleItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Article article = articles.get(position);

                Intent intent = new Intent(WaterfallActivity.this, WebViewActivity.class);
                intent.putExtra("web_url", article.getWebUrl());
                startActivity(intent);
            }
        });

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        Calendar c = Calendar.getInstance();
        String mm = Integer.toString(c.get(Calendar.MONTH));
        String dd = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        if (c.get(Calendar.MONTH) < 10) mm = "0" + mm;
        if (c.get(Calendar.DAY_OF_MONTH) < 10) dd = "0" + dd;
        beginDate = Integer.toString(c.get(Calendar.YEAR)) + mm + dd;
        Log.d("begin_date", beginDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search_text);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryParams.remove("page");
                queryParams.put("q", query);
                Utils.fetchArticles(queryParams, articleAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_text) {
            return true;
        } else if (id == R.id.search_filter) {
            FilterFragment.newInstance(beginDate, sort, arts, fashion, sports)
                    .show(getSupportFragmentManager(), "editFilter");
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveEdit(String order, String beginDate, boolean arts, boolean fashion, boolean sports) {
        sort = order;
        this.beginDate = beginDate;
        this.arts = arts;
        this.fashion = fashion;
        this.sports = sports;

        StringBuilder sb = new StringBuilder();
        sb.append("news_desk:(");
        if (arts) {
            sb.append("\"Arts\" ");
        }
        if (fashion) {
            sb.append("\"Fashion &amp; Style\" ");
        }
        if (sports) {
            sb.append("\"Sports\" ");
        }
        sb.append(")");

        queryParams.put("begin_date", beginDate);
        queryParams.put("sort", order);
        if (sb.length() > 2) {
            queryParams.put("fq", sb.toString());
        } else {
            queryParams.remove("fq");
        }
        Utils.fetchArticles(queryParams, articleAdapter);
    }
}
