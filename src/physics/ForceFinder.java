/**
 * Interface for all force finders
 */

package physics;

public interface ForceFinder
{
    /**
     * Finds the force
     */
    public void findForce();

    /**
     * How long it took
     * @return duration of findForce method
     */
    public long getDuration();
}
