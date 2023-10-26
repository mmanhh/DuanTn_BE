package com.fpt.duantn.ui.model.request;

import com.fpt.duantn.io.entity.ProductTypeEntity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PromotionDetailsRequestModel {

    private Integer amount;

    private BigDecimal discountLevel;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    private Integer status;

}
