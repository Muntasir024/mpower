package org.example.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class AdapterData {

    private String _id;
    private List<Obs> obs;
    private String _rev;
    private String team;
    private String type;
    private String teamId;
    private Long version;
    private Long duration;
    private String eventDate;
    private String eventType;
    private String entityType;
    private String locationId;
    private String providerId;
    private Date dateCreated;
    private String baseEntityId;
    private Long serverVersion;
    private String isSendToOpenMRS;
    private String formSubmissionId;
    private String clientDatabaseVersion;
    private String clientApplicationVersion;

}
