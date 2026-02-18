package simulation;

import core.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class SimViewMenu extends JPanel
{
    JPanel simStatistics;
    JLabel forceFinderDelta;
    JLabel integratorDelta;

    //physics related ui
    DropdownMenu integratorOptions;
    JRadioButton eulerExplicitButton;
    JRadioButton eulerImplicitButton;

    DropdownMenu forceFinderOptions;
    JRadioButton directButton;
    JRadioButton barnesHutButton;

    DropdownMenu physicsSettings;
    LabeledSliderGravity gravitySlider;
    LabeledSliderSoft softSlider;
    LabeledSliderStepsize stepsizeSlider;
    LabeledSliderTheta thetaSlider;

    //bodies related ui
    DropdownMenu bodiesSettings;
    LabeledSliderNumberBodies numberBodiesSlider;
    JButton resetBodies;

    final int width = 350;
    final int height = 500;

    public SimViewMenu()
    {
        super();
        setSize(width, height);
        setLocation(0, 0);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initSimStatistics();
        initBodiesSettings();

        add(Box.createRigidArea(new Dimension(0, 5)));

        initIntegratorOptions();

        add(Box.createRigidArea(new Dimension(0, 5)));

        initForcefinderOptions();

        add(Box.createRigidArea(new Dimension(0, 5)));

        initPhysicsSettings();

        //integratorOptions.addPropertyChangeListener();
    }

    public void listenAllChangeListeners(ChangeListener changeListener)
    {
        gravitySlider.listenSliderChanges(changeListener);
        softSlider.listenSliderChanges(changeListener);
        stepsizeSlider.listenSliderChanges(changeListener);
        thetaSlider.listenSliderChanges(changeListener);
        numberBodiesSlider.listenSliderChanges(changeListener);
    }

    public void listenAllActionListener(ActionListener actionListener)
    {
        eulerExplicitButton.addActionListener(actionListener);
        eulerImplicitButton.addActionListener(actionListener);
        directButton.addActionListener(actionListener);
        barnesHutButton.addActionListener(actionListener);
        resetBodies.addActionListener(actionListener);
    }

    public void setSimStatistics(long forcefinderDelta, long integratorDelta)
    {
        this.forceFinderDelta.setText("Forcefinder delta: " + forcefinderDelta + " ms");
        this.integratorDelta.setText("Integrator delta: " + integratorDelta + " ms");
    }

    private void initSimStatistics()
    {
        forceFinderDelta = new JLabel("Forcefinder delta: 0 ms");
        forceFinderDelta.setBorder(new EmptyBorder(10, 5, 0, 0));
        integratorDelta = new JLabel("Integrator delta: 0 ms");
        integratorDelta.setBorder(new EmptyBorder(5, 5, 5, 0));

        add(forceFinderDelta);
        add(integratorDelta);
    }

    private void initBodiesSettings()
    {
        ConfigSim conf = ConfigSim.getInstance();

        numberBodiesSlider = new LabeledSliderNumberBodies();

        resetBodies = new JButton(conf.RESET_BODIES_BUTTON_NAME);
        resetBodies.setName(conf.RESET_BODIES_BUTTON_NAME);
        resetBodies.setActionCommand(conf.RESET_BODIES_BUTTON_ACTION_COMMAND);

        bodiesSettings = new DropdownMenu(conf.DROPDOWN_BODIES_NAME);
        bodiesSettings.addDropComponents(numberBodiesSlider);
        bodiesSettings.addDropComponents(Box.createRigidArea(new Dimension(0, 5)));
        bodiesSettings.addDropComponents(resetBodies);
        bodiesSettings.setBorder(new EmptyBorder(0, 5, 0, 20));

        add(bodiesSettings);
    }

    private void initIntegratorOptions()
    {
        ConfigSim conf = ConfigSim.getInstance();

        integratorOptions = new DropdownMenu(conf.DROPDOWN_INTEGRATOR_NAME);
        integratorOptions.setBorder(new EmptyBorder(0, 5, 0, 0));

        eulerExplicitButton = new JRadioButton(conf.EULER_EXPLICIT_BUTTON_NAME);
        eulerExplicitButton.setName(conf.EULER_EXPLICIT_BUTTON_NAME);
        eulerExplicitButton.setActionCommand(conf.EULER_EXPLICIT_BUTTON_ACTION_COMMAND);

        eulerImplicitButton = new JRadioButton(conf.EULER_IMPLICIT_BUTTON_NAME);
        eulerImplicitButton.setName(conf.EULER_IMPLICIT_BUTTON_NAME);
        eulerImplicitButton.setActionCommand(conf.EULER_IMPLICIT_BUTTON_ACTION_COMMAND);
        eulerImplicitButton.setSelected(true);

        ButtonGroup integratorGroup = new ButtonGroup();
        integratorGroup.add(eulerExplicitButton);
        integratorGroup.add(eulerImplicitButton);

        integratorOptions.addDropComponents(eulerExplicitButton);
        integratorOptions.addDropComponents(eulerImplicitButton);

        add(integratorOptions);

    }

    private void initForcefinderOptions()
    {
        ConfigSim conf = ConfigSim.getInstance();

        forceFinderOptions = new DropdownMenu(conf.DROPDOWN_FORCEFINDER_NAME);
        forceFinderOptions.setBorder(new EmptyBorder(0, 5, 0, 0));

        directButton = new JRadioButton(conf.DIRECT_METHOD_BUTTON_NAME);
        directButton.setName(conf.DIRECT_METHOD_BUTTON_NAME);
        directButton.setActionCommand(conf.DIRECT_METHOD_BUTTON_ACTION_COMMAND);

        barnesHutButton = new JRadioButton(conf.BARNES_HUT_METHOD_BUTTON_NAME);
        barnesHutButton.setName(conf.BARNES_HUT_METHOD_BUTTON_NAME);
        barnesHutButton.setActionCommand(conf.BARNES_HUT_METHOD_BUTTON_ACTION_COMMAND);
        barnesHutButton.setSelected(true);

        ButtonGroup forcefinderGroup = new ButtonGroup();
        forcefinderGroup.add(directButton);
        forcefinderGroup.add(barnesHutButton);

        forceFinderOptions.addDropComponents(directButton);
        forceFinderOptions.addDropComponents(barnesHutButton);

        add(forceFinderOptions);
    }

    private void initPhysicsSettings()
    {
        ConfigSim conf = ConfigSim.getInstance();

        gravitySlider = new LabeledSliderGravity();
        softSlider = new LabeledSliderSoft();
        stepsizeSlider = new LabeledSliderStepsize();
        thetaSlider = new LabeledSliderTheta();

        physicsSettings = new DropdownMenu(conf.DROPDOWN_PHYSICS_NAME);
        physicsSettings.addDropComponents(gravitySlider);
        physicsSettings.addDropComponents(softSlider);
        physicsSettings.addDropComponents(stepsizeSlider);
        physicsSettings.addDropComponents(thetaSlider);
        physicsSettings.setBorder(new EmptyBorder(0, 5, 0, 20));

        add(physicsSettings);
    }


}
