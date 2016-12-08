package Mapper;

import po.Classify;

public interface ClassifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Classify record);

    int insertSelective(Classify record);

    Classify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classify record);

    int updateByPrimaryKey(Classify record);
}