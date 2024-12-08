package com.byron.interfaces;

import com.byron.factories.ItemFactory;

public interface IItemManager {

    IItemService getItemService();

    ItemFactory getItemFactory();
}
