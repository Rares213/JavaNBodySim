/**
 * Class that implements Implicit Euler, integrator that conserves the energy
 */

package physics;

import java.util.ArrayList;

public class EulerImplicit extends Integrator
{
    /**
     * Constructor for Implicit Euler
     * @param integratorData date needed for the class
     */
    public EulerImplicit(IntegratorData integratorData)
    {
        super(integratorData);
    }

    @Override
    public void integrate(ForceFinder forcefinder)
    {
        forcefinder.findForce();

        long startTime = System.currentTimeMillis();

        ArrayList<Float> fx = integratorData.force.get(0);
        ArrayList<Float> fy = integratorData.force.get(1);

        ArrayList<Float> vx = integratorData.velocity.get(0);
        ArrayList<Float> vy = integratorData.velocity.get(1);

        ArrayList<Float> x = integratorData.position.get(0);
        ArrayList<Float> y = integratorData.position.get(1);

        ArrayList<Float> mass = integratorData.mass;

        final float stepSize = integratorData.stepSize;
        final int size = integratorData.mass.size();

        for(int i = 0; i < size; i++)
        {
            final float ax = fx.get(i) / mass.get(i);
            final float ay = fy.get(i) / mass.get(i);

            final float newVx = vx.get(i) + stepSize * ax;
            vx.set(i, newVx);

            final float newVy = vy.get(i) + stepSize * ay;
            vy.set(i, newVy);

            final float newX = x.get(i) +  stepSize * newVx;
            x.set(i, newX);

            final float newY = y.get(i) + stepSize * newVy;
            y.set(i, newY);
        }

        long endTime = System.currentTimeMillis();
        duration = endTime - startTime;
    }
}
