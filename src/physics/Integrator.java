/**
 * Abstract class for all integrators
 */

package physics;

import java.util.ArrayList;

public abstract class Integrator
{
    protected IntegratorData integratorData;
    protected long duration = 0;

    /**
     * Class that contains all the information needed to find the forces and to update the position
     */
    public static class IntegratorData
    {
        /**
         * Constructor for IntegratorData
         * @param stepSize how for the simulation moves
         * @param position
         * @param velocity
         * @param acceleration
         * @param force
         * @param mass
         */
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

    /**
     * Constructor for Integrator
     * @param integratorData
     */
    public Integrator(IntegratorData integratorData)
    {
        this.integratorData = integratorData;
    }

    /**
     * how long the integrator needed to complete, without taking into account the force finder
     * @return duration of integrator step
     */
    public long getDuration() { return duration; }

    /**
     * find the next position
     * @param forcefinder called by the integrator to find the forces
     */
    public abstract void integrate(ForceFinder forcefinder);
}
