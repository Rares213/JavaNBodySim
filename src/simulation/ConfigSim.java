package simulation;

import physics.ForceFinderType;
import physics.IntegratorType;
import bodies.InitialConditionsType;

public class ConfigSim
{
    static private ConfigSim instance = null;

    static public ConfigSim getInstance()
    {
        if(instance == null)
        {
            instance = new ConfigSim();
        }

        return instance;
    }

    private ConfigSim()
    {}

    public final InitialConditionsType INIT_BODIES_COND = InitialConditionsType.STATIC;
    public final ForceFinderType INIT_FORCE_FINDER_OPTION = ForceFinderType.GRAVITY_DIRECT;
    public final IntegratorType INIT_INTEGRATOR_OPTION = IntegratorType.EULER_IMPLICIT;

    public final String DROPDOWN_PHYSICS_NAME = "Physics settings";

    public final Float INIT_G = 1.0F;
    public final String SLIDER_GRAVITY_NAME = "Gravity";
    public final Float SLIDER_G_STEPSIZE = 0.01F;
    public final Float SLIDER_G_MAX = 10.0F;

    public final Float INIT_SOFT = 0.1F;
    public final String SLIDER_SOFT_NAME = "Soft";
    public final Float SLIDER_SOFT_STEPSIZE = 0.01F;
    public final Float SLIDER_SOFT_MAX = 1.0F;

    public final Float INIT_STEPSIZE = 0.01F;
    public final String SLIDER_STEPSIZE_NAME = "Stepsize";
    public final Float SLIDER_STEPSIZE_STEPSIZE = 0.001F;
    public final Float SLIDER_STEPSIZE_MAX = 1.0F;

    public final String DROPDOWN_BODIES_NAME = "Bodies settings";
    public final Integer INIT_NUMBER_BODIES = 100;
    public final String SLIDER_NUMBER_BODIES_NAME = "Number bodies";
    public final Integer SLIDER_NUMBER_BODIES_MAX = 10000;

    public final String RESET_BODIES_BUTTON_NAME = "Reset Bodies";
    public final String RESET_BODIES_BUTTON_ACTION_COMMAND = "reset";

    public final String DROPDOWN_INTEGRATOR_NAME = "Integrator options";
    public final String EULER_EXPLICIT_BUTTON_NAME = "Euler Explicit";
    public final String EULER_EXPLICIT_BUTTON_ACTION_COMMAND = "eulerExplicit";

    public final String EULER_IMPLICIT_BUTTON_NAME = "Euler Implicit";
    public final String EULER_IMPLICIT_BUTTON_ACTION_COMMAND = "eulerImplicit";

    public final String DROPDOWN_FORCEFINDER_NAME = "ForceFinder options";
    public final String DIRECT_METHOD_BUTTON_NAME = "Direct Method";
    public final String DIRECT_METHOD_BUTTON_ACTION_COMMAND = "directMethod";
}
