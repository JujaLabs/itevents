package org.itevents.mybatis.mapper.util;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SQLBuilderTest {

    @Test
//    @Ignore
    public void test(){
        String expectedSQL = "SELECT * FROM events";
        SQLBuilder sqlBuilder = new SQLBuilder();
        List<Integer> listId = new ArrayList<>();
        listId.add(1);
        listId.add(3);
        String actualString = sqlBuilder.test(listId);
        assertEquals(expectedSQL, actualString);
    }

    @Test
    public void testId(){
        String expectedSQL = "SELECT *\nFROM events\nWHERE (id = #{id})";
        SQLBuilder sqlBuilder = new SQLBuilder();
        String actualString = sqlBuilder.selectEvent();
        assertEquals(expectedSQL, actualString);
    }
}
