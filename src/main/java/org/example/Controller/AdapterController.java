package org.example.Controller;

import ca.uhn.fhir.context.FhirContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Model.AdapterData;
import org.example.Model.Content;
import org.example.Model.Obs;
import org.example.Service.AdapterService;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class AdapterController {


    @Autowired
    private AdapterService adapterService;

    @PostMapping("/HDA-Mpower")
    public String createBundle(@RequestBody AdapterData adapterData1)
            throws JsonProcessingException, ParseException {

        AdapterData adapterData = adapterService.getAdapterData(adapterData1);

        //System.out.println(adapterData);

        // Health id or subscriber id
        String healthId = "";

        for(Obs data : adapterData.getObs()) {
            if(data.getFieldDataType().equals("subscriberid")) {
                healthId = data.getValues().get(0);
            }
        }

        //System.out.println(healthId);


        Bundle bundle = new Bundle();
        bundle.setId(adapterData.get_id());
        bundle.setType(Bundle.BundleType.COLLECTION);

        Meta meta = new Meta();
        meta.setLastUpdated(adapterData.getDateCreated());
        bundle.setMeta(meta);

        List<Bundle.BundleEntryComponent> entries = new ArrayList<>();


        // Composition Start

        Bundle.BundleEntryComponent entry = new Bundle.BundleEntryComponent();
        entry.setFullUrl(adapterData.get_id());

        Composition composition = new Composition();
        Identifier identifier = new Identifier();
        identifier.setValue(adapterData.get_id());
        composition.setIdentifier(identifier);

        composition.setDate(adapterData.getDateCreated());
        composition.setTitle("ANC Home Visit");
        composition.setSubject(new Reference("http://{shared-health-url}/patients/" + healthId));

        List<Reference> resourceAuthors = new ArrayList<>();
        resourceAuthors.add(new Reference("http://{shared-health-url}/facilities/BRAC"));
        composition.setAuthor(resourceAuthors);

        CodeableConcept codeableConcept = new CodeableConcept();
        List<Coding> typeCoding = new ArrayList<>();
        Coding coding1 = new Coding();
        coding1.setCode("51899-3");
        coding1.setDisplay("DetailsDocument");
        coding1.setSystem("http://hl7.org/fhir/vs/doc-typecodes");
        typeCoding.add(coding1);
        codeableConcept.setCoding(typeCoding);
        composition.setType(codeableConcept);


        entry.setResource(composition);
        entries.add(entry);


        // End of Composition



        // Entries


        for(Obs obs : adapterData.getObs()) {

            // Observation Code
            if(obs.getFieldCode().equals("pregnancy_symptoms") | obs.getFieldCode().equals("has_edema") | obs.getFieldCode().equals("has_other_complication")) {


                Bundle.BundleEntryComponent entry1 = new Bundle.BundleEntryComponent();
                UUID uuid = UUID.randomUUID();
                entry1.setId(uuid.toString());

                Observation observation = new Observation();

                List<Identifier> identifierList = new ArrayList<>();
                Identifier identifier1= new Identifier();
                identifier1.setValue(uuid.toString());
                identifierList.add(identifier1);
                observation.setIdentifier(identifierList);

                observation.setSubject(new Reference("http://{shared-health-url}/patients/" + healthId));

                List<Reference> performer = new ArrayList<>();
                Reference reference = new Reference();
                reference.setReference("http://{shared-health-url}/providers/" + adapterData.getProviderId());
                performer.add(reference);
                observation.setPerformer(performer);

                CodeableConcept codeableConcept1 = new CodeableConcept();
                List<Coding> typeCoding1 = new ArrayList<>();
                Coding coding2 = new Coding();
                coding2.setSystem("http://localhost:9997/openmrs/ws/rest/v1/tr/concepts/" + obs.getFieldCode());
                coding2.setCode(obs.getFieldCode());
                if (!obs.getHumanReadableValues().isEmpty()) coding2.setDisplay(obs.getHumanReadableValues().get(0));
                typeCoding1.add(coding2);
                codeableConcept1.setCoding(typeCoding1);
                observation.setCode(codeableConcept1);

                entry1.setResource(observation);
                entries.add(entry1);

            }

            else if (obs.getFieldCode().equals("weight") | obs.getFieldCode().equals("height") | obs.getFieldCode().equals("bmi") | obs.getFieldCode().equals("body_temperature") | obs.getFieldCode().equals("pulse_rate") | obs.getFieldCode().equals("blood_pressure_systolic") | obs.getFieldCode().equals("blood_pressure_diastolic") | obs.getFieldCode().equals("uterus_length") | obs.getFieldCode().equals("hemoglobin_test") | obs.getFieldCode().equals("albumin_test") | obs.getFieldCode().equals("bilirubin_test") ) {


                Bundle.BundleEntryComponent entry1 = new Bundle.BundleEntryComponent();
                UUID uuid = UUID.randomUUID();
                entry1.setId(uuid.toString());

                Observation observation = new Observation();

                List<Identifier> identifierList = new ArrayList<>();
                Identifier identifier1= new Identifier();
                identifier1.setValue(uuid.toString());
                identifierList.add(identifier1);
                observation.setIdentifier(identifierList);

                observation.setSubject(new Reference("http://{shared-health-url}/patients/" + healthId));

                CodeableConcept codeableConcept1 = new CodeableConcept();
                List<Coding> typeCoding1 = new ArrayList<>();
                Coding coding2 = new Coding();
                coding2.setSystem("http://localhost:9997/openmrs/ws/rest/v1/tr/concepts/" + obs.getFieldCode());
                coding2.setCode(obs.getFieldCode());
                if(!obs.getHumanReadableValues().isEmpty()) coding2.setDisplay(obs.getHumanReadableValues().get(0));
                typeCoding1.add(coding2);
                codeableConcept1.setCoding(typeCoding1);
                observation.setCode(codeableConcept1);

                if(!obs.getValues().isEmpty()) {
                    if(obs.getValues().get(0).equals("No")) {
                        continue;
                    }
                    Quantity valueQuantity = new Quantity();
                    valueQuantity.setValue(Double.parseDouble(obs.getValues().get(0)));
                    valueQuantity.setSystem(obs.getFieldCode());
                    observation.setValue(valueQuantity);
                }

                entry1.setResource(observation);
                entries.add(entry1);
            }

            else if (obs.getFieldCode().equals("number_of_iron_medication") | obs.getFieldCode().equals("number_of_calcium_medication")) {


                Bundle.BundleEntryComponent entry1 = new Bundle.BundleEntryComponent();
                UUID uuid = UUID.randomUUID();
                entry1.setId(uuid.toString());

                Medication medication = new Medication();

                CodeableConcept codeableConcept1 = new CodeableConcept();
                List<Coding> typeCoding1 = new ArrayList<>();
                Coding coding2 = new Coding();
                coding2.setSystem("http://localhost:9997/openmrs/ws/rest/v1/tr/concepts/" + obs.getFieldCode());
                coding2.setCode(obs.getFieldCode());
                if(!obs.getHumanReadableValues().isEmpty()) coding2.setDisplay(obs.getHumanReadableValues().get(0));
                typeCoding1.add(coding2);
                codeableConcept1.setCoding(typeCoding1);
                medication.setCode(codeableConcept1);

                if(!obs.getValues().isEmpty()) {
                    Medication.MedicationPackageComponent packageComponent = new Medication.MedicationPackageComponent();
                    List<Medication.MedicationPackageContentComponent> contentComponents = new ArrayList<>();
                    Medication.MedicationPackageContentComponent contentComponent = new Medication.MedicationPackageContentComponent();
                    SimpleQuantity amount = new SimpleQuantity();
                    amount.setValue(Integer.parseInt(obs.getValues().get(0)));
                    contentComponent.setAmount(amount);
                    contentComponents.add(contentComponent);
                    packageComponent.setContent(contentComponents);
                    medication.setPackage(packageComponent);
                }


                entry1.setResource(medication);
                entries.add(entry1);
            }

            else if (obs.getFieldCode().equals("vaccination_tt_dose_completed")) {


                Bundle.BundleEntryComponent entry1 = new Bundle.BundleEntryComponent();
                UUID uuid = UUID.randomUUID();
                entry.setId(uuid.toString());

                Immunization immunization = new Immunization();

                List<Identifier> identifierList = new ArrayList<>();
                Identifier identifier1 = new Identifier();
                identifier1.setValue(uuid.toString());
                identifierList.add(identifier1);
                immunization.setIdentifier(identifierList);

                if(!obs.getValues().isEmpty()) immunization.setNotGiven(Boolean.parseBoolean(obs.getValues().get(0)));
                immunization.setPatient(new Reference("http://{shared-health-url}/patients/" + healthId));

                immunization.setDate(adapterData.getDateCreated());

                entry1.setResource(immunization);
                entries.add(entry1);

            }

            else if (obs.getFieldCode().equals("edd")) {


                Bundle.BundleEntryComponent entry1 = new Bundle.BundleEntryComponent();
                UUID uuid = UUID.randomUUID();
                entry.setId(uuid.toString());

                Observation observation = new Observation();

                List<Identifier> identifierList = new ArrayList<>();
                Identifier identifier1 = new Identifier();
                identifier1.setValue(uuid.toString());
                identifierList.add(identifier1);
                observation.setIdentifier(identifierList);

                observation.setStatus(Observation.ObservationStatus.FINAL);

                CodeableConcept codeableConcept1 = new CodeableConcept();
                List<Coding> typeCoding1 = new ArrayList<>();
                Coding coding2 = new Coding();
                coding2.setSystem("http://loinc.org");
                coding2.setCode("11778-8");
                coding2.setDisplay("Estimated delivery date");
                typeCoding1.add(coding2);
                codeableConcept1.setCoding(typeCoding1);
                observation.setCode(codeableConcept1);

                DateTimeType value = new DateTimeType();
                SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
                value.setValue(sf.parse(obs.getValues().get(0)));
                observation.setValue(value);

                entry1.setResource(observation);
                entries.add(entry1);

            }

        }

        bundle.setEntry(entries);

        FhirContext ctx = FhirContext.forDstu3();
        String json = ctx.newJsonParser().encodeResourceToString(bundle);
        Content content = new Content(json);

        ObjectMapper objectMapper  = new ObjectMapper();
        try {
            String json1 = objectMapper.writeValueAsString(content);

            return json1;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "Error";

    }
}
