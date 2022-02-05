import org.junit.jupiter.api.*;
import repo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Tests
{
    @BeforeAll
    public static void init()
    {
        String sql = "CREATE TABLE test (id serial primary key, test_name varchar, number integer, testboolean boolean)";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertTest()
    {
        Entities testEntity = new Entities();
        LinkedList<String> data = new LinkedList<>();
        data.add("hello");
        data.add("55");
        data.add("false");
        Row inserted = EntitiesSQL.insertRow(testEntity.getTableByName("test"), data);
        Assertions.assertEquals(inserted,testEntity.getTableByName("test").getRows().get(0));
    }
    @Test
    public void selectAllTest()
    {
        Entities testEntity = fillData();
        String result = EntitiesSQL.selectAll(testEntity.getTableByName("test"));
        Assertions.assertEquals(result,testEntity.getTableByName("test").toString());
    }
    @Test
    public void selectIDTest()
    {
        Entities testEntity = fillData();
        Row result = EntitiesSQL.selectByPrimary(testEntity.getTableByName("test"),"1");
        Assertions.assertEquals(result,testEntity.getTableByName("test").getRows().get(0));
    }
    @Test
    public void updateByIDTest()
    {
        Entities testEntity = fillData();
        Row before = EntitiesSQL.selectByPrimary(testEntity.getTableByName("test"),"1");
        LinkedList<String> data = new LinkedList<>();
        data.add("1");
        data.add("no");
        data.add("33");
        data.add("false");
        EntitiesSQL.updateByPrimary(testEntity.getTableByName("test"),data);
        Assertions.assertNotEquals(before,EntitiesSQL.selectByPrimary(testEntity.getTableByName("test"),"1"));
    }
    @Test
    public void deleteByIDTest()
    {
        Entities testEntity = fillData();
        Row before = EntitiesSQL.selectByPrimary(testEntity.getTableByName("test"),"1");
        EntitiesSQL.deleteByPrimary(testEntity.getTableByName("test"),"1");
        Assertions.assertNotEquals(before,testEntity.getTableByName("test").getRows().get(0));
    }
    @Test
    public void selectByColTest()
    {
        Entities testEntity = fillData();
        LinkedList result = EntitiesSQL.selectByColumn(testEntity.getTableByName("test"),"test_name","hey");
        Assertions.assertEquals(result.get(0),testEntity.getTableByName("test").getRows().get(0));
    }
    @Test
    public void updateByColumnTest()
    {
        Entities testEntity = fillData();
        LinkedList before = EntitiesSQL.selectByColumn(testEntity.getTableByName("test"),"test_name","hey");
        LinkedList<String> data = new LinkedList<>();
        data.add("hey");
        data.add("no");
        data.add("33");
        data.add("false");
        EntitiesSQL.updateByColumn(testEntity.getTableByName("test"),"test_name",data);
        Assertions.assertNotEquals(before.get(0),EntitiesSQL.selectByColumn(testEntity.getTableByName("test"),"test_name","no").get(0));
    }
    @Test
    public void deleteByColumnTest()
    {
        Entities testEntity = fillData();
        LinkedList<Row> before = EntitiesSQL.selectByColumn(testEntity.getTableByName("test"),"test_name","hey");
        EntitiesSQL.deleteByColumn(testEntity.getTableByName("test"),"test_name","hey");
        Assertions.assertNotEquals(before,EntitiesSQL.selectByColumn(testEntity.getTableByName("test"),"test_name","hey"));
    }
    @AfterEach
    public void reset()
    {
        String sql = "DROP TABLE test";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "CREATE TABLE test (id serial primary key, test_name varchar, number integer, testboolean boolean)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Entities fillData()
    {
        Entities testEntity = new Entities();
        LinkedList<String> data = new LinkedList<>();
        data.add("hey");
        data.add("55");
        data.add("false");
        Row inserted = EntitiesSQL.insertRow(testEntity.getTableByName("test"), data);
        for (int i =0;i<5;i++) {
            data.set(0,data.get(0)+"y");
            EntitiesSQL.insertRow(testEntity.getTableByName("test"), data);
        }
        return testEntity;
    }
    @AfterAll
    public static void finish() {
        String sql = "DROP TABLE test";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
