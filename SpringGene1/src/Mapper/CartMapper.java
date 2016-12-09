package Mapper;

import po.Cart;

public interface CartMapper {
    /* int deleteByPrimaryKey(CartKey key); */

    int insert(Cart record);

    int insertSelective(Cart record);

    /* Cart selectByPrimaryKey(CartKey key); */

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
    
    public Cart selectbyUser(Cart cart);
}