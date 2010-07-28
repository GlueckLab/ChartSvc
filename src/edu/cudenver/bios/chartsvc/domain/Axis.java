package edu.cudenver.bios.chartsvc.domain;

public class Axis
{
	protected String label;
	protected int numberTicks = 5;
	
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

}
