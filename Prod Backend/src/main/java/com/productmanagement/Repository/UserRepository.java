package com.productmanagement.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.productmanagement.Model.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final MongoCollection<Document> collection;

    @Autowired
    public UserRepository(MongoDatabase database) {
        this.collection = database.getCollection("users");
    }

    public void save(User user) {
        Document doc = new Document("email", user.getEmail())
                .append("password", user.getPassword())
                .append("role", user.getRole());
        collection.insertOne(doc);
    }

    public User findByEmail(String email) {
        Document doc = collection.find(new Document("email", email)).first();
        if (doc == null) return null;

        User user = new User();
        user.setId(doc.getObjectId("_id"));
        user.setEmail(doc.getString("email"));
        user.setPassword(doc.getString("password"));
        user.setRole(doc.getString("role"));
        return user;
    }

    public boolean existsByEmail(String email) {
        return collection.find(new Document("email", email)).first() != null;
    }
}
