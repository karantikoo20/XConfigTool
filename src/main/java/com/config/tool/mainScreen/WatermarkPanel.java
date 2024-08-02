package com.config.tool.mainScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class WatermarkPanel extends JPanel{
    // Custom panel class to paint watermark image

    private BufferedImage watermarkImage;

    public WatermarkPanel(String s) {
        try {
            // Load the watermark image from resources
            watermarkImage = ImageIO.read(getClass().getResourceAsStream("/Accenture_logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (watermarkImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = watermarkImage.getWidth();
            int imageHeight = watermarkImage.getHeight();

            // Calculate scaling factors for decreasing image size
            double scaleWidth = 0.1; // Decrease width
            double scaleHeight = 0.1; // Decrease height

            // Calculate new dimensions
            int newWidth = (int) (scaleWidth * imageWidth);
            int newHeight = (int) (scaleHeight * imageHeight);
            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;

            // Set transparency (composite operation)
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

            // Draw the scaled and transparent image
            g2d.drawImage(watermarkImage, x, y, newWidth, newHeight, this);

            // Dispose Graphics2D object
            g2d.dispose();
        }
    }
}