package com.example.assembler;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.core.OperationController;
import com.example.entity.OperationEntity;
import com.example.model.OperationModel;

@Component("operationModelAssembler")
public class OperationModelAssembler extends RepresentationModelAssemblerSupport<OperationEntity, OperationModel> {

    public OperationModelAssembler() {
        super(OperationController.class, OperationModel.class);
    }

    @Override
    public OperationModel toModel(OperationEntity entity) {
        OperationModel operationModel = instantiateModel(entity);

        operationModel.setId(entity.getId());
        operationModel.setDescription(entity.getDescription());
        operationModel.setStatus(entity.getStatus());
        operationModel.setOperationData(entity.getOperationData());
        operationModel.add(linkTo(methodOn(OperationController.class).getOperationById(entity.getId())).withSelfRel());

        return operationModel;
    }

    @Override
    public CollectionModel<OperationModel> toCollectionModel(Iterable<? extends OperationEntity> entities)
    {
        CollectionModel<OperationModel> operationModels = super.toCollectionModel(entities);
        operationModels.add(linkTo(methodOn(OperationController.class).getOperations()).withSelfRel());

        return operationModels;
    }
}
