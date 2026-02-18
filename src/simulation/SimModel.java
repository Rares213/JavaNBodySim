package simulation;

import bodies.Bodies;
import bodies.BodiesInit;
import bodies.InitialConditionsType;
import physics.*;

public class SimModel
{
    private Bodies bodies;
    private Float G;
    private Float soft;
    private Float theta;
    private Float stepSize;
    private Integer numberBodies;
    private ForceFinderType forcefinderType;
    private IntegratorType integratorType;
    private InitialConditionsType initialConditionsType;
    private Float shapeRadius;
    private Physics physics;

    private void initBodies()
    {
        BodiesInit bodiesInit = new BodiesInit(numberBodies, initialConditionsType);
        bodies = new Bodies(bodiesInit);
    }

    private void initPhysics()
    {
        physics = new Physics(makeIntegrator(), makeForceFinder());
    }

    private Integrator makeIntegrator()
    {
        Integrator.IntegratorData integratorData = new Integrator.IntegratorData(
                stepSize,
                bodies.getBodiesProperty(Bodies.Property.POSITION),
                bodies.getBodiesProperty(Bodies.Property.VELOCITY),
                bodies.getBodiesProperty(Bodies.Property.ACCELERATION),
                bodies.getBodiesProperty(Bodies.Property.FORCE),
                bodies.getBodiesProperty(Bodies.Property.MASS).getFirst());

        switch (integratorType)
        {
            case IntegratorType.EULER_EXPLICIT:
            case IntegratorType.EULER_IMPLICIT:
                return new EulerImplicit(integratorData);
        }

        return null;
    }

    private ForceFinder makeForceFinder()
    {
        switch (forcefinderType)
        {
            case ForceFinderType.GRAVITY_DIRECT:
            {
                Gravity.GravityData gravityData = new Gravity.GravityData(
                        G,
                        soft,
                        bodies.getBodiesProperty(Bodies.Property.MASS).getFirst(),
                        bodies.getBodiesProperty(Bodies.Property.POSITION),
                        bodies.getBodiesProperty(Bodies.Property.FORCE));

                return new GravityDirect(gravityData);
            }
            case ForceFinderType.BARNES_HUT:
            {
                Gravity.GravityData gravityData = new Gravity.GravityData(
                        G,
                        soft,
                        bodies.getBodiesProperty(Bodies.Property.MASS).getFirst(),
                        bodies.getBodiesProperty(Bodies.Property.POSITION),
                        bodies.getBodiesProperty(Bodies.Property.FORCE));

                BarnesHutGravity.BarnesHutData bhData = new BarnesHutGravity.BarnesHutData(gravityData, theta);

                return new BarnesHutGravity(bhData);
            }
        }

        return null;
    }

    public SimModel()
    {
        ConfigSim conf = ConfigSim.getInstance();

        initialConditionsType = conf.INIT_BODIES_COND;
        forcefinderType = conf.INIT_FORCE_FINDER_OPTION;
        integratorType = conf.INIT_INTEGRATOR_OPTION;
        G = conf.INIT_G;
        soft = conf.INIT_SOFT;
        stepSize = conf.INIT_STEPSIZE;
        theta = conf.INIT_THETA;
        numberBodies = conf.INIT_NUMBER_BODIES;
        shapeRadius = conf.INIT_RADIUS;

        initBodies();
        initPhysics();
    }


    public Bodies getBodies()
    {
        return bodies;
    }

    public void resetBodies()
    {
        BodiesInit initBodies = new BodiesInit(numberBodies,  initialConditionsType);
        bodies.resetBodies(initBodies);

        physics.setForceFinder(makeForceFinder());
        physics.setIntegrator(makeIntegrator());
    }

    public void setBodies(Bodies bodies)
    {
        this.bodies = bodies;
    }

    public Float getG()
    {
        return G;
    }

    public void setG(Float g)
    {
        G = g;
        physics.setForceFinder(makeForceFinder());
    }

    public Float getSoft()
    {
        return soft;
    }

    public void setSoft(Float soft)
    {
        this.soft = soft;
        physics.setForceFinder(makeForceFinder());
    }

    public Float getTheta()
    {
        return theta;
    }

    public void setTheta(Float theta)
    {
        this.theta = theta;
        physics.setForceFinder(makeForceFinder());
    }

    public Float getStepSize()
    {
        return stepSize;
    }

    public void setStepSize(Float stepSize)
    {
        this.stepSize = stepSize;
        physics.setIntegrator(makeIntegrator());
    }

    public ForceFinderType getForcefinderType()
    {
        return forcefinderType;
    }

    public void setForcefinderType(ForceFinderType forcefinderType)
    {
        this.forcefinderType = forcefinderType;
        physics.setForceFinder(makeForceFinder());
    }

    public IntegratorType getIntegratorType()
    {
        return integratorType;
    }

    public void setIntegratorType(IntegratorType integratorType)
    {
        this.integratorType = integratorType;
        physics.setIntegrator(makeIntegrator());
    }

    public InitialConditionsType getInitialConditionsType()
    {
        return initialConditionsType;
    }

    public void setInitialConditionsType(InitialConditionsType initialConditionsType)
    {
        this.initialConditionsType = initialConditionsType;
    }

    public Float getShapeRadius()
    {
        return shapeRadius;
    }

    public void setShapeRadius(Float shapeRadius)
    {
        this.shapeRadius = shapeRadius;
    }

    public Physics getPhysics()
    {
        return physics;
    }

    public void setPhysics(Physics physics)
    {
        this.physics = physics;
    }

    public int getNumberBodies()
    {
        return numberBodies;
    }

    public void setNumberBodies(int numberBodies)
    {
        this.numberBodies = numberBodies;

        bodies.resizeBodies(new BodiesInit(numberBodies, initialConditionsType));
        physics.setForceFinder(makeForceFinder());
        physics.setIntegrator(makeIntegrator());
    }

}
