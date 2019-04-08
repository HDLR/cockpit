/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fc.leading.cockpit.entity.TokenEntity;
import fc.leading.cockpit.entity.UserEntity;
import fc.leading.cockpit.service.TokenService;
import fc.leading.cockpit.service.UserService;
import fc.leading.cockpit.common.exception.RRException;
import fc.leading.cockpit.common.validator.Assert;
import fc.leading.cockpit.dao.UserDao;
import fc.leading.cockpit.entity.TokenEntity;
import fc.leading.cockpit.entity.UserEntity;
import fc.leading.cockpit.form.LoginForm;
import fc.leading.cockpit.service.TokenService;
import fc.leading.cockpit.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private TokenService tokenService;

	@Override
	public UserEntity queryByMobile(String mobile) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
	}

	@Override
	public Map<String, Object> login(LoginForm form) {
		UserEntity user = queryByMobile(form.getMobile());
		Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("手机号或密码错误");
		}

		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getUserId());

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenEntity.getToken());
		map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

		return map;
	}

}
