package com.edu.service;

import com.edu.bean.TbContent;
import com.edu.common.bean.EUDatagridResult;
import com.edu.common.bean.ShoppingResult;

public interface ContentService {
    EUDatagridResult getAllByCategoryId(Long categoryId, int page, int rows);

    ShoppingResult insertContent(TbContent tbContent);
}
