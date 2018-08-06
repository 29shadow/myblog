package com.gtzhou.blog.utils;


import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SpecificStringUtils {

    /**
     * Id拼接
     */
    public static String concatId(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return "\'\'";
        } else {
            return ids.stream().map(Object::toString).collect(Collectors.joining(","));
        }
    }

    public static <T> String map2Ids(List<T> list, Function<T, Integer> mapFunc) {
        return SpecificStringUtils.concatId(list.stream().map(mapFunc).collect(Collectors.toList()));
    }
}
