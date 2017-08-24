package common.util;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/6/9.
 */
public class MongoUtil {

    public static void insertDocument(Document document) {
        // 1、实例化客户端
        MongoClient client = new MongoClient("192.168.31.146", 27017);
        // 2、获取数据库
        MongoDatabase database = client.getDatabase("1609B");
        // 3、获取集合
        MongoCollection<Document> comments = database.getCollection("comments");
        // 4、操作
        comments.insertOne(document);
        //关闭
        client.close();
    }

    public static void deleteDocument(Document document) {
        // 1、实例化客户端
        MongoClient client = new MongoClient("192.168.31.146", 27017);
        // 2、获取数据库
        MongoDatabase database = client.getDatabase("1609B");
        // 3、获取集合
        MongoCollection<Document> comments = database.getCollection("comments");
        // 4、操作
        comments.deleteOne(document);
        //关闭
        client.close();
    }

    public static void updataDocument(Document filter, Document update) {
        // 1、实例化客户端
        MongoClient client = new MongoClient("192.168.31.146", 27017);
        // 2、获取数据库
        MongoDatabase database = client.getDatabase("1609B");
        // 3、获取集合
        MongoCollection<Document> comments = database.getCollection("comments");
        // 4、操作
        comments.updateOne(filter, update);
        //关闭
        client.close();
    }

    public static MongoCursor<Document> findDocumentList(Document document, int skip, int limit, Document sort) {
        // 1、实例化客户端
        MongoClient client = new MongoClient("192.168.31.146", 27017);
        // 2、获取数据库
        MongoDatabase database = client.getDatabase("1609B");
        // 3、获取集合
        MongoCollection<Document> comments = database.getCollection("comments");
        // 4、操作
        FindIterable<Document> documents = comments.find(document);
        MongoCursor<Document> iterator = documents.iterator();
        //关闭
        client.close();
        return iterator;
    }

    public static long countDocumentNum(Document document) {
        // 1、实例化客户端
        MongoClient client = new MongoClient("192.168.31.146", 27017);
        // 2、获取数据库
        MongoDatabase database = client.getDatabase("1609B");
        // 3、获取集合
        MongoCollection<Document> comments = database.getCollection("comments");
        // 4、操作
        long count = comments.count(document);
        //关闭
        client.close();
        return count;
    }

    public static void main(String[] args) {

        List<ServerAddress> addresses = new ArrayList<ServerAddress>();
        ServerAddress address1 = new ServerAddress("192.168.31.146" , 27019);
        ServerAddress address2 = new ServerAddress("192.168.31.146" , 27020);
        ServerAddress address3 = new ServerAddress("192.168.31.146" , 27021);
        ServerAddress address4 = new ServerAddress("192.168.31.146" , 27022);
        addresses.add(address1);
        addresses.add(address2);
        addresses.add(address3);
        addresses.add(address4);

        // 1、实例化客户端
        MongoClient client = new MongoClient(addresses);
        // 2、获取数据库
        MongoDatabase database = client.getDatabase("1609B");
        // 3、获取集合
        MongoCollection<Document> comments = database.getCollection("student");
        Document document = new Document("name", "你好");
        // 4、操作
        comments.insertOne(document);
//        FindIterable<Document> documents = comments.find();
//        int i = 0;
//        for (Document d : documents) {
//            i++;
//        }
//        System.out.println(i);
        //关闭
        client.close();


    }

//    public static void main(String[] args) {
//
//        List<ServerAddress> addresses = new ArrayList<ServerAddress>();
//        ServerAddress address1 = new ServerAddress("192.168.31.146" , 27019);
//        addresses.add(address1);
//        // 1、实例化客户端
//        MongoClient client = new MongoClient(addresses);
//        // 2、获取数据库
//        MongoDatabase database = client.getDatabase("1609B");
//        // 3、获取集合
//        MongoCollection<Document> comments = database.getCollection("student");
//        Document document = new Document("name", "你好");
//        // 4、操作
//        comments.insertOne(document);
//        //关闭
//        client.close();
//    }

}
