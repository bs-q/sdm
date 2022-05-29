package q.sdm.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.data.model.api.response.product.ProductResponse;
import q.sdm.databinding.ActivitySearchBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;
import q.sdm.ui.base.activity.BaseRequestCallback;
import q.sdm.ui.product.ProductDetailActivity;
import q.sdm.ui.search.adapter.SearchAdapter;
import q.sdm.ui.search.result.SearchResultActivity;

public class SearchActivity extends BaseActivity<ActivitySearchBinding,SearchViewModel> implements SearchAdapter.SearchAdapterCallback {

    private SearchAdapter searchAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        setupAdapter();
        listenToSearchBar();
        listenToSearchProductResult();
        viewBinding.search.requestFocus();
    }
    private void listenToSearchBar(){
        viewModel.searchQuery.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                viewModel.queryThrottle.onNext(viewModel.searchQuery.get());
            }
        });
        viewBinding.search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (viewModel.productResponses == null || viewModel.productResponses.isEmpty()){
                        viewModel.showErrorMessage("Chúng tôi không thể tìm được kết quả phù hợp với tìm kiếm của bản");
                    } else {
                        myApplication().setSearchResults(viewModel.productResponses);
                        navigateToSearchResult();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void navigateToSearchResult(){
        Intent it = new Intent(this, SearchResultActivity.class);
        startActivity(it);
    }

    private void listenToSearchProductResult(){
        viewModel.listenToAutoComplete(new BaseRequestCallback<List<ProductResponse>>() {
            @Override
            public void doSuccess(List<ProductResponse> response) {
                if (response!= null){
                    searchAdapter.updateProducts(response);
                    viewModel.productResponses = response;
                }
            }
        });
    }

    private void setupAdapter(){
        viewBinding.asRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        searchAdapter = new SearchAdapter(this);
        viewBinding.asRv.setAdapter(searchAdapter);
    }

    @Override
    public void searchItemClick(ProductResponse result) {
        myApplication().setProductDetailItem(result);
        navigateToProductDetail();
    }
    private void navigateToProductDetail(){
        Intent it = new Intent(this, ProductDetailActivity.class);
        startActivity(it);
    }
}
