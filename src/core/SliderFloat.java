package core;

import javax.swing.*;

public class SliderFloat extends JSlider
{
    private final float stepSize;

    public SliderFloat(String name, float maxValue, float stepSize, float initialValue)
    {
        if(maxValue < 0 || stepSize < 0 ||  initialValue < 0 || stepSize > 1.0)
            throw new IllegalArgumentException("NO NEGATIVE VALUES");

        super(0, toIntRepresentation(maxValue, stepSize), toIntRepresentation(initialValue, stepSize));
        super.setName(name);
        this.stepSize = stepSize;
    }

    public float getFloatValue()
    {
        return toFloatRepresentation(this.getValue(), stepSize);
    }

    private static int toIntRepresentation(final float value, final float stepSize)
    {
        return (int) (value / stepSize);
    }
    private static float toFloatRepresentation(final int value, final float stepSize)
    {
        return (float) value * stepSize;
    }
}
