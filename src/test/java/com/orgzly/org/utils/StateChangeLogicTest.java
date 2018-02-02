package com.orgzly.org.utils;

import com.orgzly.org.datetime.OrgRange;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StateChangeLogicTest {

    @Test
    public void testFromTodoToNext() {
        StateChangeLogic scl  = new StateChangeLogic(new HashSet<>(Arrays.asList("DONE")));
        scl.setState("NEXT", "TODO", null, null);
        assertEquals("NEXT", scl.getState());
        assertNull(scl.getClosed());
    }

    @Test
    public void testFromTodoToDONE() {
        StateChangeLogic scl  = new StateChangeLogic(new HashSet<>(Arrays.asList("DONE")));
        scl.setState("DONE", "TODO", null, null);
        assertEquals("DONE", scl.getState());
        assertNotNull(scl.getClosed());
    }

    @Test
    public void testFromDoneToCncl() {
        StateChangeLogic scl  = new StateChangeLogic(new HashSet<>(Arrays.asList("DONE", "CNCL")));
        scl.setState("CNCL", "DONE", null, null);
        assertEquals("CNCL", scl.getState());
        assertNotNull(scl.getClosed());
    }

    @Test
    public void testFromNoteToDoneWithRepeater() {
        StateChangeLogic scl  = new StateChangeLogic(new HashSet<>(Arrays.asList("DONE")));
        scl.setState("DONE", "NOTE", OrgRange.parse("<2018-02-06 Tue +7d>"), null);
        assertEquals("NOTE", scl.getState());
        assertEquals("<2018-02-13 Tue +7d>", scl.getScheduled().toString());
        assertNull(scl.getClosed());
    }

    @Test
    public void testFromNoteToTodoWithRepeater() {
        StateChangeLogic scl  = new StateChangeLogic(new HashSet<>(Arrays.asList("DONE")));
        scl.setState("NEXT", "NOTE", OrgRange.parse("<2018-02-06 Tue +7d>"), null);
        assertEquals("NEXT", scl.getState());
        assertEquals("<2018-02-06 Tue +7d>", scl.getScheduled().toString());
        assertNull(scl.getClosed());
    }
}