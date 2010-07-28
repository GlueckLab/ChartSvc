package edu.cudenver.bios.chartsvc.domain;

public class Axis
{
	protected String label = "";
	protected int numberTicks = 5;
	protected double rangeMin = Double.NaN;
	protected double rangeMax = Double.NaN;

	public Axis(String label)
	{
		this.label = label;
	}

	public String getLabel()
	{
		return label;
	}

	public int getNumberTicks()
	{
		return numberTicks;
	}

	public void setNumberTicks(int numberTicks)
	{
		this.numberTicks = numberTicks;
	}

    public double getRangeMin()
    {
        return rangeMin;
    }

    public void setRangeMin(double rangeMin)
    {
        this.rangeMin = rangeMin;
    }

    public double getRangeMax()
    {
        return rangeMax;
    }

    public void setRangeMax(double rangeMax)
    {
        this.rangeMax = rangeMax;
    }

}
