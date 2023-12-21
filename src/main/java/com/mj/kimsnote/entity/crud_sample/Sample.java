package com.mj.kimsnote.entity.crud_sample;

import com.mj.kimsnote.vo.crud_sample.SampleVo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sampleId;
    private String name;
    private int age;

    @Builder
    public Sample(Long sampleId, String name, int age) {
        this.sampleId = sampleId;
        this.name = name;
        this.age = age;
    }

    public void updateSample(SampleVo sampleVo){
        this.name = sampleVo.getName();
        this.age = sampleVo.getAge();
    }
}
