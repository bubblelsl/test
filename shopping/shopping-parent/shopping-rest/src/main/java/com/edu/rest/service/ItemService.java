package com.edu.rest.service;

import com.edu.common.bean.ShoppingResult;

public interface ItemService {
    ShoppingResult getInfo(long itemId);

    ShoppingResult getDesc(long itemId);

    ShoppingResult getParam(long itemId);
}
