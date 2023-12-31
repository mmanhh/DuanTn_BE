package com.fpt.duantn.ui.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CategoryRest {

    private Long id;

    private String categoryName;
    private String categoryCode;
    private Integer status;
    private Date createDate;
    private Date updateDate;

}
