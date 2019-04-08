/**
 * cockpit
 *
 *
 *
 * 版权所有，侵权必究！
 */

package fc.leading.cockpit.common.utils;

/**
 * Redis所有Keys
 *
 * @author Mark sunlightcs@gmail.com
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    public static String getShiroSessionKey(String key){
        return "sessionid:" + key;
    }
}
