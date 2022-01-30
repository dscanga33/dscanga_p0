package repo;

import java.util.LinkedList;

public class Row
{
    private int numCols;
    private LinkedList<String> colNames;
    private LinkedList<String> values;

    public Row(Entity table)
    {
        colNames = table.getColumnNames();
        numCols = colNames.size();
        values = null; //Needed?
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public LinkedList<String> getColNames() {
        return colNames;
    }

    public void setColNames(LinkedList<String> colNames) {
        this.colNames = colNames;
    }

    public LinkedList<String> getValues() {
        return values;
    }

    public void setValues(LinkedList<String> values) {
        this.values = values;
    }

    public String toString()
    {
        String output ="";
        for (int i =0;i<numCols;i++)
        {
            output+= values.get(i)+"\n";
        }
        return output;
    }
}
