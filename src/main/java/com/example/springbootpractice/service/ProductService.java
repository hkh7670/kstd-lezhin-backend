package com.example.springbootpractice.service;

import com.example.springbootpractice.exception.ApiErrorException;
import com.example.springbootpractice.exception.ErrorCode;
import com.example.springbootpractice.model.dto.CreateProductRequest;
import com.example.springbootpractice.model.entity.ManufacturerEntity;
import com.example.springbootpractice.model.entity.ProductEntity;
import com.example.springbootpractice.repository.ManufacturerRepository;
import com.example.springbootpractice.repository.ProductRepository;
import com.example.springbootpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;

    public long createProduct(CreateProductRequest request) {
        ManufacturerEntity manufacturer = manufacturerRepository.findById(request.manufacturerSeq())
            .orElseThrow(() -> new ApiErrorException(ErrorCode.NOT_FOUND));
        ProductEntity product = ProductEntity.of(request, manufacturer);

        return productRepository.save(product)
            .getSeq();
    }


}
