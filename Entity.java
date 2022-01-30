package repo;

import java.util.LinkedList;

public class Entity
{
    private String name;
    //private int numCol; ?
    private LinkedList<String> columnNames;
    private LinkedList<String> columnDataTypes; //will this get used?
    private LinkedList<Row> rows;
    //rows should be used to store the data from the table, could be its own class?


    public Entity(String name)
    {
        this.name = name;
        columnNames = EntitiesSQL.getColumnNames(name);
        columnDataTypes = EntitiesSQL.getColumnData(name);
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

    public LinkedList<String> getColumnDataTypes() {
        return columnDataTypes;
    }

    public void setColumnDataTypes(LinkedList<String> columnDataTypes) {
        this.columnDataTypes = columnDataTypes;
    }

    public LinkedList<Row> getRows() {
        return rows;
    }

    public void setRows(LinkedList<Row> rows) {
        this.rows = rows;
    }

    public String toString()
    {
        String output = "Table name:"+this.name +"\n";
                for(int i = 0;i<columnNames.size();)
                {
                    output+=columnNames.get(i)+"\t";
                }
                for(int i =0;i< rows.size();i++)
                {
                    output+=rows.get(i);
                }
                return output;
    }
}
