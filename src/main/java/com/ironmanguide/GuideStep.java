package com.ironmanguide;

public class GuideStep
{
private final String title;
private final String description;

public GuideStep(String title, String description)
{
this.title = title;
this.description = description;
}

public String getTitle()
{
return title;
}

public String getDescription()
{
return description;
}
}