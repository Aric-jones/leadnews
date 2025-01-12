package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.exception.CustomException;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import com.heima.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Transactional
@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {
    /**
     * app登录
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        // 如果是游客，直接返回Token
        if(StringUtils.isBlank(dto.getPhone()) && StringUtils.isBlank(dto.getPassword())){
            String token = AppJwtUtil.getToken(0L);
            Map<String ,Object> map = new HashMap<>();
            map.put("token",token);
            return ResponseResult.okResult(map);
        }

        // 如果是用户，进行下面操作

        // 判断用户是否存在
        ApUser user = getOne(new LambdaQueryWrapper<ApUser>().eq(ApUser::getPhone, dto.getPhone()));
        if(Objects.isNull(user)){
            throw new CustomException(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }

        // 判断密码是否正确
        String salt = user.getSalt();
        String userPassword = user.getPassword();
        String password = DigestUtils.md5DigestAsHex((dto.getPassword()+salt).getBytes());
        if(!userPassword.equals(password)){
            throw new CustomException(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        // 返回Token
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppJwtUtil.getToken(user.getId().longValue()));
        map.put("user", user);
        return ResponseResult.okResult(map);
    }
}
