package com.aza.androind.bindingrecycleradapter;

import android.support.annotation.LayoutRes;

/**
 * Created by Anton Azaryan on 11.01.2017.
 */

public interface BindingAdapterDelegate<D> {

    int BINDING_VARIABLE_NONE = 0;

    int getItemViewType(int position, D item);

    int getBindingVariable(@LayoutRes int layoutResId);

    ItemViewModel<D> createItemViewModel(int itemViewType);
}
