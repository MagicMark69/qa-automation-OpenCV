package com.marks.qa;

import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestTextFile {

    public static void main(String[] args) throws Exception{

        // Opens notepad
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/searchbar.png", "center", 0.7, 10));
        Thread.sleep(1000);
        Functions.typeString("not");
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/notepad.png", "center", 0.7, 10));
        Thread.sleep(2000);
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/fullscreen.png", "center", 0.7, 10));
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/newtext.png", "center", 0.7, 10));
        Thread.sleep(100);

        // Types text file
        Functions.typeString("Hello World");
        Shortcuts.pressEnter();
        Functions.typeString("This is only for Testing");
        Shortcuts.pressEnter();
        Functions.typeString("Delete Afterwards");
        System.out.println("Text File has been filled out!");

        // Saves file as "temp.txt" in desktop
        Robot robot = new Robot();
        // Ctrl + S (Save)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(1000);
        Functions.typeString("temp");
        // Hover on scrollable area (in case scrolling is needed for when desktop isn't visible)
        Functions.tryRun(() -> Shortcuts.hoverOnScroll());
        // Move 100 pixels down
        Point currentPos = MouseInfo.getPointerInfo().getLocation();
        int downX = currentPos.x;
        int downY = currentPos.y + 100;
        robot.mouseMove(downX, downY);
        Functions.tryRun(() -> Functions.waitAndScroll("ForTextFile/desktop.png", 0.7, 10));
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/desktop.png", "center", 0.7, 10));
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/save.png", "center", 0.7, 10));

        // Opens text file via desktop. Renames and deletes.
        Shortcuts.clickShowDesktop();
        Thread.sleep(1000);
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/temp.png", "center", 0.8, 10));
        Functions.secondClick();
        Functions.tryRun(() -> Functions.waitForTarget("ForTextFile/textsample.png", 0.8, 10));
        Shortcuts.clickShowDesktop();
        Thread.sleep(1000);
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/temp.png", "center", 0.8, 10));
        Functions.rightClick();
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/rename.png", "center", 0.7, 10));
        Thread.sleep(1000);
        Functions.typeString("delete");
        Shortcuts.pressEnter();
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/deletename.png", "center", 0.8, 10));
        Functions.rightClick();
        Functions.tryRun(() -> Shortcuts.click("ForTextFile/delete.png", "center", 0.7, 10));

        // End message
        System.out.println("[COMPLETE] TEXT FILE Regression Test DONE!!!");

    }

}
