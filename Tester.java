package repo;

import java.util.LinkedList;

public class Tester
{
    public static void main(String[] args) {
        Entities testTables = new Entities();
        LinkedList<Entity> testerList = testTables.getTableNames();

        //Any tests can be performed with the testerList list
    }
}
