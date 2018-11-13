package com.utility.consts;

/**
 * 引擎相关常量对象
 * @author 何春节
 * @version 1.0
 */
public interface EngineConst {
	
	/**
	 * 1：N搜索引擎
	 */
	public interface Search {
		
		// 添加人脸
		public static final String FACEDB_ADD_FACE = "/platform/staticdb/engine/addFace";
		
		// 删除人脸
		public static final String FACEDB_REMOVE_FACE = "/platform/staticdb/engine/removeFace";
		
		// 人脸识别
		public static final String FACE_SEARCH_RECOG = "/platform/staticdb/recog/singleLibrary";
	}

	/**
	 * 云之眼提供的各种接口地址
	 */
	public interface Service {
		
		// 人脸检测
		public static final String FACE_TOOL_DETECT = "/face/tool/detect";
		
		// 人脸属性分析
		public static final String FACE_TOOL_ATTRIBUTE = "/face/tool/attribute";
		
		// 人脸质量分析
		public static final String FACE_TOOL_QUALITY = "/face/tool/quality";
		
		// 人脸相识度比较,防hack
		public static final String FACE_TOOL_COMPARE_EXT0 = "/face/tool/compare/ext0";	
		
		// 人脸相识度比较
		public static final String FACE_TOOL_COMPARE = "/face/tool/compare";
		
		// 人脸相识度比较扩展4
		public static final String FACE_TOOL_COMPARE_EXT4 = "/face/tool/compare/ext4";
		
		// 人脸去网纹
		public static final String FACE_TOOL_RMWATER = "/face/tool/removeWater";
		
		// 人脸特征提取
		public static final String FACE_TOOL_FEATURE = "/face/tool/feature";
		
		// 人脸特征融合
		public static final String FACE_TOOL_MERGE_FEATURE = "/face/tool/mergeFeature";
		
		// 特征相识度
		public static final String FACE_TOOL_SIMILARITYBYFEATURE = "/face/tool/similarityByFeature";
		
		// 添加人脸
		public static final String FACE_CLUSTERIING_FACE_CREATE = "/face/clustering/face/create";
		
		// 删除人脸
		public static final String FACE_CLUSTERING_FACE_DELETE = "/face/clustering/face/delete";
		
		// 添加组
		public static final String FACE_CLUSTERING_GROUP_CREATE = "/face/clustering/group/create";
		
		// 删除组
		public static final String FACE_CLUSTERING_GROUP_DELETE = "/face/clustering/group/delete";
		
		// 组中添加人脸
		public static final String FACE_CLUSTERING_GROUP_ADDFACE = "/face/clustering/group/addFace";
		
		// 组中删除人脸
		public static final String FACE_CLUSTERING_GROUP_RMFACE = "/face/clustering/group/removeFace";
		
		// 查询组中人脸数目
		public static final String FACE_CLUSTERING_GROUP_QUERY = "/face/clustering/group/query";
		
		// 人脸识别
		public static final String FACE_RECOG_GROUP_IDENTIFY = "/face/recog/group/identify";
		
		// 人脸识别扩展1
		public static final String FACE_RECOG_GROUP_IDENTIFY_EXT1 = "/face/recog/group/identify/ext1";
		
		/**
		 * 水印相关
		 */		
		
		//添加数字水印
		public static final String TOOL_DIGITALWATER_EXT1_ADD = "/tool/digitalwater/ext1/add";
		
		//检测数字水印
		public static final String TOOL_DIGITALWATER_EXT1_DETECT = "/tool/digitalwater/ext1/detect";
		
		//添加图片水印
		public static final String TOOL_DIGITALWATER_EXT2_ADD = "/tool/digitalwater/ext2/add";
		
		//检测图片水印
		public static final String TOOL_DIGITALWATER_EXT2_DETECT = "/tool/digitalwater/ext2/detect";
				
	}
}
