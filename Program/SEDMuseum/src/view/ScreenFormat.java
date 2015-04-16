/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 *
 * @author Hans
 */
public class ScreenFormat {

    private final double paneHeight;
    private final double buttonWidth;
    private final double rPaneWidth;
    private final double topGap;
    private final double topGapSmall;
    private final Dimension screenSize;
    private final Rectangle screenRect;
    private final double screenWidth;
    private final double screenHeight;
    private final int fontSize;
    private final int smallFontSize;
    private final Font font;

    public ScreenFormat() {
        this.fontSize = 30;
        this.smallFontSize = 15;
        this.paneHeight = 0.70;
        this.buttonWidth = 0.25;
        this.rPaneWidth = 0.4;
        this.topGap = 0.15;
        this.topGapSmall = 0.025;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenRect = new Rectangle(screenSize);
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
        font = new Font("Garland", PLAIN, getFontSize());
    }

    public int getTopGapSmall() {
        return (int) (topGapSmall * screenHeight);
    }

    public int getTopGap() {
        return (int) (topGap * screenHeight);
    }

    public int getScreenWidth() {
        return (int) screenWidth;
    }

    public int getPaneHeight() {
        return (int) (paneHeight * screenHeight);
    }

    public int getButtonWidth() {
        return (int) (buttonWidth * screenWidth);
    }

    public int getDefaultButtonHeight() {
        return (int) (0.04 * screenSize.height);
    }

    public int getDefaultButtonWidth() {
        double buttonW = screenSize.width < 1440 ? 0.04 : 0.03;
        return (int) (buttonW * screenSize.width);
    }

    public int getRPaneWidth() {
        return (int) (rPaneWidth * screenWidth);
    }

    public int getDefaultgap() {
        return (int) (0.025 * screenWidth);
    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    public Rectangle getScreenRect() {
        return screenRect;
    }

    public int getFontSize() {
        int size = (int) (fontSize * screenWidth / 1920);
        return size;
    }

    public int getSmallFontSize() {
        return (int) (smallFontSize * screenWidth / 1920);
    }

    public Font getFont() {
        return font;
    }
}
