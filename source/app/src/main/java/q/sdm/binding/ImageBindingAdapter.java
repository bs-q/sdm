package q.sdm.binding;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageBindingAdapter {

    private ImageBindingAdapter(){}

    @androidx.databinding.BindingAdapter("online_image")
    public static void setOnlineImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url)
                .centerInside()
                .into(view);
    }
}
