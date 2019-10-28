package de.hybris.platform.cuppytrail.jobs;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.cuppytrail.services.StadiumService;
import de.hybris.platform.cuppytrail.services.impl.DefaultStadiumService;
import de.hybris.platform.impex.jalo.ImpExManager;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.CSVConstants;

import org.springframework.beans.factory.annotation.Required;

import java.io.InputStream;

public class ImportStadiumsJob extends AbstractJobPerformable<CronJobModel> {

    private StadiumService stadiumService;

    @Override
    public PerformResult perform(final CronJobModel cronJob) {

        int stadiumsFound = stadiumService.getStadiums().size();

        if (stadiumsFound != 0) {
            InputStream is = ImpExManager.class.getResourceAsStream("/impex/ImportStadiumsForJobHW.impex");
            ImpExManager.getInstance().importData(is, "UTF-8",
                    CSVConstants.HYBRIS_FIELD_SEPARATOR, CSVConstants.HYBRIS_QUOTE_CHARACTER, true);
        }

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    @Required
    public void setStadiumService(DefaultStadiumService stadiumService) {
    }
}