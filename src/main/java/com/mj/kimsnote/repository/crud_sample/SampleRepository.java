package com.mj.kimsnote.repository.crud_sample;

import com.mj.kimsnote.entity.crud_sample.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {
}
