package com.utils;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDBUtils {

    private static Properties properties;
    private static MongoDatabase mongoDatabase;
    private static InputStream stream = null;
    private static String host;
    private static int port;
    private static String dbname;

    static {
        if (properties == null) {
            properties = new Properties();
        }


        try {
            stream = MongoDBUtils.class.getClassLoader().getResourceAsStream("mongodb.properties");
            properties.load(stream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        host = properties.getProperty("host");
        port = Integer.parseInt(properties.getProperty("port"));
        dbname = properties.getProperty("dbname");

    }

    public static MongoClient getMongoClient() {
        String addr = "mongodb://" + host + ":" + port;
        MongoClient mongoClient = MongoClients.create(addr);
        return mongoClient;
    }

    public static MongoDatabase getMongoConn() {
        MongoClient mongoClient = getMongoClient();
        mongoDatabase = mongoClient.getDatabase(dbname);
        return mongoDatabase;
    }

    public static void closeLog() {
        Logger logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.OFF);
    }

}
