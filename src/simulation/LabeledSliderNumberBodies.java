package simulation;

import core.LabeledSliderInt;

public class LabeledSliderNumberBodies extends LabeledSliderInt
{
    public LabeledSliderNumberBodies()
    {
        ConfigSim config = ConfigSim.getInstance();

        super(config.SLIDER_NUMBER_BODIES_NAME, config.SLIDER_NUMBER_BODIES_MAX, config.INIT_NUMBER_BODIES);
    }

    @Override
    public String toString()
    {
        return "LabeledSlider: NumberBodies" + " Value: " + super.slider.getValue();
    }
}
