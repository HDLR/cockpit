/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fc.leading.cockpit.common.utils.PageUtils;
import fc.leading.cockpit.modules.sys.entity.SysDictEntity;

import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

