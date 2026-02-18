/**
 * UI class that can be closed or opened by pressing a button
 */

package core;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DropdownMenu extends JPanel
        implements ActionListener
{
    JPanel panelLabelButton;
    JPanel panelDropComponents;

    JLabel labelName;
    DropdownButton dropButton;

    static private class DropdownButton extends BasicArrowButton
    {
        private DropdownButton(int direction)
        {
            super(direction);
        }

        @Override
        public Dimension getMaximumSize()
        {
            return getPreferredSize();
        }
    }


    private enum BUTTON_STATE { SHOW, HIDE;};
    BUTTON_STATE buttonState;

    public DropdownMenu(String name)
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        //initialize the label name and the button
        //dropdown menu is hidden initially
        labelName = new JLabel(name);
        dropButton = new DropdownButton(SwingConstants.NORTH);
        dropButton.addActionListener(this);
        buttonState = BUTTON_STATE.SHOW;

        //initialize the panel that contains the label and the button
        panelLabelButton = new JPanel();
        panelLabelButton.setLayout(new BoxLayout(panelLabelButton, BoxLayout.X_AXIS));
        panelLabelButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelLabelButton.add(dropButton);
        panelLabelButton.add(Box.createRigidArea(new Dimension(5, 0)));
        panelLabelButton.add(labelName);

        //initialize the panel that will contain the components for dropdown
        panelDropComponents = new JPanel();
        panelDropComponents.setLayout(new BoxLayout(panelDropComponents, BoxLayout.Y_AXIS));
        panelDropComponents.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(panelLabelButton);

        panelDropComponents.add(Box.createRigidArea(new Dimension(0, 5)));

        //opened by default
        add(panelDropComponents);
        updateUI();
    }

    public void addDropComponents(Component dropComponent)
    {
        panelDropComponents.add(dropComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(buttonState == BUTTON_STATE.HIDE)
        {
            buttonState = BUTTON_STATE.SHOW;
            dropButton.setDirection(SwingConstants.NORTH);
            add(panelDropComponents);
            updateUI();
        }
        else
        {
            buttonState = BUTTON_STATE.HIDE;
            dropButton.setDirection(SwingConstants.SOUTH);
            remove(panelDropComponents);
            updateUI();
        }
    }
}
