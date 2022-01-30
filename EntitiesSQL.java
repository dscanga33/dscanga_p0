package repo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class EntitiesSQL
{
    private static final String SCHEMA_NAME = "public";
    public static LinkedList<String> populate()
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
            return tableNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public static void selectAll(Entity table)
    {
        String sql = "select * from "+table.getName();
        Connection conn = JDBCConnection.getConnection();
        table.setRows(null); //Reset the rows
        LinkedList<Row> row = new LinkedList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Row newRow = new Row(table);
                for(int i = 1;i<newRow.getNumCols()+1;i++) {
                    newRow.getValues().add(rs.getString(i));
                    table.getRows().add(newRow);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
