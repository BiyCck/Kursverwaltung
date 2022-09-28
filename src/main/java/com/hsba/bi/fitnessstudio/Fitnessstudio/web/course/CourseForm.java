package com.hsba.bi.fitnessstudio.Fitnessstudio.web.course;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CourseForm {

    @NotBlank(message = "Geben Sie einen Namen ein")
    private String name;

    @NotEmpty(message = "Geben Sie eine Beschreibung ein")
    private String description;

    @NotBlank(message = "Geben Sie eine Zielgruppe ein")
    private String targetGroup;

    @NotBlank(message = "Geben Sie eine Kategorie ein")
    private String category;

}
