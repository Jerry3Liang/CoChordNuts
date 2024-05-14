package com.ispan.recordshop.cochordnuts.util;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private Integer fetch;
    private Integer offset;
    private Integer total;
    private List<T> results;
}
