package core;

public interface Layer
{
    public void onEvent(Event event);
    public void onUpdate();
    public void onRender();
}
