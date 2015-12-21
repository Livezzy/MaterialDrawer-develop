package com.mikepenz.materialdrawer.app.entity.test;

public class SubCategoryListingResponse
{
    private Items[] items;

    private String description;

    private String isSuccess;

    public Items[] getItems ()
    {
        return items;
    }

    public void setItems (Items[] items)
    {
        this.items = items;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getIsSuccess ()
    {
        return isSuccess;
    }

    public void setIsSuccess (String isSuccess)
    {
        this.isSuccess = isSuccess;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [items = "+items+", description = "+description+", isSuccess = "+isSuccess+"]";
    }
}

		