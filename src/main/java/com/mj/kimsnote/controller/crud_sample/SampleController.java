package com.mj.kimsnote.controller.crud_sample;

import com.mj.kimsnote.common.apiResponse.ApiResponse;
import com.mj.kimsnote.service.crud_sample.SampleService;
import com.mj.kimsnote.vo.crud_sample.SampleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/sample")
public class SampleController {
    private final SampleService sampleService;

    @PostMapping("")
    public ApiResponse<SampleVo> sampleAdd(@RequestBody SampleVo request){
        SampleVo response = sampleService.addSample(request);
        return ApiResponse.success(response);
    }

    @GetMapping("")
    public ApiResponse<List<SampleVo>> sampleSelectAll(){
        List<SampleVo> response = sampleService.selectAllSample();
        return ApiResponse.success(response);
    }

    @GetMapping("/{sampleId}")
    public ApiResponse<SampleVo> sampleSelect(@PathVariable Long sampleId){
        SampleVo response = sampleService.selectSample(sampleId);
        return ApiResponse.success(response);
    }

    @PutMapping("")
    public ApiResponse<SampleVo> sampleModify(@RequestBody SampleVo request){
        SampleVo response = sampleService.modifySample(request);
        return ApiResponse.success(response);
    }

    @DeleteMapping("/{sampleId}")
    public ApiResponse<Boolean> sampleRemove(@PathVariable Long sampleId){
        Boolean response = sampleService.deleteSample(sampleId);
        return ApiResponse.success(response);
    }
}
