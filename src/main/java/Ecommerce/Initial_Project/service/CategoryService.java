package Ecommerce.Initial_Project.service;

import Ecommerce.Initial_Project.dto.request.CategoryRequestDTO;
import Ecommerce.Initial_Project.dto.response.CategoryResponseDTO;
import Ecommerce.Initial_Project.model.Category;
import Ecommerce.Initial_Project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> getAll() {
        List<Category> category = categoryRepository.findAll();

        return category.stream().map(cat -> CategoryResponseDTO.builder()
                .id(cat.getId()).title(cat.getTitle()).build()).collect(Collectors.toList());
    }

    public  CategoryResponseDTO create(CategoryRequestDTO request) {
        Category category = new Category();

        category.setId(UUID.randomUUID().toString());
        category.setTitle(request.getTitle());
        categoryRepository.save(category);

        return CategoryResponseDTO.builder().id(category.getId()).title(category.getTitle()).build();
    }

    public CategoryResponseDTO getById(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        return CategoryResponseDTO.builder().id(category.getId()).title(category.getTitle()).build();
    }

    public CategoryResponseDTO updateById(String id, CategoryResponseDTO request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        category.setTitle(request.getTitle());
        categoryRepository.save(category);

        return CategoryResponseDTO.builder().id(category.getId()).title(category.getTitle()).build();
    }

    public void delete(String id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(category);
    }
}
