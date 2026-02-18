/**
 * UI class that has a float slider and label above
 */

package core;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.Math.*;

public class LabeledSliderFloat extends JPanel
        implements ChangeListener
{
    protected JLabel label;
    protected SliderFloat slider;

    protected String name;
    protected int numDecimals;

    /**
     * Constructor for LabeledSliderFloat
     * @param name name of the label
     * @param maxValue maximum value of the slider
     * @param stepSize how fast to increase/decrease
     * @param initialValue starting value
     * @throws IllegalArgumentException if maximum value, stepsize or initial value are negative or stepsize is not a power of 10 and grater than 0.1
     */
    public LabeledSliderFloat(String name, float maxValue, float stepSize, float initialValue)
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.name = name + ": ";
        numDecimals = getNumberDecimals(stepSize);

        slider = new SliderFloat(name, maxValue, stepSize, initialValue);
        slider.setAlignmentX(Component.LEFT_ALIGNMENT);
        slider.addChangeListener(this);

        label = new JLabel(getFormatedLabelString(this.name, numDecimals, slider.getFloatValue()));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(label);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(slider);
    }

    /**
     * Get the float value of the slider
     * @return
     */
    public float getSliderValue()
    {
        return slider.getFloatValue();
    }

    /**
     * Listen for changes in slider
     * @param changeListener
     */
    public void listenSliderChanges(ChangeListener changeListener)
    {
        slider.addChangeListener(changeListener);
    }

    private static int getNumberDecimals(float stepSize)
    {
        double power = Math.abs(Math.log10(stepSize));
        power = Math.round(power);
        return (int)Math.abs( power );
    }

    private static String getFormatedLabelString(String str, int numDecimals, float value)
    {
        String formatStringFloat = "%." + numDecimals + "f";

        return str + String.format(formatStringFloat, value);
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        label.setText(getFormatedLabelString(name, numDecimals, slider.getFloatValue()));
    }

}
