/**
 * Abstract class for gravity.
 * There are a couple of ways to find this force,
 * but all of them have in common this class
 */

package physics;

import java.util.ArrayList;

public abstract class Gravity implements ForceFinder
{
    protected GravityData gravityData;
    protected long duration = 0;

    /**
     * Class that contains the basic info for a gravity force finder
     */
    static public class GravityData
    {

        /**
         * Constructor for GravityData
         * @param G gravitational constant
         * @param soft value that dominates at close distance in order to avoid infinities
         * @param mass bodies mass
         * @param position bodies position
         * @param force bodies force
         * @throws IllegalArgumentException if G or soft are less than 0 or mass/position/force are null
         */
        public GravityData(float G, float soft,
                    ArrayList<Float> mass,
                    ArrayList<ArrayList<Float>> position,
                    ArrayList<ArrayList<Float>> force)
        {
            if(G < 0.0f || soft < 0.0f || mass == null || position == null || force==null)
                throw new IllegalArgumentException("Invalid parameters");

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
