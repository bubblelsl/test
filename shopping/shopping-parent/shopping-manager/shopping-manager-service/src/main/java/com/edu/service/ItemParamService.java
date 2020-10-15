package com.edu.service;

import com.edu.common.bean.EUDatagridResult;
import com.edu.common.bean.ShoppingResult;

public interface ItemParamService {
    EUDatagridResult getAll(int page, int rows);

    ShoppingResult getItemParamByCategoryId(Long categoryId);

    ShoppingResult insertItemParam(Long categoryId, String paramDate);
}
