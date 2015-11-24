package org.kidinov.retr_rx_test.viewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import org.kidinov.retr_rx_test.BR;
import org.kidinov.retr_rx_test.R;

import me.tatarka.bindingcollectionadapter.ItemView;

public class PageViewModel {
    public final ObservableList<ItemViewModel> items = new ObservableArrayList<>();
    public final ItemView itemView = ItemView.of(BR.viewModel, R.layout.rv_item);
    public final ObservableField<ScreenState> screenState = new ObservableField<>();

    public enum ScreenState {
        LIST(0), PROGRESS(1), ERROR(2);

        public final int number;

        ScreenState(int number) {
            this.number = number;
        }
    }
}
