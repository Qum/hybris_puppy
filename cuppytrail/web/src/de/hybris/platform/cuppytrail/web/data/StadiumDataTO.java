package de.hybris.platform.cuppytrail.web.data;

import java.util.List;


/**
 * This class belongs to the Source Code Trail documented at https://wiki.hybris.com/display/pm/Source+Code+Tutorial It
 * is the data transfer object for the Stadium Facade
 */
public class StadiumDataTO
{
    private final String capacity;
    private final String name;
    private List<MatchSummaryDataTO> matches;

    public StadiumDataTO(final String name, final Integer capacity)
    {
        this.name = name;
        this.matches = null;
        this.capacity = capacity == null ? "-" : capacity.toString();
    }

    public StadiumDataTO(final String name, final Integer capacity, final List<MatchSummaryDataTO> matches)
    {
        this(name, capacity);
        this.matches = matches;
    }

    public String getName()
    {
        return name;
    }

    public String getCapacity()
    {
        return capacity;
    }

    public List<MatchSummaryDataTO> getMatches()
    {
        return matches;
    }


}