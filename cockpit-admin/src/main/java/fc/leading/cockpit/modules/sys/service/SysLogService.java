/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import fc.leading.cockpit.modules.sys.entity.SysLogEntity;
import fc.leading.cockpit.common.utils.PageUtils;
import fc.leading.cockpit.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
