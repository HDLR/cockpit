package fc.leading.cockpit.modules.act.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fc.leading.cockpit.modules.sys.entity.SysRoleEntity;
import fc.leading.cockpit.modules.sys.entity.SysUserRoleEntity;
import fc.leading.cockpit.modules.sys.service.SysRoleService;
import fc.leading.cockpit.modules.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statics/act")
public class ActAssignController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @RequestMapping("/role/showaLLRoleList")
    public String showaLLRoleList(){
        List<SysRoleEntity> list = sysRoleService.queryAllRole();
        JSONArray jsons = new JSONArray();
        for(SysRoleEntity a : list){
            JSONObject jo = new JSONObject();
            jo.put("id", a.getRoleId());
            jo.put("name", a.getRoleName());
            jo.put("remark", a.getRemark());
            jsons.add(jo);
        }
        String roleStr = jsons.toJSONString();
        return roleStr;
    }

    @GetMapping("/user/listByRoleId")
    public String showUser(Model model, String roleId, int page, int limit) {
        JSONObject returnValue = new JSONObject();
        List<SysUserRoleEntity> list = sysUserRoleService.queryUserByRoleId(roleId);
        returnValue.put("users", list);
        returnValue.put("totals", list.size());
        return JSON.toJSONString(returnValue);
    }
}
