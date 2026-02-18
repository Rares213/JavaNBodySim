/**
 * UI class that has a slider and label above
 */

package core;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class LabeledSliderInt extends JPanel
        implements ChangeListener
{
    protected JLabel label;
    protected JSlider slider;

    protected String name;

    /**
     * Constructor for LabeledSliderInt
     * @param name name of the label
     * @param maxValue maximum value
     * @param initialValue initial starting value
     * @throws IllegalArgumentException if maximum value or initial value are negative
     */
    public LabeledSliderInt(String name, int maxValue, int initialValue)
    {
        if (maxValue < 0 || initialValue < 0)
            throw new IllegalArgumentException("NO NEGATIVE VALUES");

        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.name = name + ": ";

        label = new JLabel(this.name + initialValue);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        slider = new JSlider(0, maxValue, initialValue);
        slider.setName(name);
        slider.setAlignmentX(Component.LEFT_ALIGNMENT);
        slider.addChangeListener(this);

        add(label);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(slider);
    }

    public int getValueSlider()
    {
        return slider.getValue();
    }

    public void listenSliderChanges(ChangeListener changeListener)
    {
        slider.addChangeListener(changeListener);
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        label.setText(name + slider.getValue());
    }
}
