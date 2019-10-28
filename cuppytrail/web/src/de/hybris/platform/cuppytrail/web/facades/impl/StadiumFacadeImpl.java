package de.hybris.platform.cuppytrail.web.facades.impl;

import de.hybris.platform.cuppytrail.web.data.StadiumDataTO;
import de.hybris.platform.cuppytrail.web.facades.StadiumFacade;

import java.util.ArrayList;
import java.util.List;
import de.hybris.platform.cuppytrail.services.StadiumService;


public class StadiumFacadeImpl implements StadiumFacade
{

    private StadiumService stadiumService;

    @Override
    public void setStadiumService(final StadiumService stadiumService)
    {
        this.stadiumService = stadiumService;
    }


    @Override
    public StadiumDataTO getStadiumDetails(final String name)
    {
        return new StadiumDataTO("WembleyImplDet", Integer.valueOf(12345));
    }

    @Override
    public List<StadiumDataTO> getAllStadium()
    {
        final List<StadiumDataTO> dataTOs = new ArrayList<StadiumDataTO>();

        return dataTOs;
    }

    @Override
    public StadiumService getStadiumService()
    {
        return stadiumService;
    }

}