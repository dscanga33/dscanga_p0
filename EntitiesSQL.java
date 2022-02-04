package repo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class EntitiesSQL
{
    private static final String SCHEMA_NAME = "public"; //Name of the schema being used

    /**
     * Used to initialize the Entities class object
     * @return A linked list of all existing tables is returned
     */
    public static LinkedList<Entity> populate()
    {
        String sql = "select table_name from information_schema.tables where table_schema = '"+SCHEMA_NAME+"';";
        Connection conn = JDBCConnection.getConnection();
        LinkedList<String> tableNames = new LinkedList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                tableNames.add(rs.getString(1));
            }
            LinkedList<Entity> entityList = new LinkedList<>();
            for(String name:tableNames)
            {
                entityList.add(new Entity(name));
            }
            return entityList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Used to initialize the column names list for an entity in the constructor
     * @param tableName The name of the table being initialized is passed in
     * @return Returns a linked list of all column names
     */
    public static LinkedList<String> getColumnNames(String tableName)
    {
        String sql = "select column_name from information_schema.columns where table_schema = '"+SCHEMA_NAME+"' and table_name = '"+tableName+"';";
        Connection conn = JDBCConnection.getConnection();
        LinkedList<String> tableNames = new LinkedList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                tableNames.add(rs.getString(1));
            }
            return tableNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Used to initialize the column data list for an entity in the constructor
     * @param tableName The name of the table being initialized is passed in
     * @return Returns a linked list of all column data types
     */
    public static LinkedList<String> getColumnData(String tableName)
    {
        String sql = "select data_type from information_schema.columns where table_schema = '"+SCHEMA_NAME+"' and table_name = '"+tableName+"';";
        Connection conn = JDBCConnection.getConnection();
        LinkedList<String> columnNames = new LinkedList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                columnNames.add(rs.getString(1));
            }
            return columnNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Used to initialize the existing rows in the table
     * @param table The table being passed in
     * @return Returns a linked list of all rows in the table
     */
    public static LinkedList<Row> setRows(Entity table)
    {
        String sql = "select * from "+table.getName();
        Connection conn = JDBCConnection.getConnection();
        LinkedList rows = new LinkedList<Row>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return buildRows(table, rows, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static LinkedList buildRows(Entity table, LinkedList rows, ResultSet rs) throws SQLException {
        while (rs.next())
        {
            Row newRow = new Row(table);
            for(int i = 1;i<newRow.getNumCols()+1;i++) {
                newRow.getValues().add(rs.getString(i));
            }
            rows.add(newRow);
        }
        return rows;
    }

    /**
     * Inserts a new row into the requested table with the data defined
     * @param table The target table
     * @param data All data to be inserted, in order
     * @return returns the new row that was created
     */
    public static Row insertRow(Entity table, LinkedList<String> data)
    {
        String sql = "INSERT INTO "+table.getName() +" VALUES (";
        for(int i =0;i<table.getColumnNames().size();i++)
        {
            if(table.getColumnData().get(i)=="integer" && i==table.getPrimaryColumnIndex())//using default for primary keys that are numbers
            {
                sql+=" default";
            }
            else {
                sql += " ?";
            }
            if(i!=table.getColumnNames().size()-1)//Inserting a comma if it is not the last column
            {
                sql+=",";
            }
        }
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            //setting data to be inserted
            int x = 0; //needed to increment data inside the for loop as it is not 1 indexed like PreparedStatement
            for(int i =1;i<=table.getColumnNames().size();i++) //<= used here because PreparedStatement is 1 indexed
            {
                ps.setString(i,data.get(x));
                x++;//incrementing x
            }
            ResultSet rs = ps.executeQuery();

            Row newRow = new Row(table);
            for(int i = 1;i<newRow.getNumCols()+1;i++) {
                newRow.getValues().add(rs.getString(i));
            }

            return newRow;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method finds the column that the primary key is located in and returns the index as an int
     * @param table the table being searched
     * @return returns the index of the found primary key, or -1 if none is found
     */
    public static int getPrimaryColumn(Entity table)
    {
        //This extremely long sql statement returns the column name where the primary key is
        String sql = "SELECT pg_attribute.attname, format_type(pg_attribute.atttypid, pg_attribute.atttypmod) FROM pg_index, pg_class, pg_attribute, pg_namespace WHERE pg_class.oid = '"+table.getName()+"'::regclass AND indrelid = pg_class.oid AND nspname = '"+SCHEMA_NAME+"' AND pg_class.relnamespace = pg_namespace.oid AND pg_attribute.attrelid = pg_class.oid AND pg_attribute.attnum = any(pg_index.indkey) AND indisprimaryg_class.oid AND pg_attribute.attnum = any(pg_index.indkey) AND indisprimary;";
        Connection conn = JDBCConnection.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String primaryCol = rs.getString(1);
            for (int i =0; i <table.getColumnNames().size();i++)
            {
                if(primaryCol.equals(table.getColumnNames().get(i)));
                {
                    return i;
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
