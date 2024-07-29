package vn.io.vutiendat3601.beatbuddy.domain.search;

import java.util.Set;

public record SearchRequest(String keyword, Set<SearchType> types, Integer page, Integer size) {}
