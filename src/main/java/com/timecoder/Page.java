package com.timecoder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@RequiredArgsConstructor
public class Page {

    private int pageSize;
    private int pageNumber;
    @ApiModelProperty(example = "id")
    private String sortBy = "id";
    private Sort.Direction orderBy;
}
