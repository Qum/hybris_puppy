package de.hybris.platform.cuppytrail.services.impl;

import de.hybris.platform.core.PK;
import de.hybris.platform.cuppytrail.events.RemoveStadiumEvent;
import de.hybris.platform.cuppytrail.model.StadiumRemoveNotifModel;
import de.hybris.platform.cuppytrail.services.StadiumService;
import de.hybris.platform.cuppytrail.daos.StadiumDAO;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.List;

import de.hybris.platform.servicelayer.internal.model.impl.DefaultModelService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

public class DefaultStadiumService implements StadiumService {

    static final private Logger LOG = Logger.getLogger(DefaultStadiumService.class);

    @Autowired
    private ModelService modelService;

    private StadiumDAO stadiumDAO;

    @Autowired
    private EventService eventService;

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    /**
     * Gets all stadiums by delegating to {@link StadiumDAO#findStadiums()}.
     */
    @Override
    public List<StadiumModel> getStadiums() {
        return stadiumDAO.findStadiums();
    }

    /**
     * Gets all stadiums for given code by delegating to {@link StadiumDAO#findStadiumsByCode(String)} and then assuring
     * uniqueness of result.
     */
    @Override
    public StadiumModel getStadiumForCode(final String code) throws AmbiguousIdentifierException, UnknownIdentifierException {
        final List<StadiumModel> result = stadiumDAO.findStadiumsByCode(code);
        if (result.isEmpty()) {
            throw new UnknownIdentifierException("Stadium with code '" + code + "' not found!");
        } else if (result.size() > 1) {
            throw new AmbiguousIdentifierException("Stadium code '" + code + "' is not unique, " + result.size()
                    + " stadiums found!");
        }
        return result.get(0);
    }

    @Required
    public void setStadiumDAO(final StadiumDAO stadiumDAO) {
        this.stadiumDAO = stadiumDAO;
    }

    @Required
    public void setModelService(DefaultModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public void removeAllStadiums() {
        List<StadiumModel> allStadiums = stadiumDAO.findStadiums();
        modelService.removeAll(allStadiums);
    }

    @Override
    public String removeStadiumByPk(PK pk, boolean forced) {
        String error = null;
        RemoveStadiumEvent event = new RemoveStadiumEvent(pk, forced);
        eventService.publishEvent(event);
        StadiumRemoveNotifModel notification = findRemoveNotif(pk);
        if (notification != null) {
            if (notification.getExceptionOccurred().booleanValue()) {
                error =  "trouble";
            } else {
                error = "isLast";
            }
            modelService.remove(notification);
        }
        return error;
    }

    private StadiumRemoveNotifModel findRemoveNotif(PK pk) {

        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT {n:").append(StadiumRemoveNotifModel.PK).append("} ");
        builder.append("FROM {").append(StadiumRemoveNotifModel._TYPECODE).append(" AS n} ");
        builder.append("WHERE ").append("{n:").append(StadiumRemoveNotifModel.TARGETSTADIUMPK).append("} = ");
        builder.append(pk.toString());

        final List<StadiumRemoveNotifModel> list = flexibleSearchService.<StadiumRemoveNotifModel>search(builder.toString()).getResult();
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

}