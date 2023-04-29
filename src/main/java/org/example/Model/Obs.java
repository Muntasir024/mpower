package org.example.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class Obs {

    private List<String> values;
    private String fieldCode;
    private String fieldType;
    private String parentCode;
    private String fieldDataType;
    private String formSubmissionField;
    private List<String> humanReadableValues;

}
