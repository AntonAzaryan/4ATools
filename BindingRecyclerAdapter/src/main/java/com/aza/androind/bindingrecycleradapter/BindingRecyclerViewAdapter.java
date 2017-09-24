package com.aza.androind.bindingrecycleradapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import static android.support.v7.widget.RecyclerView.NO_ID;
import static com.aza.androind.bindingrecycleradapter.BindingAdapterDelegate.BINDING_VARIABLE_NONE;

/**
 * Created by Anton Azaryan on 10.01.2017.
 */

public class BindingRecyclerViewAdapter<D> extends RecyclerView.Adapter<BindingViewHolder<D>> {

    AdapterDataModel<D> dataModel;

    protected LayoutInflater inflater;

    protected ViewHolderFactory viewHolderFactory;
    protected StaticIdsHolder<D> staticIdsHolder;

    final BindingAdapterDelegate<D> bindingDelegate;
    boolean attachedToRecyclerView;

    public BindingRecyclerViewAdapter(BindingAdapterDelegate<D> adapterDelegate) {
        dataModel = AdapterDataModel.empty();
        bindingDelegate = adapterDelegate;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        attachedToRecyclerView = true;
        dataModel.onAttachedToAdapter(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        attachedToRecyclerView = false;
        dataModel.onDetachedFromAdapter();
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
            ItemViewState<D> viewModel = bindingDelegate.createItemViewState(layoutResId);
            //link viewHolder and viewModel
            viewHolder.viewModel = viewModel;
            viewModel.setViewHolder(viewHolder);
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
        holder.onBindDataModel(position, dataModel.getItemByPosition(position));
    }

    @Override
    public int getItemViewType(int position) {
        return bindingDelegate.getItemViewType(position, dataModel.getItemByPosition(position));
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
        return dataModel.getItemCount();
    }

    @Override
    public long getItemId(int position) {
        return hasStableIds() && staticIdsHolder != null ?
                staticIdsHolder.getItemId(position, dataModel.getItemByPosition(position)) :
                NO_ID;
    }

    public AdapterDataModel<D> getDataModel() {
        return dataModel;
    }

    public void setDataModel(@NonNull AdapterDataModel<D> dataModel) {
        if (dataModel == this.dataModel)
            return;

        if (attachedToRecyclerView) {
            this.dataModel.onDetachedFromAdapter();
            dataModel.onAttachedToAdapter(this);
        }

        this.dataModel = dataModel;
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
