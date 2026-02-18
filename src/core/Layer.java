/**
 * Interface for classes that want to be called
 * every iteration by the core
 */

package core;

public interface Layer
{
    /**
     * Update the class
     */
    public void onUpdate();

    /**
     * What to render on screen
     */
    public void onRender();
}
