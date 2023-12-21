package com.mj.kimsnote.service.crud_sample;

import com.mj.kimsnote.common.apiException.ApiException;
import com.mj.kimsnote.common.apiException.ApiExceptionCode;
import com.mj.kimsnote.entity.crud_sample.Sample;
import com.mj.kimsnote.repository.crud_sample.SampleRepository;
import com.mj.kimsnote.vo.crud_sample.SampleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository sampleRepository;

    @Transactional
    public SampleVo addSample(SampleVo sampleVo){
        Sample sample = dtoToEntity(sampleVo);
        return entityToDto(sampleRepository.save(sample));
    }

    @Transactional(readOnly = true)
    public List<SampleVo> selectAllSample(){
        List<Sample> samples = sampleRepository.findAll();
        return samples.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SampleVo selectSample(Long sampleId){
        Sample findSample = sampleRepository.findById(sampleId)
                .orElseThrow(() -> new ApiException(ApiExceptionCode.NOT_FOUND_SAMPLE));
        return entityToDto(findSample);
    }

    @Transactional
    public SampleVo modifySample(SampleVo sampleVo){
        Sample findSample = sampleRepository.findById(sampleVo.getSampleId())
                .orElseThrow(() -> new ApiException(ApiExceptionCode.NOT_FOUND_SAMPLE));

        findSample.updateSample(sampleVo);
        Sample savedSample = sampleRepository.save(findSample);
        return entityToDto(savedSample);
    }

    @Transactional
    public Boolean deleteSample(Long sampleId){
        sampleRepository.deleteById(sampleId);
        return true;
    }

    private Sample dtoToEntity(SampleVo sampleVo){
        return Sample.builder()
                .name(sampleVo.getName())
                .age(sampleVo.getAge())
                .build();
    }

    private SampleVo entityToDto(Sample sample){
        return SampleVo.builder()
                .sampleId(sample.getSampleId())
                .name(sample.getName())
                .age(sample.getAge())
                .build();
    }
}
