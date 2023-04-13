package com.cookos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    public final int RC_IMAGE = 1;
    public final int RC_MUSIC = 2;

    private RecyclerView recyclerView;
    private Button button;
    private ArrayList<CreateList> createLists;
    public int currentlyPlaying = -1;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private MediaPlayer mediaPlayer = new MediaPlayer();

    private MainActivity activity;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {


        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity.getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);

        createLists = new ArrayList<>();
        updateView();

        button = view.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Audio"), RC_MUSIC);
        });
    }

    private void updateView() {
        var adapter = new MyAdapter(this, createLists);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RC_IMAGE:
                    var uriImage = data.getData();
                    var c = new CreateList();
                    c.setImage_URI(uriImage);

                    createLists.add(c);

                    Intent intent = new Intent();
                    intent.setType("audio/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Audio"), RC_MUSIC);
                    break;
                case RC_MUSIC:
                    var uriAudio = data.getData();

                    createLists.get(createLists.size() - 1).setAudio_uri(uriAudio);

                    updateView();
                    break;
            }
        }

    }

    public Context getApplicationContext() {
        return activity.getApplicationContext();
    }
}