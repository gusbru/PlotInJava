package sample;

public class IntegralValue
{
    private int step;
    private double value;

    public IntegralValue(int step, double value)
    {
        this.step = step;
        this.value = value;
    }

    public int getStep()
    {
        return step;
    }

    public double getValue()
    {
        return value;
    }
}
