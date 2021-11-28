package com.mysite.core.services;

import com.day.cq.search.result.SearchResult;

public interface SearchResultService {

    SearchResult createQuery(String keyword);
}
