package com.edu.portal.service;

import com.edu.portal.bean.ItemCustomer;

public interface ItemService {
    ItemCustomer getInfo(long itemId);

    String getDesc(long itemId);

    String getParam(long itemId);
}
