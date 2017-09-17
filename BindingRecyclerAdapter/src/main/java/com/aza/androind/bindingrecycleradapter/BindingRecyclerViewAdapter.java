package com.aza.androind.bindingrecycleradapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_ID;
import static com.aza.androind.bindingrecycleradapter.BindingAdapterDelegate.BINDING_VARIABLE_NONE;

/**
 * Created by Anton Azaryan on 10.01.2017.
 */

public class BindingRecyclerViewAdapter<D> extends RecyclerView.Adapter<BindingViewHolder<D>> {

    List<D> dataList;

    LayoutInflater inflater;

    ViewHolderFactory viewHolderFactory;
    StaticIdsHolder<D> staticIdsHolder;
    WeakReferenceOnListChangedCallback<D> observableListCallback;

    final BindingAdapterDelegate<D> bindingDelegate;

    @Nullable
    RecyclerView recyclerView;

    public BindingRecyclerViewAdapter(BindingAdapterDelegate<D> adapterDelegate) {
        dataList = Collections.emptyList();
        bindingDelegate = adapterDelegate;
    }

    WeakReferenceOnListChangedCallback<D> getOnListChangeCallback() {
        if (observableListCallback == null) {
            observableListCallback = new WeakReferenceOnListChangedCallback<>(this);
        }
        return observableListCallback;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView == null && dataList instanceof ObservableList) {
            ((ObservableList<D>) dataList).addOnListChangedCallback(getOnListChangeCallback());
        }
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView != null && dataList instanceof ObservableList) {
            ((ObservableList<D>) dataList).removeOnListChangedCallback(getOnListChangeCallback());
        }
        this.recyclerView = null;
    }

    @Override
    public BindingViewHolder<D> onCreateViewHolder(ViewGroup parent, @LayoutRes int layoutResId) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutResId, parent, false);
        //create ViewHolder
        BindingViewHolder<D> viewHolder = viewHolderFactory != null
                ? viewHolderFactory.createViewHolder(binding)
                : new BindingViewHolder<>(binding);

        int bindingVariable = bindingDelegate.getBindingVariable(layoutResId);
        //check if we need to bind viewModel to layout
        if (bindingVariable != BINDING_VARIABLE_NONE) {
            ItemViewModel<D> viewModel = bindingDelegate.createItemViewModel(layoutResId);
            //link viewHolder and viewModel
            viewHolder.viewModel = viewModel;
            viewModel.viewHolder = viewHolder;
            //bind viewModel to layout
            boolean bindingVariableExists = binding.setVariable(bindingVariable, viewModel);
            if (!bindingVariableExists) {
                ExceptionUtils.throwMissingVariable(binding, bindingVariable, layoutResId);
            }
            binding.executePendingBindings();
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<D> holder, int position) {
        holder.onBindDataModel(position, dataList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return bindingDelegate.getItemViewType(position, dataList.get(position));
    }

    @Override
    public void onViewAttachedToWindow(BindingViewHolder<D> holder) {
        holder.onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(BindingViewHolder<D> holder) {
        holder.onViewDetachedFromWindow();
    }

    @Override
    public void onViewRecycled(BindingViewHolder<D> holder) {
        holder.onViewRecycled();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return hasStableIds() && staticIdsHolder != null ?
                staticIdsHolder.getItemId(position, dataList.get(position)) :
                NO_ID;
    }

    public List<D> getDataList() {
        return dataList;
    }

    public void setDataList(@Nullable List<D> dataList) {
        if (dataList == null || dataList == this.dataList)
            return;

        if (recyclerView != null) {
            if (this.dataList instanceof ObservableList) {
                ((ObservableList<D>) this.dataList).removeOnListChangedCallback(getOnListChangeCallback());
            }
            if (dataList instanceof ObservableList) {
                ((ObservableList<D>) dataList).addOnListChangedCallback(getOnListChangeCallback());
            }
        }
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * Set the item id's for the items. If not null, this will set {@link
     * android.support.v7.widget.RecyclerView.Adapter#setHasStableIds(boolean)} to true.
     */
    public void setStaticIdsHolder(@Nullable StaticIdsHolder<D> staticIdsHolder) {
        this.staticIdsHolder = staticIdsHolder;
        setHasStableIds(staticIdsHolder != null);
    }

    public void setViewHolderFactory(@Nullable ViewHolderFactory viewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory;
    }

    public interface Factory {

        <D> BindingRecyclerViewAdapter<D> create(RecyclerView recyclerView, BindingAdapterDelegate<D> arg);

        BindingRecyclerViewAdapter.Factory DEFAULT = new BindingRecyclerViewAdapter.Factory() {
            @Override
            public <D> BindingRecyclerViewAdapter<D> create(RecyclerView recyclerView, BindingAdapterDelegate<D> arg) {
                return new BindingRecyclerViewAdapter<>(arg);
            }
        };
    }

    public interface ViewHolderFactory {
        <D> BindingViewHolder<D> createViewHolder(ViewDataBinding dataBinding);
    }
}
