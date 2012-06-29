// -*- Java -*-
/*!
 * @file MobileRobot.java
 * @date $Date$
 *
 * $Id$
 */

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.RegisterModuleFunc;
import jp.go.aist.rtm.RTC.util.Properties;

/*!
 * @class MobileRobot
 * @brief Mobile Vehicle Robot RTC
 */
public class MobileRobot implements RtcNewFunc, RtcDeleteFunc, RegisterModuleFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
    	    "implementation_id", "MobileRobot",
    	    "type_name",         "MobileRobot",
    	    "description",       "Mobile Vehicle Robot RTC",
    	    "version",           "1.0.0",
    	    "vendor",            "ysuga_net",
    	    "category",          "Example",
    	    "activity_type",     "STATIC",
    	    "max_instance",      "1",
    	    "language",          "Java",
    	    "lang_type",         "compile",
            // Configuration variables
            //"conf.default.debug", "0",
            "conf.default.color", "red",
            // Widget
            "conf.__widget__.debug", "text",
            // Constraints
            "conf.__constraints__.debug", "conf_constraint",
    	    ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new MobileRobotImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
    public void registerModule() {
        Properties prop = new Properties(component_conf);
        final Manager manager = Manager.instance();
        manager.registerFactory(prop, new MobileRobot(), new MobileRobot());
    }
}
