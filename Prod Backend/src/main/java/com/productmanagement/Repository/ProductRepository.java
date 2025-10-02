package com.productmanagement.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.productmanagement.Model.Product;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final MongoCollection<Document> collection;

    @Autowired
    public ProductRepository(MongoDatabase database) {
        this.collection = database.getCollection("products");
    }

    public Product save(Product product) {
        Document doc = new Document("name", product.getName())
                .append("description", product.getDescription())
                .append("price", product.getPrice())
                .append("stock", product.getStock())
                .append("category", product.getCategory());
        collection.insertOne(doc);
        product.setId(doc.getObjectId("_id"));
        return product;
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        for (Document doc : collection.find()) {
            products.add(documentToProduct(doc));
        }
        return products;
    }

    public Product findById(String id) {
        Document doc = collection.find(new Document("_id", new ObjectId(id))).first();
        return doc != null ? documentToProduct(doc) : null;
    }

    public Product update(String id, Product product) {
        Document doc = new Document("name", product.getName())
                .append("description", product.getDescription())
                .append("price", product.getPrice())
                .append("stock", product.getStock())
                .append("category", product.getCategory());

        collection.updateOne(
                new Document("_id", new ObjectId(id)),
                new Document("$set", doc)
        );
        product.setId(new ObjectId(id));
        return product;
    }

    public void deleteById(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    private Product documentToProduct(Document doc) {
        Product product = new Product();
        product.setId(doc.getObjectId("_id"));
        product.setName(doc.getString("name"));
        product.setDescription(doc.getString("description"));
        product.setPrice(doc.getDouble("price"));
        product.setStock(doc.getInteger("stock"));
        product.setCategory(doc.getString("category"));
        return product;
    }
}
