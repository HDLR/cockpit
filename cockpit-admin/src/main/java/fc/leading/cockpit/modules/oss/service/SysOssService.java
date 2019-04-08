/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fc.leading.cockpit.common.utils.PageUtils;
import fc.leading.cockpit.modules.oss.entity.SysOssEntity;

import java.util.Map;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
