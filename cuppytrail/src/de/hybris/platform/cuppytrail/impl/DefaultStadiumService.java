package de.hybris.platform.cuppytrail.impl;

import de.hybris.platform.core.PK;
import de.hybris.platform.cuppytrail.services.StadiumService;
import de.hybris.platform.cuppytrail.daos.StadiumDAO;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.List;

import de.hybris.platform.servicelayer.internal.model.impl.DefaultModelService;
import de.hybris.platform.servicelayer.model.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

public class DefaultStadiumService implements StadiumService
{


    private ModelService modelService;

    private StadiumDAO stadiumDAO;

    /**
     * Gets all stadiums by delegating to {@link StadiumDAO#findStadiums()}.
     */
    @Override
    public List<StadiumModel> getStadiums()
    {
        return stadiumDAO.findStadiums();
    }

    /**
     * Gets all stadiums for given code by delegating to {@link StadiumDAO#findStadiumsByCode(String)} and then assuring
     * uniqueness of result.
     */
    @Override
    public StadiumModel getStadiumForCode(final String code) throws AmbiguousIdentifierException, UnknownIdentifierException
    {
        final List<StadiumModel> result = stadiumDAO.findStadiumsByCode(code);
        if (result.isEmpty())
        {
            throw new UnknownIdentifierException("Stadium with code '" + code + "' not found!");
        }
        else if (result.size() > 1)
        {
            throw new AmbiguousIdentifierException("Stadium code '" + code + "' is not unique, " + result.size()
                    + " stadiums found!");
        }
        return result.get(0);
    }

    @Required
    public void setStadiumDAO(final StadiumDAO stadiumDAO)
    {
        this.stadiumDAO = stadiumDAO;
    }

    @Required
    public void setModelService(DefaultModelService modelService) { this.modelService = modelService; }


    @Override
    public void removeAllStadiums() {
        List<StadiumModel> allStadiums = stadiumDAO.findStadiums();
        modelService.removeAll(allStadiums);
    }


    @Override
    public void removeStadiumByPk(PK pk) {
        modelService.remove(pk);
    }


}