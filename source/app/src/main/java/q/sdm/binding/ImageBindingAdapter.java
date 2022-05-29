package q.sdm.binding;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

public class ImageBindingAdapter {

    private ImageBindingAdapter(){}

    @androidx.databinding.BindingAdapter(value = {"online_image","holder_image"},requireAll = false)
    public static void setOnlineImage(ImageView view, String url, Drawable holder) {
        if (holder == null){
            Glide.with(view.getContext()).load(url)
                    .centerInside()
                    .into(view);
        } else {
            Glide.with(view.getContext()).load(url)
                    .centerInside()
                    .placeholder(holder)
                    .into(view);
        }

    }
    @androidx.databinding.BindingAdapter("online_image")
    public static void setOnlineImage(ShapeableImageView view, String url) {
        Glide.with(view.getContext()).load(url)
                .centerInside()
                .into(view);
    }
}
