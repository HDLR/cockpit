package fc.leading.cockpit.modules.act.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fc.leading.cockpit.modules.act.exception.MyException;
import fc.leading.cockpit.modules.act.entity.ActModel;
import fc.leading.cockpit.modules.act.exception.MyException;
import fc.leading.cockpit.modules.act.util.JsonUtil;
import fc.leading.cockpit.modules.act.util.ModelDataJsonConstants;
import fc.leading.cockpit.modules.act.util.ReType;
import fc.leading.cockpit.modules.sys.entity.SysRoleEntity;
import fc.leading.cockpit.modules.sys.entity.SysUserEntity;
import fc.leading.cockpit.modules.sys.entity.SysUserRoleEntity;
import fc.leading.cockpit.modules.sys.service.SysRoleService;
import fc.leading.cockpit.modules.sys.service.SysUserRoleService;
import fc.leading.cockpit.modules.sys.service.SysUserService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import fc.leading.cockpit.modules.act.entity.ProcessDefinition;

@Controller
@RequestMapping(value = "/act")
public class ActController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 模型列表
     */
    @GetMapping(value = "showAm")
    @ResponseBody
    public ReType showModel(org.springframework.ui.Model model, ActModel actModel, String page, String limit) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (actModel != null) {
            if (!StringUtils.isEmpty(actModel.getKey())) {
                modelQuery.modelKey(actModel.getKey());
            }
            if (!StringUtils.isEmpty(actModel.getName())) {
                modelQuery.modelNameLike("%" + actModel.getName() + "%");
            }
        }
        List<Model> models = modelQuery
                .listPage(Integer.valueOf(limit) * (Integer.valueOf(page) - 1), Integer.valueOf(limit));
        long count = repositoryService.createModelQuery().count();
        List<ActModel> list = new ArrayList<>();
        models.forEach(mo -> list.add(new ActModel(mo)));
        return new ReType(count, list);
    }

    //新建流程
    @GetMapping(value = "goActiviti")
    public String goActiviti() throws UnsupportedEncodingException {
        Model model = repositoryService.newModel();

        String name = "新建流程";
        String description = "";
        int revision = 1;
        String key = "processKey";

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        return "redirect:/statics/act/static/modeler.html?modelId=" + id;
    }

    //修改流程
    @GetMapping("actUpdate/{id}")
    public String actUpdate(@PathVariable String id) {
        return "redirect:/statics/act/static/modeler.html?modelId=" + id;
    }

    /**
     * 发布流程
     */
    @PostMapping(value = "open")
    @ResponseBody
    public JsonUtil open(String id) {
        String msg = "发布成功";
        JsonUtil j = new JsonUtil();
        try {
            Model modelData = repositoryService.getModel(id);
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

            if (bytes == null) {
                return JsonUtil.error("模型为空");
            }
            JsonNode modelNode = null;
            modelNode = new ObjectMapper().readTree(bytes);
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            if (model.getProcesses().size() == 0) {
                return JsonUtil.error("数据不符合要求");
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            //发布流程
            String processName = modelData.getName() + ".bpmn20.xml";
            String convertToXML = new String(bpmnBytes);

            System.out.println(convertToXML);
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName, new String(bpmnBytes, "UTF-8"))
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
        } catch (MyException e) {
            msg = "发布数失败";
            j.setFlag(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        j.setMsg(msg);
        return j;
    }

    //删除模型
    @PostMapping("delModel")
    @ResponseBody
    public JsonUtil delModel(org.springframework.ui.Model model, String id) {
        FileInputStream inputStream = null;
        JsonUtil j = new JsonUtil();
        try {
            repositoryService.deleteModel(id);
            j.setMsg("删除成功");
        } catch (MyException e) {
            j.setMsg("删除失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 同步用户 角色 用户角色到activiti表中
     */
    @PostMapping(value = "syncdata")
    @ResponseBody
    public JsonUtil syncdata() {
        JsonUtil j = new JsonUtil();
        try {
            List<SysUserEntity> userList = sysUserService.queryAllUser();
            User au = null;
            for (SysUserEntity user : userList) {
                au = identityService.newUser("" + user.getUserId());
                au.setId("" + user.getUserId());
                au.setFirstName(user.getUsername());
                au.setEmail(user.getEmail());
                identityService.deleteUser(au.getId());
                identityService.saveUser(au);
            }

            List<SysRoleEntity> sysRoleList = sysRoleService.queryAllRole();
            Group group = null;
            for (SysRoleEntity role : sysRoleList) {
                group = identityService.newGroup("" + role.getRoleId());
                group.setId("" + role.getRoleId());
                group.setName(role.getRoleName());
                identityService.deleteGroup(group.getId());
                identityService.saveGroup(group);
            }

            List<SysUserRoleEntity> roleUserList = sysUserRoleService.queryAllUserRole();
            for (SysUserRoleEntity sysRoleUser : roleUserList) {
                identityService.deleteMembership("" + sysRoleUser.getUserId(), "" + sysRoleUser.getRoleId());
                identityService.createMembership("" + sysRoleUser.getUserId(), "" + sysRoleUser.getRoleId());
            }
            j.setMsg("同步成功");
        } catch (MyException e) {
            j.setFlag(false);
            j.setMsg("同步失败");
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 部署列表
     */
    @GetMapping(value = "showAct")
    @ResponseBody
    public ReType showAct(org.springframework.ui.Model model, ProcessDefinition definition,
                          String page, String limit) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService
                .createProcessDefinitionQuery();
        List<org.activiti.engine.repository.ProcessDefinition> processDefinitionList = null;
        if (definition != null) {
            if (!StringUtils.isEmpty(definition.getDeploymentId())) {
                processDefinitionQuery.deploymentId(definition.getDeploymentId());
            }
            if (!StringUtils.isEmpty(definition.getName())) {
                processDefinitionQuery.processDefinitionNameLike("%" + definition.getName() + "%");

            }
        }
        processDefinitionList = processDefinitionQuery.listPage(Integer.valueOf(limit) * (Integer.valueOf(page) - 1), Integer.valueOf(limit));
        long count = repositoryService.createDeploymentQuery().count();
        List<ProcessDefinition> list = new ArrayList<>();
        processDefinitionList
                .forEach(processDefinition -> list.add(new ProcessDefinition(processDefinition)));
        return new ReType(count, list);
    }
}
