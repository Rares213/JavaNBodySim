package simulation;

import core.*;
import bodies.*;
import physics.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.EventObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulation
        implements Layer, ChangeListener, ActionListener
{

    private Bodies bodies;
    private Physics physics;
    private SimulationState simulationState;
    private SimulationPanel simulationPanel;
    private SimulationMenu simulationMenu;

    ConcurrentLinkedQueue<EventObject> eventQueue;

    public Simulation(SimulationState simState, SimulationPanel simulationPanel, SimulationMenu simulationMenu)
    {
        this.simulationState = simState;
        this.simulationPanel = simulationPanel;
        this.simulationMenu = simulationMenu;

        BodiesInit bodiesInit = new BodiesInit(simState.getNumberBodies(), simState.getIcType());
        bodies = new Bodies(bodiesInit);
        simulationPanel.setPosition(bodies.getBodiesProperty(Bodies.Property.POSITION));

        physics = new Physics(makeIntegrator(), makeForceFinder());

        eventQueue = new ConcurrentLinkedQueue<>();
    }

    public void onUpdate()
    {
        if(!eventQueue.isEmpty())
            processEvents();

        bodies.resetForce();
        physics.runPhysics();

        simulationMenu.setSimStatistics(physics.getForcefinderDuration(), physics.getIntegratorDuration());
    }

    public void onRender()
    {

    }

    public BodiesProperties getBodiesProperties()
    {
        return bodies.getBodiesProperties();
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
                {
                    simulationState.setG(sliderFloat.getFloatValue());
                    physics.setForceFinder(makeForceFinder());
                }
                else if(sliderFloat.getName().equals(conf.SLIDER_SOFT_NAME))
                {
                    simulationState.setSoft(sliderFloat.getFloatValue());
                    physics.setForceFinder(makeForceFinder());
                }
                else if(sliderFloat.getName().equals(conf.SLIDER_STEPSIZE_NAME))
                {
                    simulationState.setStepSize(sliderFloat.getFloatValue());
                    physics.setIntegrator(makeIntegrator());
                }
                else if(sliderFloat.getName().equals(conf.SLIDER_THETA_NAME))
                {
                    simulationState.setTheta(sliderFloat.getFloatValue());
                    physics.setForceFinder(makeForceFinder());
                }

                System.out.println("Name: " + sliderFloat.getName() + " value: " +  sliderFloat.getFloatValue());
            }
            else if (event.getSource() instanceof JSlider sliderInt)
            {
                if(sliderInt.getName().equals(conf.SLIDER_NUMBER_BODIES_NAME))
                {
                    System.out.println("Name: " + sliderInt.getName() + " value: " + sliderInt.getValue());

                    simulationState.setNumberBodies(sliderInt.getValue());

                    bodies.resizeBodies(new BodiesInit(simulationState.getNumberBodies(), simulationState.getIcType()));

                    physics.setForceFinder(makeForceFinder());
                    physics.setIntegrator(makeIntegrator());
                    simulationPanel.setPosition(bodies.getBodiesProperty(Bodies.Property.POSITION));
                }

            }
            else if (event.getSource() instanceof AbstractButton aButton)
            {
                if(aButton.getActionCommand().equals(conf.EULER_EXPLICIT_BUTTON_ACTION_COMMAND))
                {
                    simulationState.setIntType(IntegratorType.EULER_EXPLICIT);
                    physics.setIntegrator(makeIntegrator());
                }
                else if (aButton.getActionCommand().equals(conf.EULER_IMPLICIT_BUTTON_ACTION_COMMAND))
                {
                    simulationState.setIntType(IntegratorType.EULER_IMPLICIT);
                    physics.setIntegrator(makeIntegrator());
                }
                else if (aButton.getActionCommand().equals(conf.DIRECT_METHOD_BUTTON_ACTION_COMMAND))
                {
                    simulationState.setFfType(ForceFinderType.GRAVITY_DIRECT);
                    physics.setForceFinder(makeForceFinder());
                }
                else if(aButton.getActionCommand().equals(conf.RESET_BODIES_BUTTON_ACTION_COMMAND))
                {
                    BodiesInit initBodies = new BodiesInit(simulationState.getNumberBodies(),  simulationState.getIcType());
                    bodies.resetBodies(initBodies);

                    simulationPanel.setPosition(bodies.getBodiesProperty(Bodies.Property.POSITION));
                    physics.setForceFinder(makeForceFinder());
                    physics.setIntegrator(makeIntegrator());
                }
                else if(aButton.getActionCommand().equals(conf.BARNES_HUT_METHOD_BUTTON_ACTION_COMMAND))
                {
                    simulationState.setFfType(ForceFinderType.BARNES_HUT);
                    physics.setForceFinder(makeForceFinder());
                }


                System.out.println("Button name: " + aButton.getName() + " Action: " + aButton.getActionCommand());
            }

        }
    }

    private Integrator makeIntegrator()
    {
        Integrator.IntegratorData integratorData = new Integrator.IntegratorData(
                simulationState.getStepSize(),
                bodies.getBodiesProperty(Bodies.Property.POSITION),
                bodies.getBodiesProperty(Bodies.Property.VELOCITY),
                bodies.getBodiesProperty(Bodies.Property.ACCELERATION),
                bodies.getBodiesProperty(Bodies.Property.FORCE),
                bodies.getBodiesProperty(Bodies.Property.MASS).getFirst());

        switch (simulationState.getIntType())
        {
            case IntegratorType.EULER_EXPLICIT:
            case IntegratorType.EULER_IMPLICIT:
                return new EulerImplicit(integratorData);
        }

        return null;
    }

    private ForceFinder makeForceFinder()
    {
        switch (simulationState.getFfType())
        {
            case ForceFinderType.GRAVITY_DIRECT:
            {
                Gravity.GravityData gravityData = new Gravity.GravityData(
                        simulationState.getG(),
                        simulationState.getSoft(),
                        bodies.getBodiesProperty(Bodies.Property.MASS).getFirst(),
                        bodies.getBodiesProperty(Bodies.Property.POSITION),
                        bodies.getBodiesProperty(Bodies.Property.FORCE));

                return new GravityDirect(gravityData);
            }
            case ForceFinderType.BARNES_HUT:
            {
                Gravity.GravityData gravityData = new Gravity.GravityData(
                        simulationState.getG(),
                        simulationState.getSoft(),
                        bodies.getBodiesProperty(Bodies.Property.MASS).getFirst(),
                        bodies.getBodiesProperty(Bodies.Property.POSITION),
                        bodies.getBodiesProperty(Bodies.Property.FORCE));

                BarnesHutGravity.BarnesHutData bhData = new BarnesHutGravity.BarnesHutData(gravityData, simulationState.getTheta());

                return new BarnesHutGravity(bhData);
            }
        }

        return null;
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
}
