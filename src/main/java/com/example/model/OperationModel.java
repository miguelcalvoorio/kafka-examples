package com.example.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation("operations")
@JsonRootName("operation")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationModel extends RepresentationModel<OperationModel>{
    private Long id;
    private String description;
    private String status;
    private HashMap<String, Object> operationData;
}
