package com.aza.androind.bindingrecycleradapter.datamodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;

/**
 * Created by anton_azaryan on 18.09.17.
 */

public class ObservableListDataModel<D> extends AdapterListDataModel<D> {

    public ObservableListDataModel() {
        super(new ObservableArrayList<>());
        getList().addOnListChangedCallback(new OnListChangedCallbackImpl());
    }

    @Override
    public ObservableArrayList<D> getList() {
        return (ObservableArrayList<D>) dataList;
    }

    class OnListChangedCallbackImpl extends ObservableList.OnListChangedCallback<ObservableList<?>> {

        final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void onChanged(ObservableList sender) {
            if (!isOnMainThread()) {
                handler.post(() -> onChanged(sender));
                return;
            }

            RecyclerView.Adapter adapter = getAdapter();
            if (adapter == null) return;

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            if (!isOnMainThread()) {
                handler.post(() -> onItemRangeChanged(sender, positionStart, itemCount));
                return;
            }

            RecyclerView.Adapter adapter = getAdapter();
            if (adapter == null) return;

            adapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            if (!isOnMainThread()) {
                handler.post(() -> onItemRangeInserted(sender, positionStart, itemCount));
                return;
            }

            RecyclerView.Adapter adapter = getAdapter();
            if (adapter == null) return;

            adapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            if (!isOnMainThread()) {
                handler.post(() -> onItemRangeMoved(sender, fromPosition, toPosition, itemCount));
                return;
            }

            RecyclerView.Adapter adapter = getAdapter();
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

            RecyclerView.Adapter adapter = getAdapter();
            if (adapter == null) return;

            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    static boolean isOnMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
