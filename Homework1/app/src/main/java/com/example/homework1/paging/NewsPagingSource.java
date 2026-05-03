package com.example.homework1.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.ListenableFuturePagingSource;
import com.example.homework1.api.NewsApiService;
import com.example.homework1.api.NewsResponse;
import com.example.homework1.model.Article;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

public class NewsPagingSource extends ListenableFuturePagingSource<Integer, Article> {
    private final NewsApiService apiService;
    private final String query;
    private final String apiKey;

    public NewsPagingSource(NewsApiService apiService, String query, String apiKey) {
        this.apiService = apiService;
        this.query = query;
        this.apiKey = apiKey;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Article> state) {
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) return null;
        return state.closestPageToPosition(anchorPosition).getPrevKey();
    }

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, Article>> loadFuture(@NonNull LoadParams<Integer> params) {
        Integer nextPageNumber = params.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
        }

        int finalNextPageNumber = nextPageNumber;
        
        // Gọi API trả về ListenableFuture (không chặn luồng chính)
        ListenableFuture<NewsResponse> responseFuture = apiService.getNews(query, apiKey, finalNextPageNumber, params.getLoadSize());

        return Futures.transform(
                responseFuture,
                response -> {
                    if (response == null || response.getArticles() == null) {
                        return new LoadResult.Error<>(new IOException("Failed to load news"));
                    }
                    List<Article> articles = response.getArticles();
                    return new LoadResult.Page<>(
                            articles,
                            finalNextPageNumber == 1 ? null : finalNextPageNumber - 1,
                            articles.isEmpty() ? null : finalNextPageNumber + 1
                    );
                },
                // Chạy phần xử lý kết quả ở một luồng khác để tránh giật lag UI
                Executors.newSingleThreadExecutor()
        );
    }
}
