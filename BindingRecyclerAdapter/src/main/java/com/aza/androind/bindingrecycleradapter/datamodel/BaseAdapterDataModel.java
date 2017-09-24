package com.aza.androind.bindingrecycleradapter.datamodel;

import android.support.v7.widget.RecyclerView;

import com.aza.androind.bindingrecycleradapter.AdapterDataModel;

import java.lang.ref.WeakReference;

import static android.support.v7.widget.RecyclerView.NO_ID;

/**
 * Created by anton_azaryan on 18.09.17.
 */

public abstract class BaseAdapterDataModel<D> implements AdapterDataModel<D> {

    private WeakReference<RecyclerView.Adapter> adapterReference;

    RecyclerView.Adapter getAdapter() {
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

    @Override
    public long getStaticItemId(int position, D item) {
        return NO_ID;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    protected final void notifyDataSetChanged() {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    protected final void notifyItemRangeChanged(int positionStart, int itemsCount) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null)
            adapter.notifyItemRangeChanged(positionStart, itemsCount);
    }

    protected final void notifyItemRangeInserted(int positionStart, int itemCount) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null)
            adapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    protected final void notifyItemMoved(int fromPosition, int toPosition) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null)
            adapter.notifyItemMoved(fromPosition, toPosition);
    }

    protected final void notifyItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null)
            for (int i = 0; i < itemCount; i++)
                adapter.notifyItemMoved(fromPosition + i, toPosition + i);
    }

    protected final void notifyItemRangeRemoved(int positionStart, int itemCount) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null)
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

}
