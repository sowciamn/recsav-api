package com.sowciamn.recsav.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sowciamn.recsav.model.entity.Category;
import com.sowciamn.recsav.model.request.CategoryRequest;
import com.sowciamn.recsav.service.CategoryService;
import com.sowciamn.recsav.controller.CategoryController;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    private Category category1;
    private Category category2;
    private CategoryRequest categoryRequest;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setCategoryCd(1);
        category1.setCategoryNm("食費");
        category1.setCategoryType("2");
        category1.setDisplayOrder(10);

        category2 = new Category();
        category2.setCategoryCd(2);
        category2.setCategoryNm("交通費");
        category2.setCategoryType("2");
        category2.setDisplayOrder(20);

        categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryNm("娯楽費");
        categoryRequest.setCategoryType("2");
        categoryRequest.setDisplayOrder(30);
    }

    @Test
    void getAllCategories_shouldReturnListOfCategories() throws Exception {
        List<Category> allCategories = Arrays.asList(category1, category2);
        when(categoryService.findAllCategories()).thenReturn(allCategories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].categoryCd").value(category1.getCategoryCd()))
                .andExpect(jsonPath("$[0].categoryNm").value(category1.getCategoryNm()))
                .andExpect(jsonPath("$[1].categoryCd").value(category2.getCategoryCd()));
    }

    @Test
    void createCategory_shouldReturnCreatedCategory() throws Exception {
        Category createdCategory = new Category();
        createdCategory.setCategoryCd(3);
        createdCategory.setCategoryNm(categoryRequest.getCategoryNm());
        createdCategory.setCategoryType(categoryRequest.getCategoryType());
        createdCategory.setDisplayOrder(categoryRequest.getDisplayOrder());

        when(categoryService.createCategory(any(CategoryRequest.class))).thenReturn(createdCategory);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryCd").value(createdCategory.getCategoryCd()))
                .andExpect(jsonPath("$.categoryNm").value(createdCategory.getCategoryNm()));
    }

    @Test
    void updateCategory_shouldReturnUpdatedCategory() throws Exception {
        Integer categoryCdToUpdate = 1;
        Category updatedCategory = new Category();
        updatedCategory.setCategoryCd(categoryCdToUpdate);
        updatedCategory.setCategoryNm("更新食費");
        updatedCategory.setCategoryType("2");
        updatedCategory.setDisplayOrder(10);

        CategoryRequest updateRequest = new CategoryRequest();
        updateRequest.setCategoryNm("更新食費");
        updateRequest.setCategoryType("2");
        updateRequest.setDisplayOrder(10);

        when(categoryService.updateCategory(eq(categoryCdToUpdate), any(CategoryRequest.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/api/categories/{categoryCd}", categoryCdToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryCd").value(updatedCategory.getCategoryCd()))
                .andExpect(jsonPath("$.categoryNm").value(updatedCategory.getCategoryNm()));
    }

    @Test
    void deleteCategory_shouldReturnNoContent() throws Exception {
        Integer categoryCdToDelete = 1;
        doNothing().when(categoryService).deleteCategory(categoryCdToDelete);

        mockMvc.perform(delete("/api/categories/{categoryCd}", categoryCdToDelete))
                .andExpect(status().isNoContent());
    }
}
