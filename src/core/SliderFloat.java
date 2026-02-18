package core;

import javax.swing.JSlider;

public class SliderFloat extends JSlider
{
    protected float stepSize;

    public SliderFloat(String name, float maxValue, float stepSize, float initialValue)
    {
        if(maxValue < 0 || stepSize < 0 ||  initialValue < 0)
            throw new IllegalArgumentException("NO NEGATIVE VALUES");

        if(!isStepsizeValid(stepSize))
            throw new IllegalArgumentException("INVALID STEPSIZE");

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

    private static boolean isStepsizeValid(float stepsize)
    {
        final float[] validStepsize = { 0.1f, 0.01f, 0.001f, 0.0001f };

        boolean isValid = false;
        for(float step : validStepsize)
            if (Float.compare(step, stepsize) == 0)
            {
                isValid = true;
                break;
            }

        return isValid;
    }


}
