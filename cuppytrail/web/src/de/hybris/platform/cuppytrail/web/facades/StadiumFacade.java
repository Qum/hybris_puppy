package de.hybris.platform.cuppytrail.web.facades;

import de.hybris.platform.core.PK;
import de.hybris.platform.cuppytrail.web.data.StadiumDataTO;

import de.hybris.platform.cuppytrail.services.StadiumService;

import java.util.List;


/**
 * This interface belongs to the Source Code Trail documented at https://wiki.hybris.com/display/pm/Source+Code+Tutorial
 */
public interface StadiumFacade
{

    void setStadiumService(StadiumService stadiumService);

    StadiumService getStadiumService();

    StadiumDataTO getStadiumDetails(String name);

    List<StadiumDataTO> getAllStadium();

}