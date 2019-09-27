package de.hybris.platform.cuppytrail.events;

import de.hybris.platform.core.PK;
import de.hybris.platform.cuppytrail.daos.StadiumDAO;
import de.hybris.platform.cuppytrail.daos.impl.DefaultStadiumDAO;
import de.hybris.platform.cuppytrail.model.StadiumRemoveNotifModel;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.exceptions.ModelLoadingException;
import de.hybris.platform.servicelayer.internal.model.impl.DefaultModelService;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

public class RemoveStadiumEventListener extends AbstractEventListener<RemoveStadiumEvent>
{
    private ModelService modelService;

    private StadiumDAO stadiumDAO;

    @Override
    public void onEvent(final RemoveStadiumEvent event)
    {
        PK stadiumPk = event.getStadiumPk();
        boolean forced = event.getForcedDel();
        int stadiumsLeft = stadiumDAO.findStadiums().size();
        StadiumRemoveNotifModel newNotification = modelService.create(StadiumRemoveNotifModel.class);

        if (stadiumsLeft == 1 & forced == false){
            newNotification.setTargetStadiumPk(stadiumPk.toString());
            newNotification.setExceptionOccurred(false);
            modelService.save(newNotification);
        } else {
            try {
                modelService.remove(stadiumPk);
            } catch (ModelLoadingException ex) {
                newNotification.setTargetStadiumPk(stadiumPk.toString());
                newNotification.setExceptionOccurred(true);
                modelService.save(newNotification);
            }
        }
    }

    @Required
    public void setModelService(DefaultModelService modelService) {
        this.modelService = modelService;
    }

    @Required
    public void setStadiumDAO(DefaultStadiumDAO stadiumDAO) {
        this.stadiumDAO = stadiumDAO;
    }
}