/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fc.leading.cockpit.common.utils.PageUtils;
import fc.leading.cockpit.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
