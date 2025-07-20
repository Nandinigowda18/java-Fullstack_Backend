package com.nandini.ecom_website.controller;

import com.nandini.ecom_website.model.Product;
import com.nandini.ecom_website.service.productService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController

@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
   private productService service;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getproducts(){
        return new ResponseEntity<>(service.getproducts(), HttpStatus.OK) ;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id ){
     Product p = service.getById(id);
     if(p != null)
        return new ResponseEntity<>(service.getById(id),HttpStatus.OK ) ;
     else
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                       @RequestPart MultipartFile imgFile){
        try{
            Product p = service.addProduct(product ,imgFile);
            return new ResponseEntity<>(p,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
   @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable int id){

      Product product =  service.getById(id);
      byte[] imageFile = product.getImageData();

      return ResponseEntity.ok()
              .contentType(MediaType.valueOf(product.getImageType()))
              .body(imageFile);

   }
    @CrossOrigin(origins = "http://localhost:5173")
   @PutMapping("/product/{id}")
    public ResponseEntity<String> updateByID(@PathVariable int id,@RequestPart Product product,
                                             @RequestPart MultipartFile imgFile){
       Product p = null;
       try {
           p = service.updateById(id , product, imgFile);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       if(p != null)
          return new  ResponseEntity<>("updated", HttpStatus.OK);
      else
          return  new ResponseEntity<>("failed to update", HttpStatus.BAD_REQUEST);

   }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id ){
       Product product = service.getById(id);
       if(product != null){
           service.deleteProduct(id);
           return new ResponseEntity<>("deleted successfully",HttpStatus.OK);
       }else
           return new ResponseEntity<>("Product not found ", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
       List<Product> p = service.serachProducts(keyword);
        return new ResponseEntity<>(p,HttpStatus.OK);

    }


}
