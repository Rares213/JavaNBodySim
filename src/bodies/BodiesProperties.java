package bodies;

import java.util.ArrayList;
import java.util.Collections;

public class BodiesProperties
{
    public BodiesProperties(int size)
    {
        this.size = size;
        this.dimensions = 2;

        this.position = new ArrayList<ArrayList<Float>>(this.dimensions);
        this.position.add(0, new ArrayList<Float>(Collections.nCopies(this.size, 0.0f)));
        this.position.add(1, new ArrayList<Float>(Collections.nCopies(this.size, 0.0f)));

        this.velocity = new ArrayList<ArrayList<Float>>(this.dimensions);
        this.velocity.add(0, new ArrayList<Float>(Collections.nCopies(this.size, 0.0f)));
        this.velocity.add(1, new ArrayList<Float>(Collections.nCopies(this.size, 0.0f)));

        this.acceleration = new ArrayList<ArrayList<Float>>(this.dimensions);
        this.acceleration.add(0, new ArrayList<Float>(Collections.nCopies(this.size, 0.0f)));
        this.acceleration.add(1, new ArrayList<Float>(Collections.nCopies(this.size, 0.0f)));

        this.force = new ArrayList<ArrayList<Float>>(this.dimensions);
        this.force.add(0, new ArrayList<Float>(Collections.nCopies(this.size, 0.0f)));
        this.force.add(1, new ArrayList<Float>(Collections.nCopies(this.size, 0.0f)));

        this.mass = new ArrayList<ArrayList<Float>>(1);
        this.mass.add(0, new ArrayList<Float>(Collections.nCopies(this.size, 0.0f)));
    }

    void setSize(int size)
    {
        this.size = size;
    }

    void trimArrays()
    {

    }

    public int getDimensions()
    {
        return dimensions;
    }

    public int getSize()
    {
        return size;
    }

    public ArrayList<ArrayList<Float>> getPosition()
    {
        return position;
    }

    public ArrayList<ArrayList<Float>> getVelocity()
    {
        return velocity;
    }

    public ArrayList<ArrayList<Float>> getAcceleration()
    {
        return acceleration;
    }

    public ArrayList<ArrayList<Float>> getForce()
    {
        return force;
    }

    public ArrayList<ArrayList<Float>> getMass()
    {
        return mass;
    }

    private int size;
    private int dimensions;

    private ArrayList<ArrayList<Float>> position;
    private ArrayList<ArrayList<Float>> velocity;
    private ArrayList<ArrayList<Float>> acceleration;
    private ArrayList<ArrayList<Float>> force;
    private ArrayList<ArrayList<Float>> mass;
}
