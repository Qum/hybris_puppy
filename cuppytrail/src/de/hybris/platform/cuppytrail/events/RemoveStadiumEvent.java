package de.hybris.platform.cuppytrail.events;

import de.hybris.platform.core.PK;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;

public class RemoveStadiumEvent extends AbstractEvent {

    private final PK STADIUMPK;
    private final boolean FORCEDDEL;

    public RemoveStadiumEvent(final PK stadiumPk, final boolean forcedDel)
    {
        super();
        this.STADIUMPK = stadiumPk;
        this.FORCEDDEL = forcedDel;
    }
  
    public  PK getStadiumPk() {
        return STADIUMPK;
    }

    public boolean getForcedDel(){
        return FORCEDDEL;
    }
}
