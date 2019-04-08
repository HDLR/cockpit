/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fc.leading.cockpit.entity.UserEntity;
import fc.leading.cockpit.entity.UserEntity;
import fc.leading.cockpit.form.LoginForm;

import java.util.Map;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回登录信息
	 */
	Map<String, Object> login(LoginForm form);
}
