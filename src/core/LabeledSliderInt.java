package core;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class LabeledSliderInt extends JPanel
        implements ChangeListener
{
    JLabel label;
    JSlider slider;

    String name;

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
