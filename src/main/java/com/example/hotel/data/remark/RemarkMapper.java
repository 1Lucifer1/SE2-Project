package com.example.hotel.data.remark;
/**
 * @Author: LuZhangchi
 * @Date: 2020-06-07
 */
import com.example.hotel.po.Remark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RemarkMapper {
    Remark getRemarkById(@Param("id") Integer id);

    int insertRemark(Remark remark);

    List<Remark> getRemarkByHotelId(@Param("hotelId") Integer hotelId);

    List<Remark> getRemarkByUserId(@Param("hotelId") Integer userId);

    int createNewRemark(Remark remark);
}
