package eangeli.stealthwatch;

import org.postgresql.util.PSQLException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * just a simple java test to test the queries instead.
 * Created by eangeli on 2/20/2017.
 */
public class PostgreSQLTest {
    PostgreSQL db;

    @BeforeClass(alwaysRun = true)
    public void initDB() throws SQLException, ClassNotFoundException, IOException {
        db = new PostgreSQL().db("memorg").user("eangeli").pw("stinkycheese");
        db.connect();
        File file = new File(getClass().getResource("/initdb.sql").toString().replace("file:/","").replace("file:",""));
        Path path = file.toPath();
        String[] schema = new String(Files.readAllBytes(path)).split(";");
        for(String sql:schema){
            try {
                doQuery(sql);
            }catch(PSQLException pe){
                //ignore query results as delete,drop, insert and update to not return a result set.
            }
        }
    }

    protected ResultSet doQuery(String query) throws SQLException, ClassNotFoundException {
        return db.doQuery(query);
    }

    @AfterClass(alwaysRun = true)
    public void closeDB() throws SQLException {
        if(db!=null) {
            db.close();
        }
    }

    protected List<Map<String, Object>> doSelect(String query) throws SQLException, ClassNotFoundException {
        ResultSet rs = doQuery(query);
        List<Map<String,Object>> result = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()){
            Map<String, Object> data = new HashMap<>();
            int cols = rsmd.getColumnCount();
            for (int c = 1; c < cols + 1; c++) {
                data.put(rsmd.getColumnLabel(c), rs.getObject(rsmd.getColumnLabel(c)));
            }
            result.add(data);
        }
        return result;
    }

    protected void addExpectedMI(List<Map<String,Object>> list, String name, String address, Object dues, String location){
        Map<String,Object> e1 = new HashMap<>();
        e1.put("name", name);
        e1.put("address", address);
        e1.put("dues", dues);
        e1.put("location", location);
        list.add(e1);
    }

    protected void addExpectedMAge(List<Map<String,Object>> list, int id, String name, String address, String phone, int age){
        Map<String,Object> e1 = new HashMap<>();
        e1.put("id", id);
        e1.put("name", name);
        e1.put("address", address);
        e1.put("phone", phone);
        e1.put("age", age);
        list.add(e1);
    }

    protected void addExpectedDues(List<Map<String,Object>> list, int id, String name, Object dues){
        Map<String,Object> e1 = new HashMap<>();
        e1.put("id", id);
        e1.put("name", name);
        e1.put("dues", dues);
        list.add(e1);
    }

    protected boolean checkLists(List<Map<String,Object>> expected,List<Map<String,Object>> actual){
        boolean result = true;
        for(Map<String,Object> e:expected){
            boolean matched = false;
            for(Map<String,Object> a:actual){
                int c = 0;
                for(String key:e.keySet()){
                    if(a.containsKey(key)){
                        if(a.get(key).equals(e.get(key))){
                            c++;
                        }
                    }
                }
                if(c==e.keySet().size()){
                    matched = true;
                    break;
                }
            }
            if(!matched){
                result = false;
                break;
            }
        }
        return result;
    }

    @Test(groups = {"db"})
    public void memberInfo() throws SQLException, ClassNotFoundException {
        String query = "select m.NAME, m.ADDRESS, o.DUES, o.LOCATION from members as m, organization as o where o.MEMBER_ID=m.ID;";
        List<Map<String,Object>> list = doSelect(query);
        List<Map<String,Object>> expected = new ArrayList<>();
        addExpectedMI(expected, "Alvin", "1 Treehouse Lane", new BigDecimal("2.00"), "north");
        addExpectedMI(expected, "Alvin", "1 Treehouse Lane", new BigDecimal("0.99"), "north");
        addExpectedMI(expected, "Alvin", "1 Treehouse Lane", new BigDecimal("0.99"), "north");
        addExpectedMI(expected, "Simon", "1 Treehouse Lane", new BigDecimal("2.00"), "south");
        addExpectedMI(expected, "Theodore", "1 Treehouse Lane", new BigDecimal("0.00"), "east");
        addExpectedMI(expected, "Dave", "1 Treehouse Lane", new BigDecimal("2.00"), "west");
        addExpectedMI(expected, "Dave", "1 Treehouse Lane", new BigDecimal("0.99"), "under the stairs");
        assertEquals(expected.size(), list.size());
        assertTrue(checkLists(expected, list));
    }

    @Test(groups = {"db"})
    public void memberOlderThen45() throws SQLException, ClassNotFoundException {
        String query = "select * from members where AGE>45;";
        List<Map<String,Object>> list = doSelect(query);
        List<Map<String,Object>> expected = new ArrayList<>();
        addExpectedMAge(expected, 3, "Theodore", "1 Treehouse Lane", "1234567890", 46);
        addExpectedMAge(expected, 4, "Dave", "1 Treehouse Lane", "1234567890", 50);
        assertEquals(expected.size(), list.size());
        assertTrue(checkLists(expected, list));
    }

    @Test(groups = {"db"})
    public void memberZeroDues() throws SQLException, ClassNotFoundException {
        String query = "select m.ID,m.NAME,d.dues from (select m.ID, sum(o.DUES) as dues " +
                "from members as m, organization as o where o.MEMBER_ID=m.ID GROUP BY m.ID) as d, members as m " +
                "where m.ID=d.ID and d.dues=0.00 or (m.ID not in (select MEMBER_ID from organization) and d.dues=0.00);";
        List<Map<String,Object>> list = doSelect(query);
        List<Map<String,Object>> expected = new ArrayList<>();
        addExpectedDues(expected, 3, "Theodore", new BigDecimal("0.00"));
        addExpectedDues(expected, 5, "Britney", new BigDecimal("0.00"));
        assertEquals(expected.size(), list.size());
        assertTrue(checkLists(expected, list));
    }
}
