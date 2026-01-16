package physics;

import java.util.ArrayList;

public abstract class Gravity implements ForceFinder
{
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

            this.duration = 0.0f;
        }

        float G;
        float soft;
        float duration;

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

    public float getDuration()
    {
        return gravityData.duration;
    }
    public void setPosition(ArrayList<ArrayList<Float>> position)
    {
        gravityData.position = position;
    }

    GravityData gravityData;
}
