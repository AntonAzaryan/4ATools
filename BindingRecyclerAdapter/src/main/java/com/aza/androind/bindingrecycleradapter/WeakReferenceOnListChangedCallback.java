package com.aza.androind.bindingrecycleradapter;

import android.databinding.ObservableList;
import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

class WeakReferenceOnListChangedCallback<D> extends ObservableList.OnListChangedCallback<ObservableList<D>> {

    private final WeakReference<BindingRecyclerViewAdapter<D>> adapterRef;
    private final Handler handler = new Handler(Looper.getMainLooper());

    WeakReferenceOnListChangedCallback(BindingRecyclerViewAdapter<D> adapter) {
        this.adapterRef = new WeakReference<>(adapter);
    }

    @Override
    public void onChanged(ObservableList sender) {
        if (!isOnMainThread()) {
            handler.post(() -> onChanged(sender));
            return;
        }

        BindingRecyclerViewAdapter<D> adapter = adapterRef.get();
        if (adapter == null) return;

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
        if (!isOnMainThread()) {
            handler.post(() -> onItemRangeChanged(sender, positionStart, itemCount));
            return;
        }

        BindingRecyclerViewAdapter<D> adapter = adapterRef.get();
        if (adapter == null) return;

        adapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
        if (!isOnMainThread()) {
            handler.post(() -> onItemRangeInserted(sender, positionStart, itemCount));
            return;
        }

        BindingRecyclerViewAdapter<D> adapter = adapterRef.get();
        if (adapter == null) return;

        adapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
        if (!isOnMainThread()) {
            handler.post(() -> onItemRangeMoved(sender, fromPosition, toPosition, itemCount));
            return;
        }

        BindingRecyclerViewAdapter<D> adapter = adapterRef.get();
        if (adapter == null) return;

        for (int i = 0; i < itemCount; i++) {
            adapter.notifyItemMoved(fromPosition + i, toPosition + i);
        }
    }

    @Override
    public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
        if (!isOnMainThread()) {
            handler.post(() -> onItemRangeRemoved(sender, positionStart, itemCount));
            return;
        }

        BindingRecyclerViewAdapter<D> adapter = adapterRef.get();
        if (adapter == null) return;

        adapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

    boolean isOnMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}

