package com.mikepenz.materialdrawer.app.entity.test;

public class Items
{
    private String groupId;

    private String delearId;

    private String idealFor;

    private String name;

    private String composition;

    private String brand;

    private String avgRating;

    private String usedFor;

    private String type;

    private String ranking;

    private String upc;

    private Dimentions[] dimentions;

    private String subCategoryName;

    public String getGroupId ()
    {
        return groupId;
    }

    public void setGroupId (String groupId)
    {
        this.groupId = groupId;
    }

    public String getDelearId ()
    {
        return delearId;
    }

    public void setDelearId (String delearId)
    {
        this.delearId = delearId;
    }

    public String getIdealFor ()
    {
        return idealFor;
    }

    public void setIdealFor (String idealFor)
    {
        this.idealFor = idealFor;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getComposition ()
    {
        return composition;
    }

    public void setComposition (String composition)
    {
        this.composition = composition;
    }

    public String getBrand ()
    {
        return brand;
    }

    public void setBrand (String brand)
    {
        this.brand = brand;
    }

    public String getAvgRating ()
    {
        return avgRating;
    }

    public void setAvgRating (String avgRating)
    {
        this.avgRating = avgRating;
    }

    public String getUsedFor ()
    {
        return usedFor;
    }

    public void setUsedFor (String usedFor)
    {
        this.usedFor = usedFor;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getRanking ()
    {
        return ranking;
    }

    public void setRanking (String ranking)
    {
        this.ranking = ranking;
    }

    public String getUpc ()
    {
        return upc;
    }

    public void setUpc (String upc)
    {
        this.upc = upc;
    }

    public Dimentions[] getDimentions ()
    {
        return dimentions;
    }

    public void setDimentions (Dimentions[] dimentions)
    {
        this.dimentions = dimentions;
    }

    public String getSubCategoryName ()
    {
        return subCategoryName;
    }

    public void setSubCategoryName (String subCategoryName)
    {
        this.subCategoryName = subCategoryName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [groupId = "+groupId+", delearId = "+delearId+", idealFor = "+idealFor+", name = "+name+", composition = "+composition+", brand = "+brand+", avgRating = "+avgRating+", usedFor = "+usedFor+", type = "+type+", ranking = "+ranking+", upc = "+upc+", dimentions = "+dimentions+", subCategoryName = "+subCategoryName+"]";
    }
}

			