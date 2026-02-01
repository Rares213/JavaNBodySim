package bodies;

import java.util.Random;

public class BodiesInit
{
    private BodiesProperties bodiesProperties;

    public BodiesInit(int size, InitialConditionsType initCont)
    {
        this.bodiesProperties = new BodiesProperties(size);

        switch (initCont)
        {
            case STATIC:
                staticInit();
                break;

            case SPIRAL:
                spiralInit();
                break;
        }
    }

    public BodiesProperties getBodiesProperties()
    {
        return bodiesProperties;
    }

    private void staticInit()
    {
        explicitMassInit(0, bodiesProperties.getSize(), 10.0f);
        explicitStaticInit(0, bodiesProperties.getSize(), 250.0f);
    }

    private void spiralInit()
    {
        throw new UnsupportedOperationException("Spiral bodies not supported yet.");
    }

    private void explicitStaticInit(int indexStart, int indexEnd, float width)
    {
        Random rand = new Random();

        for(int dimension = 0; dimension < bodiesProperties.getDimensions(); dimension++)
            for(int bodyIndex = indexStart; bodyIndex < indexEnd; bodyIndex++)
            {
                float coordRand = ((rand.nextFloat() - 0.5f) * 2.0f) * width;
                this.bodiesProperties.getPosition().get(dimension).set(bodyIndex, coordRand);
            }
    }

    private void explicitMassInit(int startIndex, int endIndex, float massRange)
    {
        Random rand = new Random();

        for(int bodyIndex = startIndex; bodyIndex < endIndex; bodyIndex++)
        {
            float massRand = rand.nextFloat() * massRange + 1.0f;
            this.bodiesProperties.getMass().get(0).set(bodyIndex, massRand);
        }
    }

}
