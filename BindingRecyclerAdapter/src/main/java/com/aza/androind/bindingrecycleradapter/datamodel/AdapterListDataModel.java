package com.aza.androind.bindingrecycleradapter.datamodel;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by anton_azaryan on 18.09.17.
 */

public class AdapterListDataModel<D> extends BaseAdapterDataModel<D> {

    final List<D> dataList;

    public AdapterListDataModel(@NonNull List<D> dataList) {
        this.dataList = dataList;
    }

    public List<D> getList() {
        return dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public D getItemByPosition(int position) {
        return dataList.get(position);
    }

}
