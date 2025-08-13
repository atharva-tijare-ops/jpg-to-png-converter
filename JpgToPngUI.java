import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class JpgToPngUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JpgToPngUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("JPG to PNG Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(null);

        JLabel label = new JLabel("Select JPG file to convert:");
        label.setBounds(20, 20, 250, 25);
        frame.add(label);

        JButton selectButton = new JButton("Choose File");
        selectButton.setBounds(20, 60, 120, 30);
        frame.add(selectButton);

        JButton convertButton = new JButton("Convert to PNG");
        convertButton.setBounds(150, 60, 160, 30);
        convertButton.setEnabled(false); 
        frame.add(convertButton);

        JLabel status = new JLabel("");
        status.setBounds(20, 110, 300, 25);
        frame.add(status);

        final File[] selectedFile = {null};

        selectButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JPG Images", "jpg", "jpeg"));
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile[0] = fileChooser.getSelectedFile();
                status.setText("Selected: " + selectedFile[0].getName());
                convertButton.setEnabled(true);
            }
        });

        convertButton.addActionListener(e -> {
            if (selectedFile[0] != null) {
                try {
                    BufferedImage jpgImage = ImageIO.read(selectedFile[0]);
                    if (jpgImage == null) {
                        status.setText("Invalid image file.");
                        return;
                    }

                    String outputPath = selectedFile[0].getParent() + File.separator +
                            selectedFile[0].getName().replaceAll("\\.jpe?g$", "") + ".png";

                    ImageIO.write(jpgImage, "png", new File(outputPath));
                    status.setText("Saved as: " + outputPath);
                } catch (Exception ex) {
                    status.setText("Error: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
