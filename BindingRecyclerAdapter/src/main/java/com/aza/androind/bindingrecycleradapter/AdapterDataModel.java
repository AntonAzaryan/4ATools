package com.aza.androind.bindingrecycleradapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by anton_azaryan on 17.09.17.
 */

public interface AdapterDataModel<D> {

    int getItemCount();

    D getItemByPosition(int position);

    void onAttachedToAdapter(RecyclerView.Adapter adapter);

    void onDetachedFromAdapter();

    @SuppressWarnings("unchecked")
    static <D> AdapterDataModel<D> empty() {
        return (AdapterDataModel<D>) EMPTY;
    }

    AdapterDataModel EMPTY = new AdapterDataModel() {

        @Override
        public int getItemCount() {
            return 0;
        }

        @Override
        public Object getItemByPosition(int position) {
            return null;
        }

        @Override
        public void onAttachedToAdapter(RecyclerView.Adapter adapter) {

        }

        @Override
        public void onDetachedFromAdapter() {
        }
    };
}
