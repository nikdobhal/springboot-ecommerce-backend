package com.ecommerce.project.payLoad;

import java.util.List;

public class CategoryResponse {

    private List<CategoryDTO> content;

    public List<CategoryDTO> getContent() {
        return content;
    }

    public void setContent(List<CategoryDTO> content) {
        this.content = content;
    }
}
