package org.jk.weixin.service;

import org.jk.weixin.mapper.WxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
public class WxServiceImpl implements WxService{

    @Autowired
    private WxMapper wxMapper;
}
