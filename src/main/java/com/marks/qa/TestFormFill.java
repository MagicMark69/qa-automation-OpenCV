package com.marks.qa;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestFormFill {

    public static void main(String[] args) throws Exception {

        //// START
        // Waits until logo is visible to confirm that it is in the correct website
        Functions.tryRun(() -> Functions.waitForTarget("W3S logo.png", 0.7, 10));

        // Fills in First Name form
        Functions.tryRun(() -> Shortcuts.click("First Name.png", "left", 0.7, 10));
        Shortcuts.deleteFiller();
        Functions.typeString("Mark Christian");
        System.out.println("First Name has been typed");
        Thread.sleep(500);

        // Fills in Last Name form
        // Tab
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        Shortcuts.deleteFiller();
        Functions.typeString("Sayson");
        System.out.println("Last Name has been typed");
        Thread.sleep(500);

        // Clicks submit button
        Functions.tryRun(() -> Shortcuts.click("Submit button.png", "center", 0.7, 10));

        // Confirmation
        Functions.tryRun(() -> Functions.waitForTarget("Form confirm.png", 0.7, 10));

        // Go back to site
        Functions.tryRun(() -> Shortcuts.click("W3S tab.png", "center", 0.7, 10));
        Functions.tryRun(() -> Shortcuts.click("page.png", "center", 0.7, 10));

        // Scrolls until it finds second form
        Functions.tryRun(() -> Functions.waitAndScroll("Second Form.png", 0.7, 30));

        // Fills in First Name form
        Functions.tryRun(() -> Shortcuts.click("First Name.png", "left", 0.7, 10));
        Shortcuts.deleteFiller();
        Functions.typeString("Mark Christian");
        System.out.println("First Name has been typed");
        Thread.sleep(500);

        // Fills in Last Name form
        // Tab
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        Shortcuts.deleteFiller();
        Functions.typeString("Sayson");
        System.out.println("Last Name has been typed");
        Thread.sleep(500);

        // Scrolls until it finds Radio blank
        Functions.tryRun(() -> Functions.waitAndScroll("Radio blank.png", 0.7, 30));

        // Radio button clicker and checker
        Functions.tryRun(() -> Shortcuts.click("Blank HTML.png", "left", 0.8, 10));
        Functions.tryRun(() -> Functions.waitForTarget("Radio HTML selected.png", 0.8, 10));
        Functions.tryRun(() -> Shortcuts.click("Blank CSS.png", "left", 0.8, 10));
        Functions.tryRun(() -> Functions.waitForTarget("Radio CSS selected.png", 0.8, 10));
        Functions.tryRun(() -> Shortcuts.click("Blank JS.png", "left", 0.8, 10));
        Functions.tryRun(() -> Functions.waitForTarget("Radio JS selected.png", 0.8, 10));

        // Scrolls until it finds CB blank
        Functions.tryRun(() -> Functions.waitAndScroll("CB blank.png", 0.7, 30));

        // Checklist clicker and checker
        Functions.tryRun(() -> Shortcuts.click("CB empty bike.png", "left", 0.8, 10));
        Functions.tryRun(() -> Shortcuts.click("CB bike selected.png", "left", 0.8, 10));
        Functions.tryRun(() -> Functions.waitForTarget("CB empty bike.png", 0.8, 10));
        Functions.tryRun(() -> Shortcuts.click("CB empty car.png", "left", 0.8, 10));
        Functions.tryRun(() -> Shortcuts.click("CB car selected.png", "left", 0.8, 10));
        Functions.tryRun(() -> Functions.waitForTarget("CB empty car.png", 0.8, 10));
        Functions.tryRun(() -> Shortcuts.click("CB empty boat.png", "left", 0.8, 10));
        Functions.tryRun(() -> Shortcuts.click("CB boat selected.png", "left", 0.8, 10));
        Functions.tryRun(() -> Functions.waitForTarget("CB empty boat.png", 0.8, 10));

        // End message
        System.out.println("[COMPLETE] FORM FILL Regression Test DONE!!!");

    }

}