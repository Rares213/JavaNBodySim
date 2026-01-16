package physics;

import java.util.ArrayList;

public abstract class Integrator
{
    public static class IntegratorData
    {
        public IntegratorData(float stepSize,
                       ArrayList<ArrayList<Float>> position,
                       ArrayList<ArrayList<Float>> velocity,
                       ArrayList<ArrayList<Float>> acceleration,
                       ArrayList<ArrayList<Float>> force,
                       ArrayList<Float> mass)
        {
            this.stepSize = stepSize;
            this.position = position;
            this.velocity = velocity;
            this.acceleration = acceleration;
            this.force = force;
            this.mass = mass;
        }


        float stepSize;

        ArrayList<ArrayList<Float>> position;
        ArrayList<ArrayList<Float>> velocity;
        ArrayList<ArrayList<Float>> acceleration;
        ArrayList<ArrayList<Float>> force;
        ArrayList<Float> mass;
    }

    public Integrator(IntegratorData integratorData)
    {
        this.integratorData = integratorData;
    }

    public abstract void integrate(ForceFinder forcefinder);

    IntegratorData integratorData;
}
