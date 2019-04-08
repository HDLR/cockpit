package fc.leading.cockpit.modules.act.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-16 00:43:31
 */
@Data
@TableName("act_assignee")
public class ActAssigneeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 节点id
	 */
	private String sid;
	/**
	 * 办理人
	 */
	private String assignee;
	/**
	 * 候选组(角色)
	 */
	private String roleId;
	/**
	 * 办理人类型1办理人2候选人3组
	 */
	private Integer assigneeType;
	/**
	 * 节点名称
	 */
	private String activtiName;

}
