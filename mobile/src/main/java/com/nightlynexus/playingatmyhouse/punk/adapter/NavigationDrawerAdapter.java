package com.nightlynexus.playingatmyhouse.punk.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nightlynexus.playingatmyhouse.R;
import com.nightlynexus.playingatmyhouse.punk.model.Picture;
import com.nightlynexus.playingatmyhouse.punk.widget.ForegroundImageView;

public class NavigationDrawerAdapter
        extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolderPicture> {

    private static final int VIEW_TYPE_PICTURE = R.layout.row_picture;

    private final Picture[] mPictures;
    private final OnPictureClickListener mPictureClickListener;

    public interface OnPictureClickListener {
        void onPictureClick(int adapterPosition);
    }

    public static class ViewHolderPicture extends RecyclerView.ViewHolder {

        public final ForegroundImageView imageView;

        public ViewHolderPicture(View v) {
            super(v);
            imageView = (ForegroundImageView) v;
        }
    }

    public NavigationDrawerAdapter(Picture[] pictures,
                                   @NonNull OnPictureClickListener pictureClickListener) {
        mPictures = pictures;
        mPictureClickListener = pictureClickListener;
    }

    @Override
    public ViewHolderPicture onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderPicture(LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolderPicture viewHolder, final int position) {
        final Picture picture = mPictures[position];
        viewHolder.imageView.setImageResource(picture.getImageResourceId());
        viewHolder.imageView.setForeground(viewHolder.imageView.getResources().getDrawable(
                picture.isChecked()
                        ? R.drawable.foreground_row_picture_checked
                        : R.drawable.foreground_row_picture));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPictureClickListener.onPictureClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPictures.length;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_PICTURE;
    }
}
