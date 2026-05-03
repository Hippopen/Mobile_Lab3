package com.example.homework1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;
import com.example.homework1.api.NewsApiService;
import com.example.homework1.model.Article;
import com.example.homework1.paging.NewsPagingSource;
import retrofit2.Retrofit;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {
    private final NewsApiService apiService;
    private final LiveData<PagingData<Article>> newsLiveData;

    public NewsViewModel() {
        // Cần thêm GuavaCallAdapterFactory để Retrofit hỗ trợ ListenableFuture bất đồng bộ
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(GuavaCallAdapterFactory.create())
                .build();

        apiService = retrofit.create(NewsApiService.class);

        // API Key của bạn
        String apiKey = "8bdcbfb73cb4405facbb988366c6a608";

        Pager<Integer, Article> pager = new Pager<>(
                new PagingConfig(20, 5, false),
                () -> new NewsPagingSource(apiService, "covid", apiKey)
        );

        newsLiveData = PagingLiveData.getLiveData(pager);
    }

    public LiveData<PagingData<Article>> getNewsLiveData() {
        return newsLiveData;
    }
}
