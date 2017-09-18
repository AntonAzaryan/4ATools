package com.aza.androind.bindingrecycleradapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by anton_azaryan on 17.09.17.
 */

public class BindingViewHolder<D> extends RecyclerView.ViewHolder {

    final ViewDataBinding binding;
    ItemViewState<D> viewModel;

    public BindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    protected void onBindDataModel(int position, D dataModel) {
        if (viewModel != null) {
            viewModel.onBindDataModel(position, dataModel);
        }
    }

    @SuppressWarnings("unchecked")
    public <B extends ViewDataBinding> B getBinding() {
        return (B) binding;
    }

    public ItemViewState<D> getViewModel() {
        return viewModel;
    }

    protected void onViewRecycled() {
        viewModel.onViewRecycled();
    }

    protected void onViewAttachedToWindow() {
        viewModel.onViewAttachedToWindow();
    }

    protected void onViewDetachedFromWindow() {
        viewModel.onViewDetachedFromWindow();
    }
}