// -*- Java -*-
/*!
 * @file  MobileRobotImpl.java
 * @brief Mobile Vehicle Robot RTC
 * @date  $Date$
 *
 * $Id$
 */

import java.awt.Color;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.RTC.util.StringHolder;
import RTC.ReturnCode_t;
import RTC.Time;
import RTC.TimedBooleanSeq;
import RTC.TimedDouble;
import RTC.TimedPose2D;
import RTC.TimedVelocity2D;

/*!
 * @class MobileRobotImpl
 * @brief Mobile Vehicle Robot RTC
 *
 */
public class MobileRobotImpl extends DataFlowComponentBase {

    
	/**
	 * @brief Simulator's Frame
	 */
    private VehicleSimWindow frame;
    
    /**
     * @brief Robot
     */
    private Vehicle vehicle;
    
    
  /*!
   * @brief constructor
   * @param manager Maneger Object
   */
	public MobileRobotImpl(Manager manager) {  
        super(manager);
        // <rtc-template block="initializer">
        m_vel_val = new TimedVelocity2D();
        m_vel = new DataRef<TimedVelocity2D>(m_vel_val);
        m_velIn = new InPort<TimedVelocity2D>("vel", m_vel);
        m_pos_val = new TimedPose2D();
        m_pos = new DataRef<TimedPose2D>(m_pos_val);
        m_posOut = new OutPort<TimedPose2D>("pos", m_pos);
        m_batteryVoltage_val = new TimedDouble();
        m_batteryVoltage = new DataRef<TimedDouble>(m_batteryVoltage_val);
        m_batteryOut = new OutPort<TimedDouble>("battery", m_batteryVoltage);
        m_bump_val = new TimedBooleanSeq();
        m_bump = new DataRef<TimedBooleanSeq>(m_bump_val);
        m_bumpOut = new OutPort<TimedBooleanSeq>("bumper", m_bump);
        // </rtc-template>

        
        frame = new VehicleSimWindow();
    	vehicle = frame.getVehicle();
    	vehicle.setPose(0, 0, 0);
    	vehicle.refreshPosition();
    	frame.repaint();
    }

