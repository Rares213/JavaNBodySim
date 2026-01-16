package physics;

public class Physics
{
    private Integrator integrator;
    private ForceFinder forceFinder;

    public Physics(Integrator integrator, ForceFinder forceFinder)
    {
        this.integrator = integrator;
        this.forceFinder = forceFinder;
    }

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


}
