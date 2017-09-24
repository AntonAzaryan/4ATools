package com.aza.androind.bindingrecycleradapter.datamodel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton_azaryan on 18.09.17.
 */

public class AdapterListDataModel<D> extends BaseAdapterDataModel<D> {

    List<D> dataList;

    public AdapterListDataModel() {
        this(new ArrayList<>());
    }

    public AdapterListDataModel(@NonNull List<D> dataList) {
        this.dataList = dataList;
    }

    public List<D> getList() {
        return dataList;
    }

    public void setDataList(@NonNull List<D> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
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
