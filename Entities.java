package repo;

import java.util.LinkedList;

public class Entities
{
    /*
        Add ability to create table
     */
    private static LinkedList<String> tableNames = new LinkedList<>();
    public Entities()
    {
        tableNames = this.populate();
    }

    private static LinkedList<String> populate()
    {
        return EntitiesSQL.populate();
    }
    public LinkedList<String> getTableNames()
    {
        return tableNames;
    }
}
