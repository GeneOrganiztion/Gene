package Mapper;

import po.Address;
import po.AddressKey;

public interface AddressMapper {
    int deleteByPrimaryKey(AddressKey key);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(AddressKey key);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);
}