/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import fc.leading.cockpit.modules.sys.entity.SysRoleEntity;
import fc.leading.cockpit.common.utils.PageUtils;
import fc.leading.cockpit.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(SysRoleEntity role);

	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);

	List<SysRoleEntity> queryAllRole();

}
