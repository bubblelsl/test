package com.edu.portal.service;

import com.edu.common.bean.SearchResult;

public interface SearchService {
    SearchResult query(String q, int page);
}
