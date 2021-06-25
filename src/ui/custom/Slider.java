package ui.custom;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Slider extends JSlider {

    private Color filledColor;
    private Color emptyColor;
    private Color borderColor;

    public Slider() {
        super();
        setFilledColor(Color.GREEN);
        setEmptyColor(Color.GRAY);
        UIDefaults newDefault = new UIDefaults();
        newDefault.put("Slider:SliderTrack[Enabled].backgroundPainter", new Painter<JSlider>() {
            @Override
            public void paint(Graphics2D g, JSlider c, int w, int h) {
                int arc         = 10;
                int trackHeight = 8;
                int trackWidth  = w - 2;
                int fillTop     = 4;
                int fillLeft    = 1;

                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(1.5f));
                g.setColor(getEmptyColor());
                g.fillRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);

                int fillBottom = fillTop + trackHeight;
                int fillRight  = xPositionForValue( c.getValue(), c, new Rectangle(fillLeft, fillTop, trackWidth, fillBottom - fillTop));

                g.setColor(getFilledColor());
                g.fillRect(fillLeft + 1, fillTop + 1, fillRight - fillLeft, fillBottom - fillTop);

                if(borderColor != null) {
                    g.setColor(getBorderColor());
                    g.drawRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);
                }
            }
            //@see javax/swing/plaf/basic/BasicSliderUI#xPositionForValue(int value)
            protected int xPositionForValue(int value, JSlider slider, Rectangle trackRect) {
                int min = slider.getMinimum();
                int max = slider.getMaximum();
                int trackLength = trackRect.width;
                double valueRange = (double) max - (double) min;
                double pixelsPerValue = (double) trackLength / valueRange;
                int trackLeft = trackRect.x;
                int trackRight = trackRect.x + (trackRect.width - 1);
                int xPosition;

                xPosition = trackLeft;
                xPosition += Math.round(pixelsPerValue * ((double) value - min));

                xPosition = Math.max(trackLeft, xPosition);
                xPosition = Math.min(trackRight, xPosition);

                return xPosition;
            }
        });
        putClientProperty("Nimbus.Overrides", newDefault);
    }

    public Color getFilledColor() {
        return filledColor;
    }

    public void setFilledColor(Color filledColor) {
        this.filledColor = filledColor;
    }

    public Color getEmptyColor() {
        return emptyColor;
    }

    public void setEmptyColor(Color emptyColor) {
        this.emptyColor = emptyColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
