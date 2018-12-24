package com.ecommerce.microcommerce.web.controller;
import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value="/Produits", method= RequestMethod.GET)
    public List<Product> listeProduits() {
        return productDao.findAll();
    }

    @RequestMapping(value = "/Produits/{id}", method = RequestMethod.GET)
    public String afficherUnProduit(@PathVariable int id) {
        return "Vous avez demandé un produit avec l'id  " + id;
    }

    @GetMapping(value = "/ProduitRaccourcis/{id}")
    public String afficherUnProduitRaccourci(@PathVariable int id) {
        return "Vous avez demandé un produit avec l'id  " + id;
    }

    //Récupérer un produit par son Id
    @GetMapping(value="/ProduitsModel/{id}")
    public Product afficherUnProduitModel(@PathVariable int id) {
        Product product=new Product(id, new String("Aspirateur"), 100 );
        return product;
    }

    //Récupérer un produit par son Id
    @GetMapping(value="/ProduitsAutowired/{id}")
    public Product afficherUnProduitAutowired(@PathVariable int id) {
        return productDao.findById(id);
    }

    //ajouter un produit
    @PostMapping(value = "/Produits")
    public void ajouterProduit(@RequestBody Product product) {
        productDao.save(product);
    }

    //ajouter un produit
    @PostMapping(value = "/saveProduit")
    public ResponseEntity<Void> ajouterProduitReponseHttp(@RequestBody Product product) {

        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
