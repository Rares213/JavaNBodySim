/**
 * Class that represents a bunch of bodies
 */

package bodies;

import java.awt.*;
import java.util.ArrayList;


public class Bodies
{
    private BodiesProperties bodiesProperties;

    public enum Property
    {
        POSITION,
        VELOCITY,
        ACCELERATION,
        FORCE,
        MASS
    }

    /**
     * Class constructor
     * @param bodiesInit
     */
    public Bodies(BodiesInit bodiesInit)
    {
        this.bodiesProperties = bodiesInit.getBodiesProperties();
    }

    /**
     * Get one of the bodies properties
     * @param property
     * @return
     */
    public ArrayList<ArrayList<Float>> getBodiesProperty(Property property)
    {
        return switch (property)
        {
            case POSITION     -> bodiesProperties.getPosition();
            case VELOCITY     -> bodiesProperties.getVelocity();
            case ACCELERATION -> bodiesProperties.getAcceleration();
            case FORCE        -> bodiesProperties.getForce();
            case MASS         -> bodiesProperties.getMass();
            default -> throw new IllegalArgumentException("Invalid property");
        };
    }

    /**
     * Get all the properties
     * @return
     */
    public BodiesProperties getBodiesProperties()
    {
        return bodiesProperties;
    }

    /**
     * Method for adding or removing bodies
     * @param resizeBodiesInit
     */
    public void resizeBodies(BodiesInit resizeBodiesInit)
    {
        BodiesProperties resizeBodiesProperties = resizeBodiesInit.getBodiesProperties();

        final int currentSize = bodiesProperties.getSize();
        final int resizeSize = resizeBodiesProperties.getSize();

        if(currentSize == resizeSize)
            return;

        if(currentSize > resizeSize)
        {
            for(int i = 0; i < bodiesProperties.getDimensions(); ++i)
            {
                this.bodiesProperties.getPosition().get(i).subList(resizeSize, currentSize).clear();
                this.bodiesProperties.getVelocity().get(i).subList(resizeSize, currentSize).clear();
                this.bodiesProperties.getAcceleration().get(i).subList(resizeSize, currentSize).clear();
                this.bodiesProperties.getForce().get(i).subList(resizeSize, currentSize).clear();
            }

            this.bodiesProperties.getMass().getFirst().subList(resizeSize, currentSize).clear();

            bodiesProperties.setSize(resizeSize);
        }
        else
        {
            final int sizeToIter = resizeSize - currentSize;

            for(int bodyIndex = 0; bodyIndex < sizeToIter;++bodyIndex)
            {
                for(int dimension = 0;dimension < bodiesProperties.getDimensions();++dimension)
                {
                    bodiesProperties.getPosition().get(dimension).add(resizeBodiesProperties.getPosition().get(dimension).get(bodyIndex));
                    bodiesProperties.getVelocity().get(dimension).add(resizeBodiesProperties.getVelocity().get(dimension).get(bodyIndex));
                    bodiesProperties.getAcceleration().get(dimension).add(resizeBodiesProperties.getAcceleration().get(dimension).get(bodyIndex));
                    bodiesProperties.getForce().get(dimension).add(resizeBodiesProperties.getForce().get(dimension).get(bodyIndex));
                }

                bodiesProperties.getMass().getFirst().add(resizeBodiesProperties.getMass().getFirst().get(bodyIndex));
            }

            bodiesProperties.setSize(currentSize +  sizeToIter);
        }

    }

    /**
     * Reinitialize the bodies
     * @param bodiesInit
     */
    public void resetBodies(BodiesInit bodiesInit)
    {
        bodiesProperties = bodiesInit.getBodiesProperties();
    }

    /**
     * Zeroes the forces for all bodies.
     * Must be called after every iteration
     */
    public void resetForce()
    {
        for(int i = 0; i < bodiesProperties.getSize(); i++)
        {
            bodiesProperties.getForce().get(0).set(i, 0.0f);
            bodiesProperties.getForce().get(1).set(i, 0.0f);
        }
    }



}
