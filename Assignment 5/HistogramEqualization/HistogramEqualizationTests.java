/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  HistogramEqualizationLogic
 *  acm.program.Program
 */
import java.awt.Component;

import test.TestCase;
import test.TestRunnerPanel;
import test.TestSuite;
import acm.program.Program;

public class HistogramEqualizationTests
extends Program {
    public void init() {
        TestRunnerPanel panel = new TestRunnerPanel(new TestSuite("histogramFor Tests", new TestCase(){

            @Override
            public String getName() {
                return "Result array should have proper number of entries.";
            }

            @Override
            protected boolean runTest() {
                int[] result = HistogramEqualizationLogic.histogramFor((int[][])new int[10][10]);
                if (result != null && result.length == 256) {
                    return true;
                }
                return false;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Testing image of all black pixels.";
            }

            @Override
            protected boolean runTest() {
                int[][] image = new int[137][42];
                int[] result = HistogramEqualizationLogic.histogramFor((int[][])image);
                if (result == null || result.length != 256) {
                    return false;
                }
                if (result[0] != 5754) {
                    return false;
                }
                for (int i = 1; i < 256; ++i) {
                    if (result[i] == 0) continue;
                    return false;
                }
                return true;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Testing image of all white pixels.";
            }

            @Override
            protected boolean runTest() {
                int[][] image = new int[137][42];
                for (int row = 0; row < image.length; ++row) {
                    for (int col = 0; col < image[row].length; ++col) {
                        image[row][col] = 255;
                    }
                }
                int[] result = HistogramEqualizationLogic.histogramFor((int[][])image);
                if (result == null || result.length != 256) {
                    return false;
                }
                if (result[255] != 5754) {
                    return false;
                }
                for (int i = 0; i < 255; ++i) {
                    if (result[i] == 0) continue;
                    return false;
                }
                return true;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Testing image with one pixel of each color.";
            }

            @Override
            protected boolean runTest() {
                int[][] image = new int[1][256];
                int i = 0;
                while (i < 256) {
                    image[0][i] = i++;
                }
                int[] result = HistogramEqualizationLogic.histogramFor((int[][])image);
                if (result == null || result.length != 256) {
                    return false;
                }
                for (int i2 = 0; i2 < 256; ++i2) {
                    if (result[i2] == 1) continue;
                    return false;
                }
                return true;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Testing image with half black pixels and half white pixels.";
            }

            @Override
            protected boolean runTest() {
                int[][] image = new int[256][256];
                for (int i = 0; i < 256; ++i) {
                    for (int j = 0; j < 256; ++j) {
                        image[i][j] = i % 2 == 0 ? 0 : 255;
                    }
                }
                int[] result = HistogramEqualizationLogic.histogramFor((int[][])image);
                if (result == null || result.length != 256) {
                    return false;
                }
                if (result[255] != 32768) {
                    return false;
                }
                if (result[0] != 32768) {
                    return false;
                }
                for (int i2 = 1; i2 < 255; ++i2) {
                    if (result[i2] == 0) continue;
                    return false;
                }
                return true;
            }
        }), new TestSuite("cumulativeSumFor Tests", new TestCase(){

            @Override
            public String getName() {
                return "Result array should have proper number of entries.";
            }

            @Override
            protected boolean runTest() {
                int[] fakeData = new int[256];
                fakeData[0] = 1;
                int[] result = HistogramEqualizationLogic.cumulativeSumFor((int[])fakeData);
                if (result != null && result.length == 256) {
                    return true;
                }
                return false;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Testing sum of histogram of all 1s.";
            }

            @Override
            protected boolean runTest() {
                int[] fakeData = new int[256];
                for (int i = 0; i < fakeData.length; ++i) {
                    fakeData[i] = 1;
                }
                int[] result = HistogramEqualizationLogic.cumulativeSumFor((int[])fakeData);
                if (result == null || result.length != 256) {
                    return false;
                }
                for (int i2 = 0; i2 < result.length; ++i2) {
                    if (result[i2] == i2 + 1) continue;
                    return false;
                }
                return true;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Testing histogram of all black pixels.";
            }

            @Override
            protected boolean runTest() {
                int[] fakeData = new int[256];
                fakeData[0] = 256;
                int[] result = HistogramEqualizationLogic.cumulativeSumFor((int[])fakeData);
                if (result == null || result.length != 256) {
                    return false;
                }
                for (int i = 0; i < result.length; ++i) {
                    if (result[i] == 256) continue;
                    return false;
                }
                return true;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Testing histogram of only white and black pixels.";
            }

            @Override
            protected boolean runTest() {
                int[] fakeData = new int[256];
                fakeData[0] = 100;
                fakeData[255] = 100;
                int[] result = HistogramEqualizationLogic.cumulativeSumFor((int[])fakeData);
                if (result == null || result.length != 256) {
                    return false;
                }
                if (result[0] != 100) {
                    return false;
                }
                for (int i = 1; i < result.length - 1; ++i) {
                    if (result[i] == 100) continue;
                    return false;
                }
                if (result[255] != 200) {
                    return false;
                }
                return true;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Testing histogram of increasing frequencies.";
            }

            @Override
            protected boolean runTest() {
                int[] fakeData = new int[256];
                int i = 0;
                while (i < fakeData.length) {
                    fakeData[i] = i++;
                }
                int[] result = HistogramEqualizationLogic.cumulativeSumFor((int[])fakeData);
                if (result == null || result.length != 256) {
                    return false;
                }
                for (int i2 = 1; i2 < result.length; ++i2) {
                    if (result[i2] == i2 * (i2 + 1) / 2) continue;
                    return false;
                }
                return true;
            }
        }), new TestSuite("totalPixelsIn Tests", new TestCase(){

            @Override
            public String getName() {
                return "Test of 40 x 40 image.";
            }

            @Override
            protected boolean runTest() {
                if (HistogramEqualizationLogic.totalPixelsIn((int[][])new int[40][40]) == 1600) {
                    return true;
                }
                return false;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Test of 50 x 30 image.";
            }

            @Override
            protected boolean runTest() {
                if (HistogramEqualizationLogic.totalPixelsIn((int[][])new int[50][30]) == 1500) {
                    return true;
                }
                return false;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Test of 30 x 50 image.";
            }

            @Override
            protected boolean runTest() {
                if (HistogramEqualizationLogic.totalPixelsIn((int[][])new int[30][50]) == 1500) {
                    return true;
                }
                return false;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Test of 1 x 1 image.";
            }

            @Override
            protected boolean runTest() {
                if (HistogramEqualizationLogic.totalPixelsIn((int[][])new int[1][1]) == 1) {
                    return true;
                }
                return false;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Test of 1 x 8 image.";
            }

            @Override
            protected boolean runTest() {
                if (HistogramEqualizationLogic.totalPixelsIn((int[][])new int[1][8]) == 8) {
                    return true;
                }
                return false;
            }
        }, new TestCase(){

            @Override
            public String getName() {
                return "Test of 8 x 1 image.";
            }

            @Override
            protected boolean runTest() {
                if (HistogramEqualizationLogic.totalPixelsIn((int[][])new int[8][1]) == 8) {
                    return true;
                }
                return false;
            }
        }));
        this.add((Component)panel);
        panel.runTests();
    }

}

