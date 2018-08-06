package com.gtzhou.blog.utils;

import java.math.BigDecimal;
import java.util.Arrays;

public class MathUtils {

    public static BigDecimal add(BigDecimal... b) {
        return Arrays.stream(b).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
