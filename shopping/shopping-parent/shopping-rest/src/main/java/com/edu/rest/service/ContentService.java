package com.edu.rest.service;

import java.util.List;
import java.util.Map;

public interface ContentService {
    List<Map<String, Object>> getAllByCategoryId(Long categoryId);
}
