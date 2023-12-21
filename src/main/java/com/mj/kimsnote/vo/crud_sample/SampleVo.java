package com.mj.kimsnote.vo.crud_sample;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SampleVo {
    private Long sampleId;
    private String name;
    private int age;

    @Builder
    public SampleVo(Long sampleId, String name, int age) {
        this.sampleId = sampleId;
        this.name = name;
        this.age = age;
    }
}
