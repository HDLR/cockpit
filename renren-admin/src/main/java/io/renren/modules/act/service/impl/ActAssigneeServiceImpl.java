package io.renren.modules.act.service.impl;

import io.renren.modules.act.dao.ActAssigneeDao;
import io.renren.modules.act.entity.ActAssigneeEntity;
import io.renren.modules.act.service.ActAssigneeService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;


@Service("actAssigneeService")
public class ActAssigneeServiceImpl extends ServiceImpl<ActAssigneeDao, ActAssigneeEntity> implements ActAssigneeService {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActAssigneeEntity> page = this.page(
                new Query<ActAssigneeEntity>().getPage(params),
                new QueryWrapper<ActAssigneeEntity>()
        );

        return new PageUtils(page);
    }


}
