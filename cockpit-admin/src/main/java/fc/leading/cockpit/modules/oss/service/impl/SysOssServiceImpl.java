/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fc.leading.cockpit.modules.oss.dao.SysOssDao;
import fc.leading.cockpit.modules.oss.service.SysOssService;
import fc.leading.cockpit.common.utils.PageUtils;
import fc.leading.cockpit.common.utils.Query;
import fc.leading.cockpit.modules.oss.dao.SysOssDao;
import fc.leading.cockpit.modules.oss.entity.SysOssEntity;
import fc.leading.cockpit.modules.oss.service.SysOssService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SysOssEntity> page = this.page(
			new Query<SysOssEntity>().getPage(params)
		);

		return new PageUtils(page);
	}
	
}
