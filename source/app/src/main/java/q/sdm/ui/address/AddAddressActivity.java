package q.sdm.ui.address;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

import q.sdm.BR;
import q.sdm.R;
import q.sdm.databinding.ActivityUpdateLocationBinding;
import q.sdm.di.component.ActivityComponent;
import q.sdm.ui.base.activity.BaseActivity;

public class AddAddressActivity extends BaseActivity<ActivityUpdateLocationBinding,AddAddressViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_update_location;
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
        setupAutoComplete();
    }
    private void setupAutoComplete(){
        ArrayList<String> items = new ArrayList<String>(Arrays.asList("List Item A", "List Item B"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item,items);
        viewBinding.provinceAuto.setAdapter(adapter);
        viewBinding.provinceAuto.setShowSoftInputOnFocus(false);
        viewBinding.provinceAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                adapter.add("a");
                adapter.add("b");
                adapter.add("c");
                adapter.notifyDataSetChanged();
            }
        });

    }

}
