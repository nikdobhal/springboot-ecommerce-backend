package com.ecommerce.project.serviice;

import com.ecommerce.project.model.User;
import com.ecommerce.project.payLoad.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressesById(Long addressId);

    List<AddressDTO> getUserAddresses(User user);

    String deleteAddress(Long addressId);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);
}
