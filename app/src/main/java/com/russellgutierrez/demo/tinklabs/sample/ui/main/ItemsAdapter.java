package com.russellgutierrez.demo.tinklabs.sample.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.russellgutierrez.demo.tinklabs.sample.R;
import com.russellgutierrez.demo.tinklabs.sample.data.model.Item;
import com.russellgutierrez.demo.tinklabs.sample.data.model.ItemType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ImageViewHolder> {

    private List<Item> mItems;
    private OnItemClickListener mItemClickListener;

    @Inject
    public ItemsAdapter(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mItems = new ArrayList<>();
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (ItemType.fromCode(viewType)) {
            case IMAGE_ONLY:
                return createImageViewHolder(R.layout.item_image_only, parent);
            case NORMAL:
            default:
                return createItemViewHolder(R.layout.item, parent);
        }
    }

    @NonNull
    private ImageViewHolder createImageViewHolder(int layoutResourceId, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutResourceId, parent, false);
        return new ImageViewHolder(itemView, mItemClickListener);
    }

    @NonNull
    private ImageViewHolder createItemViewHolder(int layoutResourceId, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutResourceId, parent, false);
        return new ItemViewHolder(itemView, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        switch (ItemType.fromCode(holder.getItemViewType())) {
            case IMAGE_ONLY:
                bindImageViewHolder(holder, position);
                break;
            case NORMAL:
            default:
                bindItemViewHolder((ItemViewHolder) holder, position);
                break;
        }
    }

    private void bindItemViewHolder(ItemViewHolder holder, int position) {
        Item item = mItems.get(position);
        holder.loadFromUrl(item.imageUrl());
        holder.titleTextView.setText(item.title());
        holder.descriptionTextView.setText(item.description());
    }

    private void bindImageViewHolder(ImageViewHolder holder, int position) {
        Item item = mItems.get(position);
        holder.loadFromUrl(item.imageUrl());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).itemType().getCode();
    }

    class ItemViewHolder extends ImageViewHolder {

        @BindView(R.id.text_title)
        TextView titleTextView;
        @BindView(R.id.text_description)
        TextView descriptionTextView;

        public ItemViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
//            ButterKnife.bind(this, itemView);
        }

    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private final RequestManager mGlideRequestManager;

        @BindView(R.id.image)
        ImageView imageView;

        private OnItemClickListener mItemClickListener;

        public ImageViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mItemClickListener = onItemClickListener;
            ButterKnife.bind(this, itemView);
            mGlideRequestManager = Glide.with(itemView.getContext());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    mItemClickListener.onItemClick(position);
                }
            });
        }

        void loadFromUrl(String imageUrl) {
            mGlideRequestManager.load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
