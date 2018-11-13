package com.util;

/**
 * config的参数的Key值 参见：config.properties
 */
public class PropsParam {

	// 临时文件根路径
	public static final String UPLOAD_FILE_PATH = "upload.file.path";

	// 上传图片根路径
	public static final String TMP_ROOT_PATH = "tmp.root.path";

	// NAS根路径
	public static final String NAS_ROOT_PATH = "nas.root.path";

	// 定时任务标识
	public static final String START_TIMES_SCH = "start.schedule.flag";
	
	// 批量删除流水记录时，每次删除的记录数量
	public static final String DEL_FLOW_BATNUM = "delele.flow.batnum";

	// 人脸识别服务器http地址
	public static final String RECOG_SERVER_URL = "faceServer_url";

	// 人脸识别服务器app_id
	public static final String RECOG_APP_ID = "app_id";

	// 人脸识别服务器app_secret
	public static final String RECOG_APP_SECRET = "app_secret";

	// 通讯是否加密标识
	public static final String IS_ENCRYPT = "ibis.faceRequest.isencrypt";

	// 加密秘钥
	public static final String ENCRYPT_KEY = "ibis.faceRequest.key";

	// 1：N接口访问URL
	public static final String SEARCH_SERVICE_URL = "search.service.url";
	
	// 默认人脸库ID
	public static final String SEARCH_FACEDBID = "search.default.facedbid";
	
	/*
	 * 图片最大小范围
	 */
	public static final String IMAGE_MAX_SIZE = "image.max.size";
	public static final String IMAGE_MIN_SIZE = "image.min.size";
	
	/**
	 * 定义现场中间位置的区域，以逗号分割，分别表示：左边间距，右边间距
	 */
	public static final String IMAGE_LOCATION_LEFT = "image.location.left";
	public static final String IMAGE_LOCATION_RIGHT = "image.location.right";
	/**
	 * 监控配置
	 */
	//启动标志
	public static final String MONITIOR_START_FLAG = "monitor.start.flag";
	//数据监控中心ip
	public static final String MONITIOR_SOCKET_IP = "monitor.socket.ip";
	//数据监控中心端口
	public static final String MONITIOR_SOCKET_PORT = "monitor.socket.port";
	//监控流水清理时间点
	public static final String MONITIOR_FLOWCLEAN_TIME = "monitor.flowClean.time";
	//监控数据库连接测试执行间隔时间
	public static final String MONITIOR_DATABASELINK_TIME = "monitor.databaseLink.time";
	//监控引擎连接测试执行间隔时间
	public static final String MONITIOR_ENGINESTATE_TIME = "monitor.engineState.time";
	//监控交易状态执行间隔时间
	public static final String MONITIOR_TRANSACTIONSTATUS_TIME = "monitor.transactionStatus.time";
	//监控心跳测试执行间隔时间
	public static final String MONITIOR_HEARTSTATE_TIME = "monitor.heartState.time";	
	//心跳监控中心ip
	public static final String MONITIOR_HEART_SOCKET_IP = "monitor.heart.socket.ip";
	//心跳监控中心端口
	public static final String MONITIOR_HEART_SOCKET_PORT = "monitor.heart.socket.port";
	//心跳监控实例名称
	public static final String MONITIOR_HEART_SYSTEM_NAME = "monitor.heart.system.name";
	//监控系统模块名称
	public static final String MONITIOR_CATEGORY_NAME = "monitor.category.name";
	
	/**
	 * 用户登录密码错误次数,允许连续输入密码错误次数（次）
	 */
	public static final String USER_LOGIN_WRONGTIMES = "user.login.wrongtimes";
	/**
	 * 多次输入密码错误用户锁定时间（分钟）
	 */
	public static final String USER_FORBIDDEN_MINUTE = "user.forbidden.minute";
	/**
	 * 用户密码过期时间(天)
	 */
	public static final String USER_EXPIRE_DAYS = "user.expire.days";
	
	/**
	 * 用户登录session失效时间
	 */
	public static final String LOGIN_SESSION_TIME = "login.session.time";
	
	/**
	 * 用户历史密码校验开关，0关闭\1开启（修改密码时，修改密码不能与历史前三次密码设置相同）
	 */
	public static final String USER_HISTORY_PASSWORD = "user.history.password";
	
	
	/**
	 * 水印配置
	 */
	public static final String WATER_CHNNEL = "water.channel";
		
}
