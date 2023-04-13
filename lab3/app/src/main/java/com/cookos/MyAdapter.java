package com.cookos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

    private ArrayList<CreateList> galleryList;
    private GalleryFragment fragment;

    public MyAdapter(GalleryFragment fragment, ArrayList<CreateList> galleryList) {
        this.galleryList = galleryList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {

        if (i == 0) {
            viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewHolder.img.setImageResource(R.drawable.add);
            viewHolder.img.setOnClickListener(v -> {
                var intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                fragment.startActivityForResult(Intent.createChooser(intent, "Select Picture"), fragment.RC_IMAGE);
            });

            return;
        }

        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageURI((galleryList.get(i - 1).getImage_URI()));
        viewHolder.img.setOnClickListener(v -> {
            try {
                if (fragment.getMediaPlayer().isPlaying()) {
                    if (fragment.currentlyPlaying == i - 1) {
                        Toast.makeText(fragment.getActivity(), "PAUSING", Toast.LENGTH_SHORT).show();
                        fragment.getMediaPlayer().pause();
                    }
                    else {
                        fragment.currentlyPlaying = i - 1;
                        fragment.getMediaPlayer().reset();
                        fragment.getMediaPlayer().setDataSource(fragment.getApplicationContext(), galleryList.get(i - 1).getAudio_uri());
                        fragment.getMediaPlayer().prepare();
                        fragment.getMediaPlayer().start();
                    }
                } else {
                    if (fragment.currentlyPlaying == i - 1) {
                        Toast.makeText(fragment.getActivity(), "RESUMING", Toast.LENGTH_SHORT).show();
                        fragment.getMediaPlayer().start();
                    }
                    else {
                        fragment.currentlyPlaying = i - 1;
                        fragment.getMediaPlayer().reset();
                        fragment.getMediaPlayer().setDataSource(fragment.getApplicationContext(), galleryList.get(i - 1).getAudio_uri());
                        fragment.getMediaPlayer().prepare();
                        fragment.getMediaPlayer().start();
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size() + 1;
    }
}
