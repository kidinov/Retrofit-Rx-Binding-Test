package org.kidinov.retr_rx_test.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import org.kidinov.retr_rx_test.App;
import org.kidinov.retr_rx_test.R;
import org.kidinov.retr_rx_test.databinding.ActivityMainBinding;
import org.kidinov.retr_rx_test.model.Model;
import org.kidinov.retr_rx_test.network.Service;
import org.kidinov.retr_rx_test.viewModel.ActivityViewModel;
import org.kidinov.retr_rx_test.viewModel.ItemViewModel;
import org.kidinov.retr_rx_test.viewModel.PageViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private PageViewModel pageOneViewModel;
    private PageViewModel pageTwoViewModel;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = ((App) getApplicationContext()).getService();

        initViewModel();

        fetchData();
    }

    private void initViewModel() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ActivityViewModel activityViewModel = new ActivityViewModel();
        pageOneViewModel = new PageViewModel();
        pageTwoViewModel = new PageViewModel();
        activityViewModel.pages.add(pageOneViewModel);
        activityViewModel.pages.add(pageTwoViewModel);
        activityViewModel.titles = (position, item) -> {
            switch (position) {
                case 0:
                    return "TAB1";
                case 1:
                    return "TAB2";
                default:
                    return "";
            }
        };
        binding.setViewModel(activityViewModel);
        binding.executePendingBindings();
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void fetchData() {
        fetchPage(() -> service.getData1(20), pageOneViewModel);
        fetchPage(() -> service.getData2(55), pageTwoViewModel);
    }

    private void fetchPage(Func0<Observable<List<Model>>> func, PageViewModel pageViewModel) {
        pageViewModel.screenState.set(PageViewModel.ScreenState.PROGRESS);

        Subscription subscription = Observable.just(null)
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(x -> func.call())
                .flatMapIterable(list -> list)
                .map(model -> new ItemViewModel(model.getText(), model.getUrl()))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemViewModels -> displayFetchedData(pageViewModel, itemViewModels),
                        e -> handleFetchingError(pageViewModel, e));

        trackSubscription(subscription);
    }

    private void handleFetchingError(PageViewModel pageViewModel, Throwable e) {
        Log.e(getClass().getName(), "", e);
        pageViewModel.screenState.set(PageViewModel.ScreenState.ERROR);
    }

    private void displayFetchedData(PageViewModel pageViewModel, List<ItemViewModel> itemViewModels) {
        pageViewModel.items.clear();
        pageViewModel.items.addAll(itemViewModels);
        pageViewModel.screenState.set(PageViewModel.ScreenState.LIST);
    }

}