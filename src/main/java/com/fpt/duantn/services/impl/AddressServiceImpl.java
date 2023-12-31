package com.fpt.duantn.services.impl;

import com.fpt.duantn.exceptions.AddressServiceException;
import com.fpt.duantn.exceptions.ColorServiceException;
import com.fpt.duantn.io.entity.AddressEntity;
import com.fpt.duantn.io.repository.AddressRepository;
import com.fpt.duantn.services.AddressService;
import com.fpt.duantn.shrared.Utils;
import com.fpt.duantn.shrared.dto.CRUD.AddressDto;
import com.fpt.duantn.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    Utils utils;

    @Override
    public AddressDto createAddress(AddressDto address) {
        // Kiểm tra xem AddressCode đã tồn tại hay chưa
        if (addressRepository.findByAddressCode(address.getAddressCode()) != null) {
            throw new AddressServiceException("Address with the same code already exists");
        }

        // Chuyển đổi AddressDto thành AddressEntity
        ModelMapper modelMapper = new ModelMapper();
        AddressEntity addressEntity = modelMapper.map(address, AddressEntity.class);

        // Tạo một mã ngẫu nhiên cho AddressCode (tùy theo yêu cầu)
        String publicAddressCode = utils.generateColorCode(8);
        addressEntity.setAddressCode(publicAddressCode);

        //them khoa ngoai
        addressEntity.setCustomer(address.getCustomer());

        // Lưu trữ thông tin dia chi vào cơ sở dữ liệu
        AddressEntity storedAddressDetails = addressRepository.save(addressEntity);

        // Chuyển đổi AddressEntity thành AddressDto
        AddressDto returnValue = modelMapper.map(storedAddressDetails, AddressDto.class);

        return returnValue;
    }

    @Override
    public AddressDto getAddressById(Long addressId) {
        AddressDto returnValue = new AddressDto();
        AddressEntity AddressEntity = addressRepository.findAddressEntityById(addressId);

        if (AddressEntity == null)
            throw new AddressServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        BeanUtils.copyProperties(AddressEntity, returnValue);

        return returnValue;
    }

    @Override
    public AddressDto updateAddress(Long addressId, AddressDto address) {
        AddressDto returnValue = new AddressDto();

        AddressEntity addressEntity = addressRepository.findAddressEntityById(addressId);

        if (addressEntity == null)
            throw new AddressServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        addressEntity.setAddressDetail(address.getAddressDetail());
        addressEntity.setCity(address.getCity());
        addressEntity.setDistrict(address.getDistrict());
        addressEntity.setWard(address.getWard());
        addressEntity.setCreateDate(address.getCreateDate());
        addressEntity.setUpdateDate(address.getUpdateDate());
        addressEntity.setCustomer(address.getCustomer());

        AddressEntity updatedAddresss = addressRepository.save(addressEntity);
        returnValue = new ModelMapper().map(updatedAddresss, AddressDto.class);

        return returnValue;
    }

    @Override
    public void deleteAddress(Long addressId) {
        AddressEntity addressEntity = addressRepository.findAddressEntityById(addressId);

        if (addressEntity == null)
            throw new ColorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        addressRepository.delete(addressEntity);
    }

    @Override
    public List<AddressDto> getAddresss(int page, int limit, String filter) {
        List<AddressDto> returnValue = new ArrayList<>();

        if(page>0) page = page-1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<AddressEntity> addressPage = addressRepository.filter(filter, pageableRequest);
        List<AddressEntity> addresss = addressPage.getContent();

        for (AddressEntity addressEntity : addresss) {
            AddressDto addressDto = new AddressDto();
            BeanUtils.copyProperties(addressEntity, addressDto);
            returnValue.add(addressDto);
        }

        return returnValue;
    }

    @Override
    public Long count(String filter) {

        Long total = addressRepository.count(filter);
        return total;
    }

}
