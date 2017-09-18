package com.aza.androind.bindingrecycleradapter;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * Created by anton on 23.01.17.
 */

public abstract class ItemViewState<D> extends BaseObservable {

    D dataModel;
    BindingViewHolder<D> viewHolder; //TODO weak reference or leak

    public void onBindDataModel(int position, D model) {
        dataModel = model;
    }

    public D getDataModel() {
        return dataModel;
    }

    public int getAdapterPosition() {
        return viewHolder.getAdapterPosition();
    }

    public final Context getContext() {
        return viewHolder.binding.getRoot().getContext();
    }

    public void onViewRecycled() {
    }

    public void onViewAttachedToWindow() {
    }

    public void onViewDetachedFromWindow() {
    }
}
