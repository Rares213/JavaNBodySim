package physics;

import java.util.ArrayList;

public abstract class Gravity implements ForceFinder
{
    GravityData gravityData;
    long duration = 0;

    static public class GravityData
    {
        public GravityData(float G, float soft,
                    ArrayList<Float> mass,
                    ArrayList<ArrayList<Float>> position,
                    ArrayList<ArrayList<Float>> force)
        {
            this.G = G;
            this.soft = soft;

            this.mass = mass;
            this.position = position;
            this.force = force;
        }

        float G;
        float soft;

        ArrayList<Float> mass;
        ArrayList<ArrayList<Float>> position;
        ArrayList<ArrayList<Float>> force;
    }

    public Gravity(GravityData gravityData)
    {
        this.gravityData = gravityData;
    }

    @Override
    final public void findForce()
    {
        findGravity();
    }
    public abstract void findGravity();

    @Override
    public long getDuration()
    {
        return duration;
    }

    public void setPosition(ArrayList<ArrayList<Float>> position)
    {
        gravityData.position = position;
    }
}
