package com.timecoder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Page {

    private int pageSize;
    private int pageNumber;

}
