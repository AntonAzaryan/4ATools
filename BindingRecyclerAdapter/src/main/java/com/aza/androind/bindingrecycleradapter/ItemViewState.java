package com.aza.androind.bindingrecycleradapter;

import android.content.Context;
import android.databinding.BaseObservable;

import java.lang.ref.WeakReference;

/**
 * Created by anton on 23.01.17.
 */

public abstract class ItemViewState<D> extends BaseObservable {

    D dataModel;
    WeakReference<BindingViewHolder<D>> viewHolderRef;

    public void onBindDataModel(int position, D model) {
        dataModel = model;
    }

    void setViewHolder(BindingViewHolder<D> viewHolder) {
        viewHolderRef = new WeakReference<>(viewHolder);
    }

    public D getDataModel() {
        return dataModel;
    }

    public int getAdapterPosition() {
        final BindingViewHolder<D> viewHolder = viewHolderRef.get();
        return viewHolder != null ? viewHolder.getAdapterPosition() : -1;
    }

    public final Context getContext() {
        final BindingViewHolder<D> viewHolder = viewHolderRef.get();
        return viewHolder != null ? viewHolder.binding.getRoot().getContext() : null;
    }

    public void onViewRecycled() {
    }

    public void onViewAttachedToWindow() {
    }

    public void onViewDetachedFromWindow() {
    }
}
