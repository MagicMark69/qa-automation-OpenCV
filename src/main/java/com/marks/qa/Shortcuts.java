package com.marks.qa;

import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Shortcuts {

    // For clicking elements
    public static void click(String image, String click, double confidence, int timeoutSeconds) throws Exception {
        Point matchPoint = Functions.waitForTarget(image, confidence, timeoutSeconds);
        Mat targetMat = Functions.loadTarget(image); // Load again to get dimensions

        switch (click.toLowerCase()) {
            case "center" -> Functions.clickCenter(matchPoint, targetMat);
            case "left" -> Functions.clickBottomLeft(matchPoint, targetMat);
            case "right" -> Functions.clickBottomRight(matchPoint, targetMat);
            default -> throw new IllegalArgumentException("ERROR: Invalid click position: " + click);
        }
    }

    // Ctrl A -> Delete
    public static void deleteFiller() throws AWTException {
        Robot robot = new Robot();
        // Ctrl + A (Select all)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(100); // Small delay for realism
        // Backspace (Delete selected)
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        System.out.println("Filler deleted");
        robot.delay(100);
    }

    // Press Enter
    public static void pressEnter() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(100); // slight delay
    }

    // Shows desktop (only works if it's located in the lower right of the screen)
    public static void clickShowDesktop() throws AWTException {
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Move and click at bottom-right corner
        Robot robot = new Robot();
        robot.mouseMove(screenWidth - 1, screenHeight - 1);
        robot.delay(200); // optional delay
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        System.out.println("Clicked bottom-right corner to show desktop");
    }

    // Hovers on scrollable area
    public static void hoverOnScroll() throws Exception{
        Point matchPoint;
        Robot robot = new Robot();

        matchPoint = Functions.waitForTarget("ForTextFile/organize.png", 0.7, 10);
            if(matchPoint != null){
                Mat targetMat = Functions.loadTarget("ForTextFile/organize.png"); // Load again to get dimensions
                int centerX = matchPoint.x + targetMat.cols() / 2;
                int centerY = matchPoint.y + targetMat.rows() / 2;
                robot.mouseMove(centerX, centerY);
            }
    }

}
