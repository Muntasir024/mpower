package org.example.Service;

import org.example.Model.AdapterData;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AdapterService {


    private AdapterData adapterData1 = null;

    public void setAdapterData(AdapterData data) {

        AdapterData adapterData = new AdapterData();

        adapterData.set_id(data.get_id());
        adapterData.setProviderId(data.getProviderId());
        adapterData.setObs(data.getObs());
        adapterData.set_rev(data.get_rev());
        adapterData.setType(data.getType());
        adapterData.setTeam(data.getTeam());
        adapterData.setTeamId(data.getTeamId());
        adapterData.setVersion(data.getVersion());
        adapterData.setBaseEntityId(data.getBaseEntityId());
        adapterData.setDuration(data.getDuration());
        adapterData.setEventDate(data.getEventDate());
        adapterData.setEventType(data.getEventType());
        adapterData.setEntityType(data.getEntityType());
        adapterData.setLocationId(data.getLocationId());
        adapterData.setDateCreated(data.getDateCreated());
        adapterData.setServerVersion(data.getServerVersion());
        adapterData.setIsSendToOpenMRS(data.getIsSendToOpenMRS());
        adapterData.setFormSubmissionId(data.getFormSubmissionId());
        adapterData.setClientDatabaseVersion(data.getClientDatabaseVersion());
        adapterData.setClientApplicationVersion(data.getClientApplicationVersion());


        adapterData1 = adapterData;

    }

    public AdapterData getAdapterData(@RequestBody AdapterData data) {
        if(adapterData1 == null) setAdapterData(data);
        return adapterData1;
    }
}
