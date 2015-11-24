package org.kidinov.retr_rx_test.viewModel;

import org.kidinov.retr_rx_test.BR;
import org.kidinov.retr_rx_test.R;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter.ItemView;

public class ActivityViewModel {
    public final List<PageViewModel> pages = new ArrayList<>();
    public final ItemView pageView = ItemView.of(BR.viewModel, R.layout.page);
    public BindingViewPagerAdapter.PageTitles titles;
}
