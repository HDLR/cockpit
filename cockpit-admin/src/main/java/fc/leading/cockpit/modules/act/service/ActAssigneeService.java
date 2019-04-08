package fc.leading.cockpit.modules.act.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fc.leading.cockpit.modules.act.entity.ActAssigneeEntity;
import fc.leading.cockpit.common.utils.PageUtils;
import fc.leading.cockpit.modules.act.entity.ActAssigneeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-16 00:43:31
 */
public interface ActAssigneeService extends IService<ActAssigneeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

