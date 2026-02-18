package simulation;

import core.*;
import bodies.*;
import physics.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.EventObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SimController
        implements Layer, ChangeListener, ActionListener, ComponentListener
{
    private SimViewParticles simViewParticles;
    private SimViewMenu simViewMenu;
    private SimModel simModel;

    ConcurrentLinkedQueue<EventObject> eventQueue;

    public SimController(SimViewParticles simViewParticles, SimViewMenu simViewMenu, SimModel simModel)
    {
        this.simViewParticles = simViewParticles;
        this.simViewMenu = simViewMenu;
        this.simModel = simModel;

        eventQueue = new ConcurrentLinkedQueue<>();

        this.simViewMenu.listenAllActionListener(this);
        this.simViewMenu.listenAllChangeListeners(this);

        this.simViewParticles.setPosition(this.simModel.getBodies().getBodiesProperty(Bodies.Property.POSITION));
        this.simViewParticles.setRadius(this.simModel.getShapeRadius());
    }

    public SimViewMenu getSimViewMenu()
    {
        return simViewMenu;
    }

    public SimViewParticles getSimViewParticles()
    {
        return simViewParticles;
    }

    @Override
    public void onUpdate()
    {
        if (!eventQueue.isEmpty())
            processEvents();

        Bodies bodies = simModel.getBodies();
        bodies.resetForce();

        Physics physics = simModel.getPhysics();
        physics.runPhysics();

        simViewMenu.setSimStatistics(physics.getForcefinderDuration(), physics.getIntegratorDuration());
    }

    @Override
    public void onRender()
    {
        simViewParticles.repaint();
    }

    private void processEvents()
    {
        ConfigSim conf = ConfigSim.getInstance();

        while(true)
        {
            EventObject event = eventQueue.poll();
            if(event == null)
                break;

            if (event.getSource() instanceof SliderFloat sliderFloat)
            {
                if(sliderFloat.getName().equals(conf.SLIDER_GRAVITY_NAME))
                    simModel.setG(sliderFloat.getFloatValue());

                else if(sliderFloat.getName().equals(conf.SLIDER_SOFT_NAME))
                    simModel.setSoft(sliderFloat.getFloatValue());

                else if(sliderFloat.getName().equals(conf.SLIDER_STEPSIZE_NAME))
                    simModel.setStepSize(sliderFloat.getFloatValue());

                else if(sliderFloat.getName().equals(conf.SLIDER_THETA_NAME))
                {
                    if(sliderFloat.getFloatValue() >= 0.1f)
                        simModel.setTheta(sliderFloat.getFloatValue());
                }

                else if(sliderFloat.getName().equals(conf.SLIDER_RADIUS_NAME))
                {
                    simModel.setShapeRadius(sliderFloat.getFloatValue());
                    simViewParticles.setRadius(sliderFloat.getFloatValue());
                }

            }
            else if (event.getSource() instanceof JSlider sliderInt)
            {
                if(sliderInt.getName().equals(conf.SLIDER_NUMBER_BODIES_NAME))
                {
                    simModel.setNumberBodies(sliderInt.getValue());
                    simViewParticles.setPosition(simModel.getBodies().getBodiesProperty(Bodies.Property.POSITION));
                }
            }
            else if (event.getSource() instanceof AbstractButton aButton)
            {
                if(aButton.getActionCommand().equals(conf.EULER_EXPLICIT_BUTTON_ACTION_COMMAND))
                    simModel.setIntegratorType(IntegratorType.EULER_EXPLICIT);

                else if (aButton.getActionCommand().equals(conf.EULER_IMPLICIT_BUTTON_ACTION_COMMAND))
                    simModel.setIntegratorType(IntegratorType.EULER_IMPLICIT);

                else if (aButton.getActionCommand().equals(conf.DIRECT_METHOD_BUTTON_ACTION_COMMAND))
                    simModel.setForcefinderType(ForceFinderType.GRAVITY_DIRECT);

                else if(aButton.getActionCommand().equals(conf.BARNES_HUT_METHOD_BUTTON_ACTION_COMMAND))
                    simModel.setForcefinderType(ForceFinderType.BARNES_HUT);

                else if(aButton.getActionCommand().equals(conf.RESET_BODIES_BUTTON_ACTION_COMMAND))
                {
                    simModel.resetBodies();
                    simViewParticles.setPosition(simModel.getBodies().getBodiesProperty(Bodies.Property.POSITION));
                }

            }


        }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        eventQueue.add(e);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        eventQueue.add(e);
    }

    @Override
    public void componentResized(ComponentEvent e)
    {
        simViewParticles.setSize(e.getComponent().getSize());
    }

    @Override
    public void componentMoved(ComponentEvent e)
    {

    }

    @Override
    public void componentShown(ComponentEvent e)
    {

    }

    @Override
    public void componentHidden(ComponentEvent e)
    {

    }
}
