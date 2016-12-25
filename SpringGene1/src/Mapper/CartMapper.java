package Mapper;

import po.Cart;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CartMapper extends Mapper<Cart>,MySqlMapper<Cart>{
    /* int deleteByPrimaryKey(CartKey key); */

    int insert(Cart record);

    int insertSelective(Cart record);

    /* Cart selectByPrimaryKey(CartKey key); */

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
    
    public Cart selectbyUser(Cart cart);
}