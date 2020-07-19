package edu.ahs.robotics.pathfinder.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ADBInterfaceTest {
    @Test
    public void testSimpleAddition() {
        //List<String> output = ADBInterface.runProcess(true,"set", "/a", "2+2");

        //assertEquals(output.get(0), "4");
    }

    @Test
    public void testLaunchADB() {
        //List<String> output = ADBInterface.runProcess(true,"adb");8
        ADBInterface.receiveUDP();
    }
}