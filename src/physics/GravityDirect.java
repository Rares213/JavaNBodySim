package physics;

import java.util.ArrayList;
import static java.lang.Math.hypot;

public class GravityDirect extends Gravity
{
    public GravityDirect(GravityData gravityData)
    {
        super(gravityData);
    }

    @Override
    public void findGravity()
    {
        long startTime = System.currentTimeMillis();

        final ArrayList<Float> x = gravityData.position.get(0);
        final ArrayList<Float> y = gravityData.position.get(1);
        ArrayList<Float> mass = gravityData.mass;

        final float G = gravityData.G;
        final float soft = gravityData.soft;
        final int size = gravityData.position.getFirst().size();

        float acummFx, accumFy;

        for(int i = 0; i < size; i++)
        {
            acummFx = 0.0f; accumFy = 0.0f;

            for(int j = 0; j < size; j++)
            {
                final float diffX = x.get(i) - x.get(j);
                final float diffY = y.get(i) - y.get(j);

                float distance = (float) hypot(diffX, diffY) + soft;
                distance = distance * distance * distance;

                final float numeratorProd = -G * mass.get(i) * mass.get(j);
                final float ratioProdDistance = numeratorProd / distance;

                acummFx += diffX * ratioProdDistance;
                accumFy += diffY * ratioProdDistance;
            }

            gravityData.force.get(0).set(i, acummFx);
            gravityData.force.get(1).set(i, accumFy);
        }

        long endTime = System.currentTimeMillis();
        duration = endTime - startTime;
    }
}
