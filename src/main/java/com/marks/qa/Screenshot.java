package com.marks.qa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

//// Save screenshot (for setting up resources folder)

public class Screenshot {
    public static void main(String[] args) throws Exception {
        Thread.sleep(3000);
        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenshot = robot.createScreenCapture(screenRect);

        // Save to resources folder (ensure path exists and is writable)
        File outputfile = new File("src/main/resources/ForTextFile/New.png");
        ImageIO.write(screenshot, "png", outputfile);
        System.out.println("Screenshot saved to " + outputfile.getAbsolutePath());
    }
}