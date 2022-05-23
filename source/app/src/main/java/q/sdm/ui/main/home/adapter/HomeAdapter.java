package q.sdm.ui.main.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import q.sdm.R;
import q.sdm.data.model.api.response.category.CategoryResponse;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.databinding.LayoutCategoryBarBinding;
import q.sdm.databinding.LayoutProductBinding;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder> {

    List<ProductResponse> productResponseList = new ArrayList<>();
    List<CategoryResponse> categoryResponseList = new ArrayList<>();
    CategoryAdapter categoryAdapter = new CategoryAdapter();

    public HomeAdapter(){
        createTestData();
    }

    private void createTestData(){
        String[] productImg = new String[]{"https://s3-alpha-sig.figma.com/img/1c91/f09f/9e7e8e97c84dab60de65f00962e8a68b?Expires=1653868800&Signature=hjDGy~qqRzlop3R9Vb~p87CxRYUVPCE3bZtl2P5zWiuutQQMxwMhjcmP58HfivqE2np6fRI1Z0NudDl6rrGJrxLD0jMlvqjo3anOjPteO3958yzegWynHPNIOiSNpMilbb36Rt~I20fn7HgsCNZ8lJWZLuEX04s~m6rBr~vvg3j32AzFzN2pGAHAm6IntynKTovI1vkJnWTBNeOlgpKb-mS1Z-B8B3xMcs-1pfY75txeuhyIPeKPW5LNtqNthQm9w40-QN0EKJNen9wGv0jj277fDCaQp9nk-4pPImhiIMgS6tAvAgUgcXdNo6Lxn6nnKrK1Ne6I0HIvSodsG7a7-w__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA",
        "https://s3-alpha-sig.figma.com/img/001d/d140/cb2b1136b621c895df255e67b1067464?Expires=1653868800&Signature=SevQgMN4L~s51xjuyXYcxSoemN-QPhEZ0eiRIG2ovI3-ucoKXsMoQvrsyBGuoiBsIHQI1sqHy5ccW3Gpd7j-iR9QQeSbZpxySNWEI2USWhPl9qci5gJPoBSIjYeBdehx-P3qAKeIcOpnGgLOuAZSo4xtCJ3dZ~3L-FWNOtjVY~5XLk~7bWihAXnaXZEf0yCTse2Q-5uC-Kz1FeQAjLQtVJxnuhqiHkvzjdMA1F-kXoomOTMXQO8VnjJRaP~dZvzflVoDfPJsFtZhYNDoA70fbOdNxSnDqhMov54SNC0GWD5vLFKjgeV3cX0EbU5xyy1-rVzqnYNfsHjGjFAZrIQN5A__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA",
        "https://s3-alpha-sig.figma.com/img/f5be/0447/490b0f380db6a0e731b304ec8ff8322a?Expires=1653868800&Signature=AaDM73lAf~YFD65ecistA5uixsz-HbQzDrrtmEMW5qlm3JKSCRAsdlUpb5Shhx9d1zAVg4o8PSgixF~QVcaIBk9pz~6I9UPllPdhbUHcnCHo--U54KM9owk8xPJenki45IAJCKuhduKR9NWwOFNJpJ1UtkG4hT9FWDVxOhKRCvp8R5ahXMgnFhDF3azAfi8Bd5JjrZSly7QsnTPkcVV4PtVQ-81nCm5FfZbGvghAPr3EaI6lSSYDNmflqn-CtpNGju5qMk8kUr2lKAIilOPgWo2-chjqvyxJOJgFSwTzAnl7YzRE2SXJAXyKlqC927Yc87Sz1F85tbs1ubj98cQFIA__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA"};
        for (int i = 0; i < productImg.length*3; i++) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProductImage(productImg[i%3]);
            productResponse.setProductName(String.valueOf(i%3));
            productResponse.setId((long) i);
            productResponse.setProductPrice(100000L);
            productResponseList.add(productResponse);

            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setName(String.valueOf(i%3));
            categoryResponse.setThumbnail(productImg[i%3]);
            categoryResponseList.add(categoryResponse);
        }

        categoryAdapter.categoryResponseList = categoryResponseList;

    }

    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            LayoutCategoryBarBinding layoutCategoryBarBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_category_bar,parent,false);
            layoutCategoryBarBinding.getRoot().setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new HomeAdapterViewHolder(layoutCategoryBarBinding);
        }
        LayoutProductBinding layoutProductBinding = LayoutProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HomeAdapterViewHolder(layoutProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (viewType == 0) {
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.layoutCategoryBarBinding.rv.setAdapter(categoryAdapter);
            holder.layoutCategoryBarBinding.rv.setLayoutManager(new LinearLayoutManager(holder.layoutCategoryBarBinding.getRoot().getContext(),LinearLayoutManager.HORIZONTAL,false));
            holder.layoutCategoryBarBinding.executePendingBindings();

        } else {
            holder.layoutProductBinding.setName(productResponseList.get(position-1).getProductName());
            holder.layoutProductBinding.setThumbnail(productResponseList.get(position-1).getProductImage());
            holder.layoutProductBinding.setMoney(productResponseList.get(position-1).getProductPrice());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        else return 1;
    }

    @Override
    public int getItemCount() {
        return productResponseList.size() + 1;
    }

    public class HomeAdapterViewHolder extends RecyclerView.ViewHolder {

        LayoutCategoryBarBinding layoutCategoryBarBinding;
        LayoutProductBinding layoutProductBinding;
        public HomeAdapterViewHolder(@NonNull LayoutCategoryBarBinding layoutCategoryBarBinding) {
            super(layoutCategoryBarBinding.getRoot());
            this.layoutCategoryBarBinding = layoutCategoryBarBinding;
        }
        public HomeAdapterViewHolder(@NonNull LayoutProductBinding layoutProductBinding) {
            super(layoutProductBinding.getRoot());
            this.layoutProductBinding = layoutProductBinding;
        }
    }
}
