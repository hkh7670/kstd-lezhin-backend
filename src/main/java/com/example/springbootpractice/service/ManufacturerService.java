package com.example.springbootpractice.service;

import com.example.springbootpractice.repository.ManufacturerRepository;
import com.example.springbootpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final UserRepository userRepository;
    private final ManufacturerRepository manufacturerRepository;


}
