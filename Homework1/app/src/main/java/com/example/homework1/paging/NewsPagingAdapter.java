package com.example.homework1.paging;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.homework1.databinding.ItemArticleBinding;
import com.example.homework1.model.Article;

public class NewsPagingAdapter extends PagingDataAdapter<Article, NewsPagingAdapter.ArticleViewHolder> {

    public NewsPagingAdapter(@NonNull DiffUtil.ItemCallback<Article> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(ItemArticleBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = getItem(position);
        if (article != null) {
            holder.bind(article);
        }
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final ItemArticleBinding binding;

        public ArticleViewHolder(ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article article) {
            binding.tvTitle.setText(article.getTitle());
            binding.tvDescription.setText(article.getDescription());
            binding.tvSource.setText(article.getSource() != null ? article.getSource().getName() : "");
            binding.tvPublishedAt.setText(article.getPublishedAt());

            Glide.with(itemView.getContext())
                    .load(article.getUrlToImage())
                    .into(binding.ivArticleImage);
        }
    }

    public static class ArticleComparator extends DiffUtil.ItemCallback<Article> {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription());
        }
    }
}
