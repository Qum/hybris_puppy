package de.hybris.platform.cuppytrailfrontend.controller;

import de.hybris.platform.core.PK;
import de.hybris.platform.cuppytrail.data.StadiumData;
import de.hybris.platform.cuppytrail.facades.StadiumFacade;
import de.hybris.platform.cuppytrailfrontend.StadiumsNameEncoded;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class StadiumsController
{
    private StadiumFacade stadiumFacade;

    static final private Logger LOG = Logger.getLogger(StadiumsController.class);

    @RequestMapping(value = "/stadiums")
    public String showStadiums(final Model model)
    {
        final List<StadiumData> stadiums = stadiumFacade.getStadiums();
        model.addAttribute("stadiums", stadiums);
        System.out.println();
        return "StadiumHw";
    }

    @RequestMapping(value = "/stadiums/{stadiumName}")
    public String showStadiumDetails(@PathVariable String stadiumName, final Model model) throws UnsupportedEncodingException
    {
        stadiumName = URLDecoder.decode(stadiumName, "UTF-8");
        final StadiumData stadium = stadiumFacade.getStadium(stadiumName);
        stadium.setName(StadiumsNameEncoded.getNameEncoded(stadium.getName()));
        model.addAttribute("stadium", stadium);
        return "StadiumDetails";
    }

    @RequestMapping(value = "/stadiums/remove/{selectedPk}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String removeStadium(@RequestParam(value="accept", defaultValue = "false") String acceptRemove, @PathVariable String selectedPk, final Model model) throws UnsupportedEncodingException
    {
        selectedPk = URLDecoder.decode(selectedPk, "UTF-8");
        boolean forced  = new Boolean(acceptRemove).booleanValue();
        PK pk = PK.parse(selectedPk);
        String error = stadiumFacade.removeStadium(pk,forced);

        if (error != null) {
            return error;
        }
        return "OK";
    }

    @RequestMapping(value = "/stadiums/removeall")
    public String removeAllStadiums(final Model model)
    {
        stadiumFacade.removeAllStadiums();
        return "redirect:/stadiums";
    }

    @Autowired
    public void setFacade(final StadiumFacade facade)
    {
        this.stadiumFacade = facade;
    }
}