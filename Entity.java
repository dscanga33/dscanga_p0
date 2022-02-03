package repo;

import java.util.LinkedList;

public class Entity
{
    private String name; //Table name
    private LinkedList<String> columnNames;//All column names
    private LinkedList<String> columnData;//Data type stored in each column
    private LinkedList<Boolean> isPrimary; // Not yet implemented, but will have the value True where the column with the primary key is
    private LinkedList<Row> rows; //All data stored in the table is stored in individual rows


    public Entity(String name)
    {
        this.name = name;
        this.columnNames = EntitiesSQL.getColumnNames(name);
        this.columnData = EntitiesSQL.getColumnData(name);
        rows = EntitiesSQL.setRows(this);//set rows takes an Entity so the existing entity is passed
    }

    public LinkedList<String> getColumnData() {
        return columnData;
    }

    public void setColumnData(LinkedList<String> columnData) {
        this.columnData = columnData;
    }

    public LinkedList<Boolean> getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(LinkedList<Boolean> isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(LinkedList<String> columnNames) {
        this.columnNames = columnNames;
    }

    public LinkedList<Row> getRows() {
        return rows;
    }

    public void setRows(LinkedList<Row> rows) {
        this.rows = rows;
    }

    public String toString()
    {
        String output = "Table name:  "+this.name +"\n";
                for(int i = 0;i < columnNames.size();i++)
                {
                    output+=columnNames.get(i)+"\t";
                }
                output+="\n";
                for(int i =0;i< rows.size();i++)
                {
                    output+=rows.get(i)+"\n";
                }
                return output;
    }
}
