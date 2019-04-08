/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fc.leading.cockpit.modules.sys.entity.SysRoleEntity;
import fc.leading.cockpit.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	
    List<SysRoleEntity> queryAllRole();
}