    /*!
     *
     * The initialize action (on CREATED->ALIVE transition)
     * formaer rtc_init_entry() 
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onInitialize() {
        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        addInPort("vel", m_velIn);
        
        // Set OutPort buffer

        //addOutPort("battery", m_batteryOut);
        addOutPort("pos", m_posOut);
        addOutPort("bumper", m_bumpOut);
        // </rtc-template>
        bindParameter("debug", m_debug, "0");
        bindParameter("color", m_color, "red");
        return super.onInitialize();
    }

    /***
     *
     * The finalize action (on ALIVE->END transition)
     * formaer rtc_exiting_entry()
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onFinalize() {
//        return super.onFinalize();
//    }

    /***
     *
     * The startup action when ExecutionContext startup
     * former rtc_starting_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onStartup(int ec_id) {
//        return super.onStartup(ec_id);
//    }

    /***
     *
     * The shutdown action when ExecutionContext stop
     * former rtc_stopping_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onShutdown(int ec_id) {
//        return super.onShutdown(ec_id);
//    }

    /***
     *
     * The activated action (Active state entry action)
     * former rtc_active_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onActivated(int ec_id) {
    	vehicle.setPose(0, 0, 0);
    	frame.start();
        return super.onActivated(ec_id);
    }

    /***
     *
     * The deactivated action (Active state exit action)
     * former rtc_active_exit()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onDeactivated(int ec_id) {
    	frame.stop();
        return super.onDeactivated(ec_id);
    }

    /***
     *
     * The execution action that is invoked periodically
     * former rtc_active_do()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onExecute(int ec_id) {
    	if(m_color.value.equals("red")) {
    		vehicle.setColor(Color.red);
    	} else if(m_color.value.equals("green")) {
    		vehicle.setColor(Color.green);    		
    	} else if(m_color.value.equals("blue")) {
    		vehicle.setColor(Color.blue);
    	} else if(m_color.value.equals("yellow")) {
    		vehicle.setColor(Color.yellow);
    	} else if(m_color.value.equals("pink")) {
    		vehicle.setColor(Color.pink);
    	} else if(m_color.value.equals("orange")) {
    		vehicle.setColor(Color.orange);
    	} else if(m_color.value.equals("black")) {
    		vehicle.setColor(Color.black);
    	} else if(m_color.value.equals("gray")) {
    		vehicle.setColor(Color.gray);
    	} 
    	
    	if(m_velIn.isNew()) {
    		m_velIn.read();
    		vehicle.setVelocity(m_vel.v.data.vx, -m_vel.v.data.vy, -m_vel.v.data.va);
    	}
    	vehicle.refreshPosition();
    	
    	//m_batteryVoltage.v.tm = new RTC.Time();
    	//m_batteryVoltage.v.data = vehicle.getBatteryVoltage();
    	//m_batteryOut.write();
    	
    	m_pos.v.tm = new RTC.Time();
    	double th = -vehicle.getTh();
    	if(th < 0) { th += Math.PI * 2; }
    	m_pos.v.data = new RTC.Pose2D(new RTC.Point2D(vehicle.getX(), -vehicle.getY()), th);
    	m_posOut.write();
    	
    	m_bump.v.tm = new RTC.Time();
    	m_bump.v.data = new boolean[2];
    	m_bump.v.data[0] = vehicle.getRightBump();
    	m_bump.v.data[1] = vehicle.getLeftBump();
    	m_bumpOut.write();
    	
        return super.onExecute(ec_id);
    }

    /***
     *
     * The aborting action when main logic error occurred.
     * former rtc_aborting_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//  @Override
//  public ReturnCode_t onAborting(int ec_id) {
//      return super.onAborting(ec_id);
//  }

    /***
     *
     * The error action in ERROR state
     * former rtc_error_do()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    public ReturnCode_t onError(int ec_id) {
        return super.onError(ec_id);
    }

    /***
     *
     * The reset action that is invoked resetting
     * This is same but different the former rtc_init_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onReset(int ec_id) {
        return super.onReset(ec_id);
    }

    /***
     *
     * The state update action that is invoked after onExecute() action
     * no corresponding operation exists in OpenRTm-aist-0.2.0
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onStateUpdate(int ec_id) {
//        return super.onStateUpdate(ec_id);
//    }

    /***
     *
     * The action that is invoked when execution context's rate is changed
     * no corresponding operation exists in OpenRTm-aist-0.2.0
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onRateChanged(int ec_id) {
//        return super.onRateChanged(ec_id);
//    }
//
	// Configuration variable declaration
	// <rtc-template block="config_declare">
    /*!
     * 
     * - Name:  debug
     * - DefaultValue: 0
     */
    protected IntegerHolder m_debug = new IntegerHolder();
    
    protected StringHolder m_color = new StringHolder();
	// </rtc-template>

    // DataInPort declaration
    // <rtc-template block="inport_declare">
    protected TimedVelocity2D m_vel_val;
    protected DataRef<TimedVelocity2D> m_vel;
    /*!
     * velocity of robot
     */
    protected InPort<TimedVelocity2D> m_velIn;

    
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    protected TimedPose2D m_pos_val;
    protected DataRef<TimedPose2D> m_pos;
    /*!
     * position of robot
     */
    protected OutPort<TimedPose2D> m_posOut;
    protected TimedDouble m_batteryVoltage_val;
    protected DataRef<TimedDouble> m_batteryVoltage;
    /*!
     */
    protected OutPort<TimedDouble> m_batteryOut;

    protected TimedBooleanSeq m_bump_val;
    
    protected DataRef<TimedBooleanSeq> m_bump;
    protected OutPort<TimedBooleanSeq> m_bumpOut;
    
    // </rtc-template>

    // CORBA Port declaration
    // <rtc-template block="corbaport_declare">
    
    // </rtc-template>

    // Service declaration
    // <rtc-template block="service_declare">
    
    // </rtc-template>

    // Consumer declaration
    // <rtc-template block="consumer_declare">
    
    // </rtc-template>


}
