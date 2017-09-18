package com.aza.androind.bindingrecycleradapter.datamodel;

import android.support.v7.widget.RecyclerView;

import com.aza.androind.bindingrecycleradapter.AdapterDataModel;

import java.lang.ref.WeakReference;

/**
 * Created by anton_azaryan on 18.09.17.
 */

public abstract class BaseAdapterDataModel<D> implements AdapterDataModel<D> {

    private WeakReference<RecyclerView.Adapter> adapterReference;

    protected RecyclerView.Adapter getAdapter() {
        return adapterReference != null ? adapterReference.get() : null;
    }

    @Override
    public void onAttachedToAdapter(RecyclerView.Adapter adapter) {
        adapterReference = new WeakReference<>(adapter);
    }

    @Override
    public void onDetachedFromAdapter() {
        adapterReference = null;
    }

}
