package com.fpt.duantn.ui.model.request;

import com.fpt.duantn.io.entity.CollarEntity;
import com.fpt.duantn.io.entity.ColorEntity;
import com.fpt.duantn.io.entity.DesignEntity;
import com.fpt.duantn.io.entity.MaterialEntity;
import com.fpt.duantn.io.entity.PatternEntity;
import com.fpt.duantn.io.entity.ProductEntity;
import com.fpt.duantn.io.entity.ProductTypeEntity;
import com.fpt.duantn.io.entity.SizeEntity;
import com.fpt.duantn.io.entity.SleeveEntity;
import com.fpt.duantn.io.entity.WaistbandEntity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductDetailDetailsRequestModel {

    private ProductEntity product;

    private ColorEntity color;

    private SizeEntity size;

    private DesignEntity design;

    private MaterialEntity material;

    private PatternEntity pattern;

    private CollarEntity collar;

    private SleeveEntity sleeve;

    private WaistbandEntity waistband;

    private BigDecimal defaultPrice;

    private BigDecimal price;

    private Integer amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    private Integer status;

}
