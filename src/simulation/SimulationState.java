package simulation;

import bodies.InitialConditionsType;
import physics.ForceFinderType;
import physics.IntegratorType;

public class SimulationState
{
    public SimulationState()
    {
        ConfigSim conf = ConfigSim.getInstance();

        icType = conf.INIT_BODIES_COND;
        ffType = conf.INIT_FORCE_FINDER_OPTION;
        intType = conf.INIT_INTEGRATOR_OPTION;
        G = conf.INIT_G;
        soft = conf.INIT_SOFT;
        stepSize = conf.INIT_STEPSIZE;
        numberBodies = conf.INIT_NUMBER_BODIES;
    }

    public InitialConditionsType getIcType()
    {
        return icType;
    }

    public void setIcType(InitialConditionsType icType)
    {
        this.icType = icType;
    }

    public ForceFinderType getFfType()
    {
        return ffType;
    }

    public void setFfType(ForceFinderType ffType)
    {
        this.ffType = ffType;
    }

    public IntegratorType getIntType()
    {
        return intType;
    }

    public void setIntType(IntegratorType intType)
    {
        this.intType = intType;
    }

    public float getG()
    {
        return G;
    }

    public void setG(float g)
    {
        G = g;
    }

    public float getSoft()
    {
        return soft;
    }

    public void setSoft(float soft)
    {
        this.soft = soft;
    }

    public float getStepSize()
    {
        return stepSize;
    }

    public void setStepSize(float stepSize)
    {
        this.stepSize = stepSize;
    }

    public int getNumberBodies()
    {
        return numberBodies;
    }

    public void setNumberBodies(int numberBodies)
    {
        this.numberBodies = numberBodies;
    }

    private InitialConditionsType icType;
    private ForceFinderType ffType;
    private IntegratorType intType;
    private float G;
    private float soft;
    private float stepSize;
    private int numberBodies;
}
