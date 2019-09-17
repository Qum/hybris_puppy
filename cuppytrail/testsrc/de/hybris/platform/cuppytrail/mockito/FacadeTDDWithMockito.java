/**
 * This class belongs to the Source Code Trail documented at https://wiki.hybris.com/display/pm/Source+Code+Tutorial
 */
package de.hybris.platform.cuppytrail.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.cuppytrail.services.StadiumService;
import de.hybris.platform.cuppytrail.web.data.StadiumDataTO;
import de.hybris.platform.cuppytrail.web.facades.StadiumFacade;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class FacadeTDDWithMockito
{
    @Mock
    private StadiumFacade stadiumFacade;
    @Mock
    private StadiumService stadiumService; // Service is mocked out by Mockito
    @Mock
    private StadiumModel stadiumModel;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this); // Tells Mockito to set up its annotated mocks

        final List<StadiumModel> stadiumModels = new ArrayList<StadiumModel>();
        stadiumModels.add(newStadiumModel("Wembley", Integer.valueOf(12345)));
        stadiumModels.add(newStadiumModel("Allianz", Integer.valueOf(23456)));


        final List<StadiumDataTO> stadiumDataTOES = new ArrayList<StadiumDataTO>();
        stadiumDataTOES.add(new StadiumDataTO("Test1", 1341241));
        stadiumDataTOES.add(new StadiumDataTO("Test2", 1341241));

        when(stadiumFacade.getAllStadium()).thenReturn(stadiumDataTOES);

        when(stadiumFacade.getStadiumService()).thenReturn(stadiumService);
        when(stadiumFacade.getStadiumService().getAllStadium()).thenReturn(stadiumModels);
    }

    @Test
    public void testFacade1()
    {
        when( stadiumFacade.getStadiumDetails("Wembley")).thenReturn(new StadiumDataTO("Test3", 123123));

        final List<StadiumDataTO> stadiumDataTOs = stadiumFacade.getAllStadium();
        assertEquals(2, stadiumDataTOs.size());

        final StadiumDataTO stadiumDataTO = stadiumFacade.getStadiumDetails("Wembley");
        assertNotNull(stadiumDataTO);
    }

    public void errorTestFacade(){

    }

    @Test
    public void testFacade2()
    {
        when(stadiumFacade.getStadiumService().getStadiumDetails("Wembley")).thenReturn(stadiumModel);

        final List<StadiumModel> stadiumModels = stadiumFacade.getStadiumService().getAllStadium();
        assertEquals(2, stadiumModels.size());

        final StadiumModel stadiumModel = stadiumFacade.getStadiumService().getStadiumDetails("Wembley");
        assertNotNull(stadiumModel);
    }

    // Convenience method for creating a StadiumModel
    private StadiumModel newStadiumModel(final String name, final Integer capacity)
    {
        final StadiumModel model = new StadiumModel();
        model.setCode(name);
        model.setCapacity(capacity);
        return model;
    }
}