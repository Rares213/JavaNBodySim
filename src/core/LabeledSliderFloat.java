package core;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.Math.*;

public class LabeledSliderFloat extends JPanel
        implements ChangeListener
{
    private JLabel label;
    private SliderFloat slider;

    private String name;
    private int numDecimals;

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

    public float getSliderValue()
    {
        return slider.getFloatValue();
    }

    public void listenSliderChanges(ChangeListener changeListener)
    {
        slider.addChangeListener(changeListener);
    }

    private static int getNumberDecimals(final float stepSize)
    {
        return Math.abs( (int)Math.log10(stepSize) );
    }

    private static String getFormatedLabelString(String str, final int numDecimals,  final float value)
    {
        String formatStringFloat = "%." + numDecimals + "f";

        return str + String.format(formatStringFloat, value);
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        label.setText(getFormatedLabelString(name, numDecimals, slider.getFloatValue()));

        /*
        SliderFloat slider = (SliderFloat) e.getSource();
        System.out.println(slider.getName());
        */
    }

}
