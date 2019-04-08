/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fc.leading.cockpit.modules.sys.entity.SysDictEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysDictDao extends BaseMapper<SysDictEntity> {
	
}
