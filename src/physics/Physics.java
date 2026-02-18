/**
 * Class that applies the physics on bodies
 */

package physics;

public class Physics
{
    private Integrator integrator;
    private ForceFinder forceFinder;

    /**
     * Constructor for physics
     * @param integrator
     * @param forceFinder
     */
    public Physics(Integrator integrator, ForceFinder forceFinder)
    {
        this.integrator = integrator;
        this.forceFinder = forceFinder;
    }

    /**
     * Apply the physics
     */
    public void runPhysics()
    {
        integrator.integrate(forceFinder);
    }

    public void setForceFinder(ForceFinder forceFinder)
    {
        this.forceFinder = forceFinder;
    }

    public void setIntegrator(Integrator integrator)
    {
        this.integrator = integrator;
    }

    public long getForcefinderDuration()
    {
        return forceFinder.getDuration();
    }

    public long getIntegratorDuration()
    {
        return integrator.getDuration();
    }

}
