package com.example.core;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.RepresentationModel;

import com.example.entity.OperationEntity;
import com.example.model.OperationModel;
import com.example.assembler.OperationModelAssembler;
import com.example.connector.QueueConnector;

@RestController
@Slf4j
public class OperationController {

    @Autowired
    private OperationModelAssembler operationModelAssembler;

    @Autowired
    //@Qualifier("kakfa-spring-connector")
    @Qualifier("kakfa-connector")
    private QueueConnector queueConnector;

    @Value("${app.topics.operations}")
    private String inputTopic;
    
    @GetMapping("operations")
    public ResponseEntity<RepresentationModel> getOperations() {
        return ResponseEntity.accepted().build();
    }

    @GetMapping("operations/{id}")
    public ResponseEntity<RepresentationModel> getOperationById(@PathVariable("id") Long id) {
        return ResponseEntity.accepted().build();
    }
    
    @PostMapping("/operations")
    public ResponseEntity<OperationModel> newOperation(@RequestBody OperationEntity operation) {
        log.info("Received operation: " + operation.toString());

        writeOperation(operation);

        return new ResponseEntity<>(operationModelAssembler.toModel(operation), HttpStatus.CREATED);
    }

    private void writeOperation(OperationEntity operation) {
        this.queueConnector.sendMessage(inputTopic, operation.getId().toString(), operation);
    }
}