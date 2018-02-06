package com.russellgutierrez.demo.tinklabs.sample.ui.main;

import com.russellgutierrez.demo.tinklabs.sample.data.model.Item;
import com.russellgutierrez.demo.tinklabs.sample.ui.base.MvpView;

import java.util.List;

public interface ListMvpView extends MvpView {

    void showItems(List<Item> items, int newItemCount);

    void showItemsEmpty();

    void showError();

}
