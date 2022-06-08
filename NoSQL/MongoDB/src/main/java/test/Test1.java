package test;

import com.MongoOptions;
import com.utils.MongoDBUtils;

public class Test1 {
    public static void main(String[] args) {
        MongoDBUtils.closeLog();
        MongoOptions mongoOptions = new MongoOptions(MongoDBUtils.getMongoClient());
        mongoOptions.use_db("test");
        mongoOptions.collection_delete("firstCollection", "tag", "03");
        mongoOptions.collection_find("firstCollection");
    }
}
