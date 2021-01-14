package com.example.entity;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "operationData")
public class OperationEntity {
    private Long id;
    private String description;
    private String status;
    private HashMap<String, Object> operationData;
}