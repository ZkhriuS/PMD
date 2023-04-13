package com.cookos;

import android.net.Uri;

public class CreateList {
    private String image_title;
    private Uri image_uri;

    public Uri getAudio_uri() {
        return audio_uri;
    }

    public void setAudio_uri(Uri audio_uri) {
        this.audio_uri = audio_uri;
    }

    private Uri audio_uri;

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String android_version_name) {
        this.image_title = android_version_name;
    }

    public Uri getImage_URI() {
        return image_uri;
    }

    public void setImage_URI(Uri uri) {
        this.image_uri = uri;
    }
}
