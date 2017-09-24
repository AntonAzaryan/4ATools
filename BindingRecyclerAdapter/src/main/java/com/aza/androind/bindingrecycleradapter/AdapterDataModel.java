package com.aza.androind.bindingrecycleradapter;

import android.support.v7.widget.RecyclerView;

import com.aza.androind.bindingrecycleradapter.datamodel.SimpleAdapterDataModel;

/**
 * Created by anton_azaryan on 17.09.17.
 */

public interface AdapterDataModel<D> {

    AdapterDataModel EMPTY = new SimpleAdapterDataModel();

    @SuppressWarnings("unchecked")
    static <D> AdapterDataModel<D> empty() {
        return (AdapterDataModel<D>) EMPTY;
    }

    int getItemCount();

    D getItemByPosition(int position);

    /**
     * @return Id for item. If >= 0, this will set {@link
     * android.support.v7.widget.RecyclerView.Adapter#setHasStableIds(boolean)} to true.
     */
    long getStaticItemId(int position, D item);

    boolean hasStableIds();

    void onAttachedToAdapter(RecyclerView.Adapter adapter);

    void onDetachedFromAdapter();

}
