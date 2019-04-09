/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
public class SysPageController {

	@RequestMapping(value = {"/", "sysIndex1.html"})
	public String index(){
		return "sysIndex1";
	}

	@RequestMapping("sysIndex.html")
	public String index1(){
		return "sysIndex";
	}

	@RequestMapping("sysLogin.html")
	public String login(){
		return "sysLogin";
	}

	@RequestMapping("sysMain.html")
	public String main(){
		return "sysMain";
	}

	@RequestMapping("404.html")
	public String notFound(){
		return "404";
	}

	@RequestMapping("{url}.html")
	public String module(@PathVariable("url") String url){
		return url;
	}
	
	@RequestMapping("modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return "modules/" + module + "/" + url;
	}

	@RequestMapping("modules/{module1}/{module2}/{url}.html")
	public String module2(@PathVariable("module1") String module1, @PathVariable("module2") String module2, @PathVariable("url") String url){
		return "modules/" + module1 + "/" + module2 + "/" + url;
	}
}
