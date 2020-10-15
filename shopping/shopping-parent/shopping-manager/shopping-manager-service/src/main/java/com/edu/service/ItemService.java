package com.edu.service;

import com.edu.bean.TbItem;
import com.edu.common.bean.EUDatagridResult;
import com.edu.common.bean.ShoppingResult;

public interface ItemService {
    EUDatagridResult getAll(int page, int rows);

    ShoppingResult insertItem(TbItem item, String desc, String itemParams);
}
