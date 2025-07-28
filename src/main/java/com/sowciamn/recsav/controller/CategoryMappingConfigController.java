package com.sowciamn.recsav.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sowciamn.recsav.model.entity.CategoryMappingConfig;
import com.sowciamn.recsav.model.request.CategoryMappingConfigRequest;
import com.sowciamn.recsav.service.CategoryMappingConfigService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category-mapping-configs")
@RequiredArgsConstructor
public class CategoryMappingConfigController {

    private final CategoryMappingConfigService categoryMappingConfigService;

    @GetMapping
    public List<CategoryMappingConfig> getCategoryMappingConfigs() {
        return categoryMappingConfigService.findAll();
    }

    @GetMapping("/{categoryMappingConfigSeq}")
    public CategoryMappingConfig getCategoryMappingConfig(@PathVariable Integer categoryMappingConfigSeq) {
        return categoryMappingConfigService.findById(categoryMappingConfigSeq);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryMappingConfig createCategoryMappingConfig(@RequestBody CategoryMappingConfigRequest request) {
        CategoryMappingConfig categoryMappingConfig = new CategoryMappingConfig();
        categoryMappingConfig.setMappingKeyNm(request.getMappingKeyNm());
        categoryMappingConfig.setCategoryCd(request.getCategoryCd());
        categoryMappingConfig.setLinkingExcludedFlg(request.getLinkingExcludedFlg());
        return categoryMappingConfigService.create(categoryMappingConfig);
    }

    @PutMapping("/{categoryMappingConfigSeq}")
    public CategoryMappingConfig updateCategoryMappingConfig(@PathVariable Integer categoryMappingConfigSeq, @RequestBody CategoryMappingConfigRequest request) {
        CategoryMappingConfig categoryMappingConfig = new CategoryMappingConfig();
        categoryMappingConfig.setMappingKeyNm(request.getMappingKeyNm());
        categoryMappingConfig.setCategoryCd(request.getCategoryCd());
        categoryMappingConfig.setLinkingExcludedFlg(request.getLinkingExcludedFlg());
        return categoryMappingConfigService.update(categoryMappingConfigSeq, categoryMappingConfig);
    }

    @DeleteMapping("/{categoryMappingConfigSeq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryMappingConfig(@PathVariable Integer categoryMappingConfigSeq) {
        categoryMappingConfigService.delete(categoryMappingConfigSeq);
    }
}
