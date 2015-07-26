/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  HistogramEqualizationLogic
 *  acm.graphics.GImage
 *  acm.graphics.GObject
 *  acm.io.IODialog
 *  acm.program.GraphicsProgram
 *  acm.util.ErrorException
 */
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import acm.util.ErrorException;

public class HistogramEqualization
extends GraphicsProgram
implements ComponentListener {
    private GImage displayImage;
    private GImage masterImage;
    private JButton toggleButton;
    private static final String[] LOAD_IMAGE_EXTENSIONS = new String[]{".png", ".bmp", ".wbmp", ".jpg", ".gif", ".jpeg"};

    public void init() {
        this.add((Component)new JButton("Choose Image"), (Object)"South");
        this.toggleButton = new JButton("Equalize");
        this.add((Component)this.toggleButton, (Object)"South");
        this.setIsEqualize(true);
        this.addComponentListener((ComponentListener)this);
        this.addActionListeners();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Choose Image")) {
            this.chooseImage();
        } else if (e.getActionCommand().equals("Equalize")) {
            this.equalizeImage();
        } else if (e.getActionCommand().equals("Original")) {
            this.restoreImage();
        }
    }

    private void redrawAll() {
        if (this.displayImage != null) {
            this.displayImage.setBounds(0.0, 0.0, (double)this.getWidth(), (double)this.getHeight());
        }
    }

    private void computeEqualizedImage() {
        assert (this.displayImage != null);
        int[][] pixels = HistogramEqualizationImageTransforms.toGrayscale(this.displayImage).getPixelArray();
        int[][] intensities = HistogramEqualizationImageTransforms.imageToLuminances(pixels);
        int[][] result = HistogramEqualizationLogic.equalize((int[][])intensities);
        if (this.confirmResult(result, pixels)) {
            for (int row = 0; row < result.length; ++row) {
                for (int col = 0; col < result[row].length; ++col) {
                    int intensity = result[row][col];
                    result[row][col] = GImage.createRGBPixel((int)intensity, (int)intensity, (int)intensity);
                }
            }
            this.setImage(new GImage(result));
        }
    }

    private boolean confirmResult(int[][] result, int[][] pixels) {
        if (result == null) {
            this.getDialog().showErrorMessage("Resulting image was null.");
            return false;
        }
        if (result.length != pixels.length) {
            this.getDialog().showErrorMessage("Resulting image has the wrong number of rows.");
            return false;
        }
        for (int i = 0; i < result.length; ++i) {
            if (result[i] == null) {
                this.getDialog().showErrorMessage("Resulting image contains a null row.");
                return false;
            }
            if (result[i].length != pixels[i].length) {
                this.getDialog().showErrorMessage("Resulting image has the wrong number of columns.");
                return false;
            }
            for (int j = 0; j < result[i].length; ++j) {
                if (result[i][j] >= 0 && result[i][j] < 256) continue;
                this.getDialog().showErrorMessage("Luminance out of range: " + result[i][j]);
                return false;
            }
        }
        return true;
    }

    private void setIsEqualize(boolean isEqualize) {
        this.toggleButton.setText(isEqualize ? "Equalize" : "Original");
    }

    private void setImage(GImage image) {
        if (this.displayImage != null) {
            this.remove((GObject)this.displayImage);
        }
        this.displayImage = image;
        if (image != null) {
            this.displayImage.setBounds(0.0, 0.0, 0.0, 0.0);
            this.add((GObject)this.displayImage);
        }
        this.redrawAll();
    }

    private String extensionOf(File filename) {
        int lastDot = filename.getName().lastIndexOf(46);
        if (lastDot == -1) {
            return "";
        }
        return filename.getName().substring(lastDot);
    }

    private JFileChooser getFileChooser() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileFilter(){

            @Override
            public boolean accept(File filename) {
                if (!(filename.isDirectory() || Arrays.asList(LOAD_IMAGE_EXTENSIONS).contains(HistogramEqualization.this.extensionOf(filename)))) {
                    return false;
                }
                return true;
            }

            @Override
            public String getDescription() {
                return "Image files";
            }
        });
        fc.setCurrentDirectory(new File("histogram/"));
        return fc;
    }

    private void chooseImage() {
        JFileChooser fc = this.getFileChooser();
        if (fc.showOpenDialog((Component)this) == 0) {
            try {
                this.masterImage = new GImage(fc.getSelectedFile().getAbsolutePath());
                this.setImage(this.masterImage);
                this.setIsEqualize(true);
            }
            catch (ErrorException err) {
                this.getDialog().showErrorMessage("Error loading file: " + err.getMessage());
            }
        }
    }

    private void equalizeImage() {
        if (this.displayImage == null) {
            this.getDialog().showErrorMessage("No image selected.");
        } else {
            this.computeEqualizedImage();
            this.setIsEqualize(false);
        }
    }

    private void restoreImage() {
        this.setImage(this.masterImage);
        this.setIsEqualize(true);
    }

    @Override
    public void componentResized(ComponentEvent arg0) {
        this.redrawAll();
    }

    @Override
    public void componentHidden(ComponentEvent arg0) {
    }

    @Override
    public void componentMoved(ComponentEvent arg0) {
    }

    @Override
    public void componentShown(ComponentEvent arg0) {
    }

}

