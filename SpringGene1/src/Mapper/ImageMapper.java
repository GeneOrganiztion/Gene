package Mapper;

import po.Image;

public interface ImageMapper {
    /* int deleteByPrimaryKey(ImageKey key); */

    int insert(Image record);

    int insertSelective(Image record);

    /* Image selectByPrimaryKey(ImageKey key); */

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);
}