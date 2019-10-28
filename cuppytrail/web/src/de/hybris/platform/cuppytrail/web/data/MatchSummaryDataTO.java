package de.hybris.platform.cuppytrail.web.data;

import java.text.DateFormat;
import java.util.Date;


/**
 * This class belongs to the Source Code Trail documented at https://wiki.hybris.com/display/pm/Source+Code+Tutorial It
 * is the data transfer object for the Stadium Facade
 */
public class MatchSummaryDataTO
{
    private final String guestTeam;
    private final String homeTeam;
    private String homeGoals;
    private String guestGoals;
    private final Date date;

    public MatchSummaryDataTO(final String homeTeam, final String guestTeam, final Date date)
    {
        this.guestTeam = guestTeam;
        this.homeTeam = homeTeam;
        this.date = date;
    }

    public String getGuestTeam()
    {
        return guestTeam;
    }

    public String getHomeTeam()
    {
        return homeTeam;
    }

    public void setHomeGoals(final String homeGoals)
    {
        this.homeGoals = homeGoals;
    }

    public void setGuestGoals(final String guestGoals)
    {
        this.guestGoals = guestGoals;
    }

    @Override
    public String toString()
    {
        final String formatedDate = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date);

        return homeTeam + ":( " + homeGoals + " ) " + guestTeam + " ( " + guestGoals + " ) " + formatedDate;
    }
}