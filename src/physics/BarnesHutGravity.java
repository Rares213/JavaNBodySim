package physics;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BarnesHutGravity extends Gravity
{
    protected BarnesHutData bhData;
    protected QuadTree quadTree;
    protected ExecutorService pool;
    ArrayList<WorkerData> workersData;

    public BarnesHutGravity(BarnesHutData bhData)
    {
        super(bhData.gravityData);
        this.bhData = bhData;

        quadTree = new QuadTree(bhData);

        pool = Executors.newFixedThreadPool(NUM_WORKERS);
        workersData = new ArrayList<WorkerData>(NUM_WORKERS);

        final int bodiesWorker = bhData.gravityData.mass.size() / NUM_WORKERS;

        for(int i = 0; i < NUM_WORKERS; ++i)
            workersData.add(new WorkerData(i * bodiesWorker, (i + 1) * bodiesWorker, quadTree));

        final int remainingBodies = bhData.gravityData.mass.size() % NUM_WORKERS;
        if(remainingBodies > 0)
        {
            final int lastElement = NUM_WORKERS - 1;
            WorkerData lastWorkerData =  workersData.get(lastElement);
            lastWorkerData.endIndex += remainingBodies;
        }

    }

    @Override
    public void findGravity()
    {
        long startTime = System.currentTimeMillis();

        final int count = bhData.gravityData.position.getFirst().size();

        quadTree.initQuadTree();

        for (int i = 0; i < count; ++i)
            quadTree.insert(i, 0);

        quadTree.findCenterOfMass(0);

        for(int i = 0; i < NUM_WORKERS; ++i)
        {
            WorkerData workerData = workersData.get(i);
            workerData.future = pool.submit(workerData);
        }

        for(int i = 0; i < NUM_WORKERS; ++i)
        {
            WorkerData workerData = workersData.get(i);
            try
            {
                workerData.future.get();
            }
            catch(InterruptedException | ExecutionException err)
            {
                System.out.println("Error in future: " + err.getMessage());
            }
        }

        quadTree.clear();

        long endTime = System.currentTimeMillis();
        duration = endTime - startTime;
    }

    static public class BarnesHutData
    {
        GravityData gravityData;
        float theta;

        public BarnesHutData(GravityData gravityData, float theta)
        {
            this.gravityData = gravityData;
            this.theta = theta;
        }
    }

    static protected class WorkerData
            implements Runnable
    {
        int startIndex = 0, endIndex = 0;
        QuadTree quadTree = null;
        Future<?> future;

        WorkerData() {}

        WorkerData(int startIndex, int endIndex, QuadTree quadTree)
        {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.quadTree = quadTree;
        }

        @Override
        public void run()
        {
            for(int i = startIndex; i < endIndex; ++i)
                quadTree.findForce(i, 0);
        }
    }

    protected static final int NUM_WORKERS = 4;

    protected static final int EMPTY = -1;
    protected static final int INTERNAL_NODE = -2;

    static protected class CenterOfMass
    {
        float x, y, mass;

        public CenterOfMass()
        {
            x = 0;
            y = 0;
            mass = 0;
        }
    }

    static protected class Square
    {
        float x, y, halfLen;

        public Square()
        {
            x = 0;
            y = 0;
            halfLen = 0;
        }
    }

    static protected class Node
    {
        Node()
        {
            bodyIndex = EMPTY;
            childrenIndex = 0;
            square = new Square();
            centerOfMass = new CenterOfMass();
        }

        Node(float parentX, float parentY, float parentHalfLen, int bodyIndex, int quadrant)
        {
            this.bodyIndex = bodyIndex;
            childrenIndex = 0;

            centerOfMass = new CenterOfMass();

            square = new  Square();
            square.halfLen = parentHalfLen / 2.0f;

            switch(quadrant)
            {
                case 0:
                    square.x = parentX + square.halfLen;
                    square.y = parentY + square.halfLen;
                    break;

                case 1:
                    square.x = parentX - square.halfLen;
                    square.y = parentY + square.halfLen;
                    break;

                case 2:
                    square.x = parentX + square.halfLen;
                    square.y = parentY - square.halfLen;
                    break;

                case 3:
                    square.x = parentX - square.halfLen;
                    square.y = parentY - square.halfLen;
                    break;

            }

        }

        int bodyIndex;
        int childrenIndex;
        Square square;
        CenterOfMass centerOfMass;
    }

    static protected class QuadTree
    {
        ArrayList<Node> nodes;
        BarnesHutData bhData;

        QuadTree(BarnesHutData bhData)
        {
            this.bhData = bhData;
            nodes = new ArrayList<Node>();
        }

        void insert(int bodyIndex, int nodeIndex)
        {
            final Node parentNode = nodes.get(nodeIndex);
            final ArrayList<Float> bodiesX = bhData.gravityData.position.get(0);
            final ArrayList<Float> bodiesY = bhData.gravityData.position.get(1);

            // if node empty, add the body
            if(nodes.get(nodeIndex).bodyIndex == EMPTY)
                nodes.get(nodeIndex).bodyIndex = bodyIndex;
            // if node has a body, move stored body to a new child node, then add the new body to a child node
            else if(nodes.get(nodeIndex).bodyIndex >= 0)
            {
                // get the quadrant of the stored body
                final int quadrantStoredBody = getQuadrant(bodiesX.get(parentNode.bodyIndex),
                        bodiesY.get(parentNode.bodyIndex),
                        parentNode.square.x,
                        parentNode.square.y);

                // add the children
                if (parentNode.childrenIndex == 0)
                    parentNode.childrenIndex = addNewChildren();

                // find the child index of the current body
                final int indexCurrentBodyToChild = parentNode.childrenIndex + quadrantStoredBody;

                // move the stored body
                nodes.set(indexCurrentBodyToChild, new Node(parentNode.square.x, parentNode.square.y, parentNode.square.halfLen, parentNode.bodyIndex, quadrantStoredBody));

                // get the quadrant of the new body
                final int quadrantNewBody = getQuadrant(bodiesX.get(bodyIndex),
                        bodiesY.get(bodyIndex),
                        parentNode.square.x,
                        parentNode.square.y);

                // find the child index of the new body
                final int indexNewBodyToChild = parentNode.childrenIndex + quadrantNewBody;

                // if the new body has to be put into the same child, we recursively go to the child node
                // else just add the body to empty child
                if(indexNewBodyToChild == indexCurrentBodyToChild)
                    insert(bodyIndex, indexNewBodyToChild);
                else
                    nodes.set(indexNewBodyToChild, new Node(parentNode.square.x, parentNode.square.y, parentNode.square.halfLen, bodyIndex, quadrantNewBody));

                // mark it as internal node
                parentNode.bodyIndex = INTERNAL_NODE;
            }
            else
            {
                // get the quadrant of the new body
                final int quadrantNewBody = getQuadrant(bodiesX.get(bodyIndex),
                        bodiesY.get(bodyIndex),
                        parentNode.square.x,
                        parentNode.square.y);

                // find the child index of the new body
                final int indexNewBodyToChild = parentNode.childrenIndex + quadrantNewBody;

                // if the child node is empty, we add the body,
                // else we got to the child node
                if(nodes.get(indexNewBodyToChild).bodyIndex == EMPTY)
                    nodes.set(indexNewBodyToChild, new Node(parentNode.square.x, parentNode.square.y, parentNode.square.halfLen, bodyIndex, quadrantNewBody));
                else
                    insert(bodyIndex, indexNewBodyToChild);

            }
        }

        void findCenterOfMass(int nodeIndex)
        {
            // depth first search to find the center of mass of each node

            Node parentNode = nodes.get(nodeIndex);

            if(parentNode.bodyIndex == INTERNAL_NODE)
            {
                final int firstChildrenIndex = parentNode.childrenIndex;

                for(int quadrant = 0; quadrant < 4; ++quadrant)
                {
                    final int indexChild = firstChildrenIndex + quadrant;
                    final Node childNode = nodes.get(indexChild);

                    if(nodes.get(indexChild).bodyIndex != EMPTY)
                    {
                        findCenterOfMass(indexChild);

                        parentNode.centerOfMass.x += childNode.centerOfMass.x * childNode.centerOfMass.mass;
                        parentNode.centerOfMass.y += childNode.centerOfMass.y * childNode.centerOfMass.mass;

                        parentNode.centerOfMass.mass += childNode.centerOfMass.mass;
                    }
                }

                parentNode.centerOfMass.x /= parentNode.centerOfMass.mass;
                parentNode.centerOfMass.y /= parentNode.centerOfMass.mass;
            }
            else if (parentNode.bodyIndex >= 0)
            {
                final ArrayList<Float> bodiesX = bhData.gravityData.position.get(0);
                final ArrayList<Float> bodiesY = bhData.gravityData.position.get(1);
                final ArrayList<Float> mass = bhData.gravityData.mass;

                parentNode.centerOfMass.x = bodiesX.get(parentNode.bodyIndex);
                parentNode.centerOfMass.y = bodiesY.get(parentNode.bodyIndex);

                parentNode.centerOfMass.mass = mass.get(parentNode.bodyIndex);
            }
        }

        void findForce(int bodyIndex, int nodeIndex)
        {
            final Node parentNode = nodes.get(nodeIndex);
            final ArrayList<Float> bodiesX = bhData.gravityData.position.get(0);
            final ArrayList<Float> bodiesY = bhData.gravityData.position.get(1);

            final float lengthSquare = 4.0f * parentNode.square.halfLen * parentNode.square.halfLen;

            final float diffX = bodiesX.get(bodyIndex) - parentNode.centerOfMass.x;
            final float diffY = bodiesY.get(bodyIndex) - parentNode.centerOfMass.y;

            final float distanceSquared = diffX * diffX + diffY * diffY;

            if((lengthSquare <= (bhData.theta * bhData.theta * distanceSquared)) || parentNode.bodyIndex >= 0)
            {
                final ArrayList<Float> mass = bhData.gravityData.mass;
                ArrayList<Float> fx = bhData.gravityData.force.get(0);
                ArrayList<Float> fy = bhData.gravityData.force.get(1);

                final float distance = (float)Math.sqrt(distanceSquared);
                final float gravityNumerator = - bhData.gravityData.G * mass.get(bodyIndex) * parentNode.centerOfMass.mass;
                final float gravityDenominator = gravityNumerator / (distanceSquared * distance + bhData.theta);

                Float newFX = fx.get(bodyIndex);
                Float newFY = fy.get(bodyIndex);

                newFX += diffX * gravityDenominator;
                newFY += diffY * gravityDenominator;

                fx.set(bodyIndex, newFX);
                fy.set(bodyIndex, newFY);
            }
            else
            {
                final int childrenIndex = parentNode.childrenIndex;

                for(int quadrant = 0; quadrant < 4; ++quadrant)
                {
                    final int childIndex = childrenIndex + quadrant;

                    if(parentNode.bodyIndex != EMPTY)
                        findForce(bodyIndex, childIndex);
                }
            }
        }

        int getQuadrant(float bodyX, float bodyY, float nodeX, float nodeY)
        {
            if(bodyX > nodeX)
            {
                if(bodyY > nodeY)
                    return 0;
                else
                    return 2;
            }
            else
            {
                if(bodyY > nodeY)
                    return 1;
                else
                    return 3;
            }
        }

        int addNewChildren()
        {
            int firstChildIndex = nodes.size();

            for(int i = 0; i < 4;++i)
                nodes.add(new Node());

            return firstChildIndex;
        }

        void initQuadTree()
        {
            final float maxX = Math.abs(Collections.max(bhData.gravityData.position.get(0), (Comparator<Float>) (f1, f2) ->{
                if(Math.abs(f1) < Math.abs(f2))
                    return -1;
                else if(Math.abs(f1) > Math.abs(f2))
                    return 1;
                else
                    return 0;
            }));
            final float maxY = Math.abs(Collections.max(bhData.gravityData.position.get(1)));

            nodes.add(new Node());
            nodes.get(0).bodyIndex = EMPTY;

            if(maxX > maxY)
                nodes.get(0).square.halfLen = maxX * 3;
            else
                nodes.get(0).square.halfLen = maxY * 3;
        }

        void clear()
        {
            nodes.clear();
        }
    }

}
