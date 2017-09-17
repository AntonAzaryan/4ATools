package com.aza.androind.bindingrecycleradapter;

import static android.support.v7.widget.RecyclerView.NO_ID;

/**
 * Created by anton on 06.03.17.
 */

public interface StaticIdsHolder<D> {

    static StaticIdsHolder of(final int... staticIds) {
        return new StaticIdsHolder<Object>() {
            final int[] ids = staticIds;

            @Override
            public long getItemId(int position, Object item) {
                return isStaticId(position) ? position : NO_ID;
            }

            boolean isStaticId(int position) {
                for (int id : ids) {
                    if (id == position) return true;
                }
                return false;
            }
        };
    }

    long getItemId(int position, D item);
}
