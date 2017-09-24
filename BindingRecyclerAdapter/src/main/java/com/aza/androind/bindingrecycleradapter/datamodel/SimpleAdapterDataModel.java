package com.aza.androind.bindingrecycleradapter.datamodel;

import android.support.v7.widget.RecyclerView;

import com.aza.androind.bindingrecycleradapter.AdapterDataModel;

/**
 * Created by anton_azaryan on 24.09.17.
 */

public class SimpleAdapterDataModel implements AdapterDataModel {

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public Object getItemByPosition(int position) {
        throw new IllegalStateException("Unsupported operation!");
    }

    @Override
    public long getStaticItemId(int position, Object item) {
        return -1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onAttachedToAdapter(RecyclerView.Adapter adapter) {
    }

    @Override
    public void onDetachedFromAdapter() {
    }
}
