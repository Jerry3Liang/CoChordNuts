package com.ispan.recordshop.cochordnuts.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageConverter {

	public static <T, U> Page<U> convertPage(Page<T> source, Function<T, U> converter) {
        List<U> content = source.getContent().stream().map(converter).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(source.getNumber(), source.getSize(), source.getSort());
        return new PageImpl<>(content, pageable, source.getTotalElements());
    }
	
}
