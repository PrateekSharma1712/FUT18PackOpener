package com.prateek.fut17packopener;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Prateek on 7/14/17.
 */

public class CustomBindingAdapters {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if(url != null) {
            //loadImage(imageView, url);
        }
    }

    public static void loadImage(final ImageView imageView, String url) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    imageView.setImageBitmap(response.getBitmap());
                }
            }
        });
    }
}
