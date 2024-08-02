package com.config.tool.xstuserguide;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

public class DisplayXstUserGuidepdf {
	public void showXstUserGuide() {
		// Specify the path to your PDF file
		//String pdfFilePath = "resources/xstore-200-ug.pdf";
		String pdfFilePath=ClassLoader.getSystemClassLoader().getResource("./xstore-200-ug.pdf").getFile();
		// Build a controller
		SwingController controller = new SwingController();

		// Build a viewer component
		SwingViewBuilder factory = new SwingViewBuilder(controller);
		JPanel viewerComponentPanel = factory.buildViewerPanel();

		// Load a PDF file
		controller.openDocument(pdfFilePath);

		// Create a JFrame to display the PDF viewer component
		JFrame frame = new JFrame("Xstore User Guide Viewer");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(viewerComponentPanel, BorderLayout.CENTER);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


        /*SwingUtilities.invokeLater(() -> {
            // Specify the path to your PDF file
          String pdfFilePath = "resources/xstore-200-ug.pdf";

          // Build a controller
          SwingController controller = new SwingController();

          // Build a viewer component
          SwingViewBuilder factory = new SwingViewBuilder(controller);
          JPanel viewerComponentPanel = factory.buildViewerPanel();

          // Load a PDF file
          controller.openDocument(pdfFilePath);

          // Create a JFrame to display the PDF viewer component
          JFrame frame = new JFrame("PDF Viewer");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.getContentPane().add(viewerComponentPanel, BorderLayout.CENTER);
          frame.setSize(800, 600);
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        });*/
}