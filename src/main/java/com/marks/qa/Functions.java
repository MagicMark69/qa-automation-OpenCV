package com.marks.qa;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.javacpp.DoublePointer;


import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.InputStream;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.bytedeco.opencv.opencv_core.Mat;



public class Functions {

    //// _____IMPORTANT FOR GENERAL QA-ing_____

    // Checks if Target image is visible
    public static Point waitForTarget(String imageName, double confidence, int timeoutSeconds) throws Exception {
        Thread.sleep(500); // wait 0.5 second
        long startTime = System.currentTimeMillis();
        long timeout = timeoutSeconds * 1000L;

        while (System.currentTimeMillis() - startTime < timeout) {
            Mat screenMat = takeScreenshot();
            Mat targetMat = loadTarget(imageName);
            Point match = matchImg(screenMat, targetMat, confidence);

            if (match != null) {
                System.out.println(imageName + " has been found");
                return match;
            }

            System.out.println("No match yet for " + imageName + ", retrying...");
            Thread.sleep(500); // wait 0.5 second before retry
        }

        throw new Exception("[ERROR] " + imageName + " not found within " + timeoutSeconds + " seconds.");
    }

    // Takes screenshot
    public static Mat takeScreenshot() throws Exception{
        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenshot = robot.createScreenCapture(screenRect);
        return bufferedImageToMat(screenshot);
    }

    // Load target image from resources
    public static Mat loadTarget(String imageName) throws Exception{
        InputStream imageStream = Functions.class.getResourceAsStream("/" + imageName);
        if (imageStream == null) {
            throw new IllegalArgumentException("Image does not exist in folder: " + imageName);
        }
        BufferedImage targetBuffered = ImageIO.read(imageStream);
        return bufferedImageToMat(targetBuffered);
    }

    // Compares screenshot and target image. Returns screen coordinates of matched area if confidence threshold is enough
    public static java.awt.Point matchImg(Mat screenMat, Mat targetMat, double confidenceThreshold) {
        Mat result = new Mat();
        opencv_imgproc.matchTemplate(screenMat, targetMat, result, opencv_imgproc.TM_CCOEFF_NORMED);

        DoublePointer minVal = new DoublePointer(1);
        DoublePointer maxVal = new DoublePointer(1);
        org.bytedeco.opencv.opencv_core.Point minLoc = new org.bytedeco.opencv.opencv_core.Point();
        org.bytedeco.opencv.opencv_core.Point maxLoc = new org.bytedeco.opencv.opencv_core.Point();

        opencv_core.minMaxLoc(result, minVal, maxVal, minLoc, maxLoc, null);

        if (maxVal.get() >= confidenceThreshold) {
            return new java.awt.Point(maxLoc.x(), maxLoc.y());
        } else {
            return null;
        }
    }

    // Convert to a known byte-backed format
    private static Mat bufferedImageToMat(BufferedImage bi) {
        if (bi.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            BufferedImage converted = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g2d = converted.createGraphics();
            g2d.drawImage(bi, 0, 0, null);
            g2d.dispose();
            bi = converted;
        }

        byte[] pixels = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), opencv_core.CV_8UC3);
        mat.data().put(pixels);
        return mat;
    }

    // Try-catch handler
    public static void tryRun(CheckedRunnable action) {
        try {
            action.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Image not found. Do you want to continue? (y/n): ");
            String input = scanner.nextLine();
            if (!input.equalsIgnoreCase("y")) {
                System.out.println("Exiting program.");
                System.exit(1);
            }
        }
    }

    // Clicks center of matched area
    public static void clickCenter(Point matchPoint, Mat targetMat) {
        try {
            Robot robot = new Robot();
            int centerX = matchPoint.x + targetMat.cols() / 2;
            int centerY = matchPoint.y + targetMat.rows() / 2;

            robot.mouseMove(centerX, centerY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            //System.out.println("Clicked at: (" + centerX + ", " + centerY + ")");
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void rightClick() throws AWTException {
        Robot robot = new Robot();
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK); // Right mouse button down
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK); // Right mouse button up
        System.out.println("Right-clicked target file");
    }

    // For simulating keyboard clicks AKA typing
    public static void typeString(String text) throws AWTException {
        System.out.println("Typing...");
        Robot robot = new Robot();
        for (char c : text.toCharArray()) {
            typeChar(robot, c);
            robot.delay(100); // Optional delay for realism
        }
    }
    private static void typeChar(Robot robot, char c) {
        try {
            boolean upperCase = Character.isUpperCase(c);
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            if (keyCode == KeyEvent.VK_UNDEFINED) {
                System.out.println("Cannot type character: " + c);
                return;
            }

            if (upperCase) robot.keyPress(KeyEvent.VK_SHIFT);

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

            if (upperCase) robot.keyRelease(KeyEvent.VK_SHIFT);
        } catch (IllegalArgumentException e) {
            System.out.println("KeyCode not found for: " + c);
        }
    }


    //// _____NICHE FUNCTIONS_____

    // Alternate version of waitForTarget() that scrolls while waiting for a match
    public static void waitAndScroll(String imageName, double confidence, int timeoutSeconds) throws Exception {
        Thread.sleep(500); // wait 0.5 second
        long startTime = System.currentTimeMillis();
        long timeout = timeoutSeconds * 1000L;

        while (System.currentTimeMillis() - startTime < timeout) {
            Mat screenMat = takeScreenshot();
            Mat targetMat = loadTarget(imageName);
            Point match = matchImg(screenMat, targetMat, confidence);

            if (match != null) {
                return;
            }
            // Clicks to skip cutscenes and such
            Robot robot = new Robot();
            robot.mouseWheel(4); // scrolls down by 1 unit
            System.out.println("Scrolling down until match is found");
            Thread.sleep(500); // wait 0.5 second before retry
        }

        throw new Exception(imageName + " not found within " + timeoutSeconds + " seconds.");
    }


    // Clicks bottom left of matched area
    public static void clickBottomLeft(Point matchPoint, Mat targetMat) {
        try {
            Robot robot = new Robot();
            int clickX = matchPoint.x;
            int clickY = matchPoint.y + targetMat.rows();

            robot.mouseMove(clickX, clickY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // Clicks bottom right of matched area
    public static void clickBottomRight(Point matchPoint, Mat targetMat) {
        try {
            Robot robot = new Robot();
            int clickX = matchPoint.x + targetMat.cols();
            int clickY = matchPoint.y + targetMat.rows();

            robot.mouseMove(clickX, clickY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // Clicks for a second time after a delay
    public static void secondClick(){
        try {
            Thread.sleep(400); // wait 0.4 second
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

}
