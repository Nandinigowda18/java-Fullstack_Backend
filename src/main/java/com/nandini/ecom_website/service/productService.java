package com.nandini.ecom_website.service;

import com.nandini.ecom_website.model.Product;
import com.nandini.ecom_website.repo.productRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class productService {

    @Autowired
     private productRepo repo;

    public List<Product> getproducts() {
        return repo.findAll();
    }

    public Product getById(int id ) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imgFile) throws IOException {

        product.setImageName(imgFile.getOriginalFilename());
        product.setImageType(imgFile.getContentType());
        product.setImageData(imgFile.getBytes());
        return repo.save(product);

    }

    public Product updateById(int id, Product product, MultipartFile imgFile) throws IOException {
        product.setImageName(imgFile.getOriginalFilename());
        product.setImageType(imgFile.getContentType());
        product.setImageData(imgFile.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> serachProducts(String keyword) {

        return repo.searchByKeyword(keyword);
    }
}
