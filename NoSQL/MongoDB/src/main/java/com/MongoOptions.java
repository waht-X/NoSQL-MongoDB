package com;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.List;

public class MongoOptions {


    private MongoDatabase database = null;

    private MongoClient client;

    public MongoOptions(MongoDatabase database, MongoClient client) {
        this.database = database;
        this.client = client;
    }

    public MongoOptions(MongoClient client) {
        this.client = client;
    }

    public void show_dbs() {
        MongoIterable<String> dbs = client.listDatabaseNames();
        System.out.println("------------show dbs--------------");
        for (String db : dbs) {
            System.out.printf("dbname %s\n", db);
        }
    }

    public void show_collections() {
        MongoIterable<String> collections = database.listCollectionNames();
        MongoCursor<String> iterator = collections.iterator();
        System.out.println("-----------show collections-------------");
        while (iterator.hasNext()) {
            System.out.printf("collection %s\n", iterator.next());
        }
    }

    public void use_db(String dbname) {
        System.out.printf("-------------use %s-------------\n", dbname);
        this.database = client.getDatabase(dbname);
    }

    public void create_collection(String collection_name) {
        System.out.printf("-------create collection %s------\n", collection_name);
        database.createCollection(collection_name);
    }

    public void drop_collection(String collection_name) {
        System.out.printf("----------drop collection %s----------\n", collection_name);
        MongoCollection<Document> collection = database.getCollection(collection_name);
        collection.drop();
    }

    /**
     * 查看集合
     * @param collection_name 查看的集合的集合名
     */
    public void collection_find(String collection_name) {
        System.out.printf("--------------collection find(%s)----------\n", collection_name);
        MongoCollection<Document> collection = database.getCollection(collection_name);
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            System.out.printf("------------document------------\n");
            System.out.println(iterator.next());
        }
    }

    public void collection_insertOne(String collection_name, Document document) {
        System.out.printf("--------------collection insert(%s)----------\n", collection_name);
        MongoCollection<Document> collection = database.getCollection(collection_name);
        collection.insertOne(document);
    }

    public void collection_insertMany(String collection_name, List<Document> documents) {
        System.out.printf("--------------collection insertMany(%s)----------\n", collection_name);
        MongoCollection<Document> collection = database.getCollection(collection_name);
        collection.insertMany(documents);
    }

    /**
     * 更新文档
     * @param collection_name 需要进行更新的文档所在集合
     * @param fieldName 需要进行更新的所在的key
     * @param value 需要进行更新的所在的value
     * @param newDoc  进行更新的内容
     */
    public void collection_update(String collection_name, String fieldName, String value, Document newDoc) {
        System.out.printf("--------------collection update(%s)----------\n", collection_name);
        MongoCollection<Document> collection = database.getCollection(collection_name);
        collection.updateOne(Filters.eq(fieldName, value), new Document("$set", newDoc));
    }

    public void collection_delete(String collection_name, String fieldName, String value) {
        System.out.printf("--------------collection delete(%s)----------\n", collection_name);
        MongoCollection<Document> collection = database.getCollection(collection_name);
        collection.deleteOne(Filters.eq(fieldName, value));
    }



    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoClient getClient() {
        return client;
    }
}
