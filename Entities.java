package repo;

import java.util.LinkedList;

//Entities stores all tables in the DB, maybe could be a static class?
public class Entities
{
    private static LinkedList<Entity> tableNames = new LinkedList<>();
    public Entities()
    {
        //Populates the list of tables with the existing tables in the database
        tableNames = this.populate();
    }

    private static LinkedList<Entity> populate()
    {
        return EntitiesSQL.populate();
    }
    public LinkedList<Entity> getTableNames()
    {
        return tableNames;
    }
}
