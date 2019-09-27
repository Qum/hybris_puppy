package de.hybris.platform.cuppytrail.facades;

import de.hybris.platform.core.PK;
import de.hybris.platform.cuppytrail.data.StadiumData;

import java.util.List;


public interface StadiumFacade
{
    StadiumData getStadium(String name);

    List<StadiumData> getStadiums();

    String removeStadium(PK pk,boolean force);

    void removeAllStadiums();

}