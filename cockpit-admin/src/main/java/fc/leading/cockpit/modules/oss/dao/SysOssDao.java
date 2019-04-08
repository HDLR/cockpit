/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fc.leading.cockpit.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
	
}
