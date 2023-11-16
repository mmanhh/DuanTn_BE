package com.fpt.duantn.ui.controller;

import com.fpt.duantn.services.ProductDetailService;
import com.fpt.duantn.shrared.dto.CRUD.ProductDetailDto;
import com.fpt.duantn.ui.model.request.ProductDetailRequest;
import com.fpt.duantn.ui.model.response.PaginationRest;
import com.fpt.duantn.ui.model.response.ProductDetailRest;
import com.fpt.duantn.ui.model.response.OperationStatusModel;
import com.fpt.duantn.ui.model.response.RequestOperationStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product-detail")
public class ProductDetailController {

    @Autowired
    ProductDetailService productDetailService;

    @GetMapping(path = "/{id}")
    public ProductDetailRest getProductDetail(@PathVariable Long id) {
        ProductDetailRest returnValue = new ProductDetailRest();

        ProductDetailDto productDetailDto = productDetailService.getProductDetailByProductDetailCode(id);
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(productDetailDto, ProductDetailRest.class);

        return returnValue;
    }

    @PostMapping()
    public ProductDetailRest createProductDetail(@RequestBody ProductDetailRequest productDetailDetails) throws Exception {
        ProductDetailRest returnValue = new ProductDetailRest();

        ModelMapper modelMapper = new ModelMapper();
        ProductDetailDto productDetailDto = modelMapper.map(productDetailDetails, ProductDetailDto.class);

        productDetailDto.setProduct(productDetailDetails.getProduct());
        productDetailDto.setColor(productDetailDetails.getColor());
        productDetailDto.setSize(productDetailDetails.getSize());

        ProductDetailDto createdUser = productDetailService.createProductDetail(productDetailDto);
        returnValue = modelMapper.map(createdUser, ProductDetailRest.class);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public ProductDetailRest updateProductDetail(@PathVariable Long id, @RequestBody ProductDetailRequest productDetailDetails) {
        ProductDetailRest returnValue = new ProductDetailRest();

        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto = new ModelMapper().map(productDetailDetails, ProductDetailDto.class);

        productDetailDto.setProduct(productDetailDetails.getProduct());
        productDetailDto.setColor(productDetailDetails.getColor());
        productDetailDto.setSize(productDetailDetails.getSize());

        ProductDetailDto updateProductDetail = productDetailService.updateProductDetail(id, productDetailDto);
        returnValue = new ModelMapper().map(updateProductDetail, ProductDetailRest.class);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteProductDetail(@PathVariable Long id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        try {
            productDetailService.deleteProductDetail(id);
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
            returnValue.setOperationMessage("Xoa Thanh Cong.");
        }catch (DataIntegrityViolationException exception){
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
            returnValue.setOperationMessage("Lỗi khi xóa sản phẩm: Sản phẩm có tham chiếu đến khoá ngoại.");
        }catch (Exception e){
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
            returnValue.setOperationMessage("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }return returnValue;
    }



//    @GetMapping("/search")
//    public List<ProductDetailRest> searchProductDetails(@RequestParam(value = "productDetailCode") String productDetailCode,
//                                              @RequestParam(value = "page", defaultValue = "0") int page,
//                                              @RequestParam(value = "limit", defaultValue = "2") int limit) {
//        List<ProductDetailRest> returnValue = new ArrayList<>();
//
//        List<ProductDetailDto> productDetails = productDetailService.getProductByProductDetailCode(productDetailCode, page, limit);
//
//        for (ProductDetailDto productDetailDto : productDetails) {
//            ProductDetailRest productDetailModel = new ProductDetailRest();
//            BeanUtils.copyProperties(productDetailDto, productDetailModel);
//            returnValue.add(productDetailModel);
//        }
//
//        return returnValue;
//    }

    @GetMapping()
    public PaginationRest getProductDetails(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "limit", defaultValue = "5") int limit,
                                            @RequestParam(value = "filter", defaultValue = "") String filter) {
        List<ProductDetailRest> returnValue = new ArrayList<>();

        List<ProductDetailDto> productDetails = productDetailService.getProductsDetail(page, limit, filter);

        for (ProductDetailDto productDetailDto : productDetails) {
            ProductDetailRest productDetailModel = new ProductDetailRest();
            BeanUtils.copyProperties(productDetailDto, productDetailModel);
            returnValue.add(productDetailModel);
        }
        PaginationRest paginationRest = new PaginationRest();
        paginationRest.setListProductDetail(returnValue);
        paginationRest.setTotal(productDetailService.count(filter));

        return paginationRest;
    }

    @GetMapping("/{productId}/{colorId}/{sizeId}")
    public ResponseEntity<ProductDetailDto> getProductDetailByColorAndSize(
            @PathVariable Long productId, @PathVariable Long colorId, @PathVariable Long sizeId) {
        ProductDetailDto productDetailDto = productDetailService.getProductDetailsByProductAndColorAndSize(productId,colorId, sizeId);
        if (productDetailDto != null) {
            return new ResponseEntity<>(productDetailDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
