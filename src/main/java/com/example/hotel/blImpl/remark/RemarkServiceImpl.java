package com.example.hotel.blImpl.remark;

import com.example.hotel.bl.remark.RemarkService;
import com.example.hotel.data.remark.RemarkMapper;
import com.example.hotel.po.Remark;
import com.example.hotel.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LuZhangchi
 * @Date: 2020-06-07
 */
@Service
public class RemarkServiceImpl implements RemarkService {

    @Autowired
    private RemarkMapper remarkMapper;

    @Override
    public List<Remark> retrieveHotelRemark(Integer hotelId) {
        return remarkMapper.getRemarkByHotelId(hotelId);
    }

    @Override
    public List<Remark> retrieveUserRemark(Integer userId) {
        return remarkMapper.getRemarkByUserId(userId);
    }

    @Override
    public void addHotelRemark(Remark remark)  throws ServiceException {
        remarkMapper.insertRemark(remark);
    }

}
