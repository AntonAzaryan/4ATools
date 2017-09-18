package com.aza.androind.bindingrecycleradapter.binding;

/**
 * Created by anton on 19.01.17.
 */

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aza.androind.bindingrecycleradapter.AdapterDataModel;
import com.aza.androind.bindingrecycleradapter.BindingAdapterDelegate;
import com.aza.androind.bindingrecycleradapter.BindingRecyclerViewAdapter;
import com.aza.androind.bindingrecycleradapter.StaticIdsHolder;

public class RecyclerViewBindingAdapters {

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"dataModel", "delegate", "adapter", "viewHolder", "staticIds"}, requireAll = false)
    public static <T> void setAdapter(RecyclerView recyclerView,
                                      AdapterDataModel<T> dataModel,
                                      BindingAdapterDelegate<T> delegate,
                                      BindingRecyclerViewAdapter.Factory adapterFactory,
                                      BindingRecyclerViewAdapter.ViewHolderFactory viewHolderFactory,
                                      StaticIdsHolder<T> staticItemsIds) {
        if (delegate == null) {
            throw new IllegalArgumentException("BindingAdapterDelegate must not be null");
        }
        if (adapterFactory == null) {
            adapterFactory = BindingRecyclerViewAdapter.Factory.DEFAULT;
        }
        BindingRecyclerViewAdapter<T> adapter = (BindingRecyclerViewAdapter<T>) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = adapterFactory.create(recyclerView, delegate);
            adapter.setDataModel(dataModel);
            adapter.setViewHolderFactory(viewHolderFactory);
            adapter.setStaticIdsHolder(staticItemsIds);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDataModel(dataModel);
        }
    }

    @BindingConversion
    public static BindingRecyclerViewAdapter.Factory toRecyclerViewAdapterFactory(final String className) {
        return new BindingRecyclerViewAdapter.Factory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> BindingRecyclerViewAdapter<T> create(RecyclerView recyclerView, BindingAdapterDelegate<T> arg) {
                try {
                    return (BindingRecyclerViewAdapter<T>) Class.forName(className).getConstructor(BindingAdapterDelegate.class).newInstance(arg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

    @BindingAdapter({"decoration"})
    public static void setItemDecoration(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration) {
        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration);
        }
    }

    @BindingAdapter({"decorations"})
    public static void setItemDecorations(RecyclerView recyclerView, RecyclerView.ItemDecoration[] itemDecorations) {
        if (itemDecorations != null) {
            for (RecyclerView.ItemDecoration decoration : itemDecorations) {
                recyclerView.addItemDecoration(decoration);
            }
        }
    }

    @BindingAdapter(value = {"onLoadMore", "visibleThreshold"}, requireAll = false)
    public static void onLoadMoreListener(RecyclerView recyclerView, LoadMoreTarget loadMoreTarget, final int visibleThreshold) {
        if (loadMoreTarget == null) {
            return;
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            LinearLayoutManager linearLayoutManager;
            int totalItemCount;
            int lastVisibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (linearLayoutManager == null) {
                    final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager == null)
                        return;

                    // also GridLayoutManager extends LinearLayoutManager
                    if (layoutManager instanceof LinearLayoutManager) {
                        linearLayoutManager = (LinearLayoutManager) layoutManager;
                    } else {
                        throw new IllegalArgumentException("Load more supported only for LinearLayoutManager! " +
                                "(need access to findLastVisibleItemPosition() method)");
                    }
                }

                if (loadMoreTarget.needMoreItems()) {
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loadMoreTarget.isLoading()
                            && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        recyclerView.post(loadMoreTarget::onLoadMore);
                    }
                }
            }
        });
    }
}