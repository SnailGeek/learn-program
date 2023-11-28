package com.snail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snail.domain.Product;

import java.util.List;

public interface ProductService extends IService<Product> {

    List<Product> findAll();

}
