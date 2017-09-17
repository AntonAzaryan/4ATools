package com.aza.androind.bindingrecycleradapter.binding;

/**
 * Created by anton_azaryan on 17.09.17.
 */

public interface LoadMoreTarget {

    boolean needMoreItems();

    boolean isLoading();

    void onLoadMore();

}