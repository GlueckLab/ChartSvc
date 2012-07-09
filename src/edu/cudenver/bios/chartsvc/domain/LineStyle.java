package edu.cudenver.bios.chartsvc.domain;


public class LineStyle 
{

    protected String lineStyleLabel = null;
    protected double width = 1.0f;
    protected double dashLength = 1.0f;
    protected double spaceLength = 10.0f;
    
    /*protected ArrayList<Double> width = new ArrayList<Double>();
    protected ArrayList<Double> dashLength = new ArrayList<Double>();
    protected ArrayList<Double> spaceLength = new ArrayList<Double>();*/
    
        
    public void setWidth(double width)
    {
        this.width = width;
    }
    
    public void setDashLength(double dashLength)
    {
        this.dashLength = dashLength;
    }
    
    public void setSpaceLength(double spaceLength)
    {
        this.spaceLength = spaceLength;
    }
    
    public double getWidth()
    {
        return width;
    }
    
    public double getDashLength()
    {
        return dashLength;
    }
    
    public double getSpaceLength()
    {
        return spaceLength;
    }
    
    /*public void addLineWidth(double lineWidth)
    {
        width.add(lineWidth);
    }
    
    public void addDashLength(double dashLengthSize)
    {
        dashLength.add(dashLengthSize);
    }
    
    public void addSpaceLength(double spaceLengthSize)
    {
        spaceLength.add(spaceLengthSize);
    }
    
    public List<Double> getLineWidth()
    {
        return width;
    }
    
    public List<Double> getLineDashLength()
    {
        return dashLength;
    }
    
    public List<Double> getLineSpaceLength()
    {
        return spaceLength;
    }*/
}
