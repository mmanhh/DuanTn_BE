package com.fpt.duantn.ui.model.response;

import com.fpt.duantn.io.entity.EmployeeEntity;
import com.fpt.duantn.io.entity.ProductTypeEntity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CartRest {

    private Long id;

    private String cartCode;

    private EmployeeEntity employee;

    private Date createDate;

    private Date updateDate;

    private Integer status;

}
