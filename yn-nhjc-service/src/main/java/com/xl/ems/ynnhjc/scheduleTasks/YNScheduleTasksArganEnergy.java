package com.xl.ems.ynnhjc.scheduleTasks;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.base.Strings;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.ArganEnergyConsumeModelMapper;
import com.xl.ems.ynnhjc.mapper.ArganEnergyIdcModelMapper;
import com.xl.ems.ynnhjc.mapper.ArganEnergyWarmModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.ArganEnergyConsumeModel;
import com.xl.ems.ynnhjc.model.ArganEnergyIdcModel;
import com.xl.ems.ynnhjc.model.ArganEnergyWarmModel;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;

@RestController
@EnableScheduling
public class YNScheduleTasksArganEnergy {
	
	@Autowired
	ArganEnergyConsumeModelMapper arganEnergyConsumeModelMapper;
	
	@Autowired
	ArganEnergyIdcModelMapper arganEnergyIdcModelMapper;
	
	@Autowired
	ArganEnergyWarmModelMapper arganEnergyWarmModelMapper;
	
	@Autowired
	AKModelMapper akModelMapper;
	
	@Autowired
    GenericRest genericRest;
	
	@Autowired
    GetUrlUtils getUrlUtils;
	
	@Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNScheduleTasksArganEnergy.class);
	
	@Scheduled(cron = "0 0 17 * * ?")
	//@Scheduled(fixedDelay = 1000*120)
	public void ScheduleTasks_addYNService(){
		Map<String, Object> params = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		params.put("startDate", sdf.format(calendar.getTime()) + " 00:00:00");
		params.put("endDate", sdf.format(new Date()) + " 23:59:59");
		/*********************************************Energy********************************************************/
		try {
			//查询当天和昨天的数据，按照i0字段的值去筛选，0:已经成功上传、1:上传失败、2:待上传
			List<ArganEnergyConsumeModel> Consume_list = arganEnergyConsumeModelMapper.selectUnLoadingData(params);
			for (int i = 0; i < Consume_list.size(); i++) {
				ArganEnergyConsumeModel arganEnergyConsumeModel = Consume_list.get(i);
				String enterpriseCode = arganEnergyConsumeModel.getEnterprisecode();
			    if (Strings.isNullOrEmpty(enterpriseCode)) {
			        LOGGER.info("YNScheduleTasksArganEnergy add enterpriseCode is null......");
			    }
			    String apiUrl = getUrlUtils.getUrl(apiUrlType);
			    String softUrl = getUrlUtils.getUrl(softUrlType);
			    if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
			        LOGGER.error("YNScheduleTasksArganEnergy add apiUrl or softUrl is null......");
			    }
			    AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
			    if (akModel == null) {
			        LOGGER.error("YNScheduleTasksArganEnergy add akModel is null......");
			    }
			    String token = akModel.getToken();
			    if (Strings.isNullOrEmpty(token)) {
			        LOGGER.error("YNScheduleTasksArganEnergy add token is null......");
			    }
			    Map<String, Object> body = new Hashtable<>();
			    body.put("ArganEnergyConsumeModel", arganEnergyConsumeModel);
			    body.put("softurl", softUrl);
			    body.put("token", token);
			    ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/arganEnergy/addEnergy", body,
			            new ParameterizedTypeReference<RestResponse<JSONObject>>() {
			            });
			    if (entity.getStatusCode().equals(HttpStatus.OK)) {
			    	JSONObject res = entity.getBody().getResult();
			    	if (res.getInteger("responseCode") == 200) {
			    		JSONObject data = res.getJSONObject("data");
			    		String retime = data.getString("platformTime");
			    		params.put("reportDate",retime);
			    		params.put("i0",0);
			    		params.put("id", arganEnergyConsumeModel.getId());
			    		arganEnergyConsumeModelMapper.updateById_Energy(params);
			    	}else{
			    		params.put("reportDate",sdf.format(new Date()));
			    		params.put("i0",1);
			    		params.put("id", arganEnergyConsumeModel.getId());
			    		arganEnergyConsumeModelMapper.updateById_Energy(params);
			    		LOGGER.info("YNScheduleTasksArganEnergy add res has error:{" + res.getString("responseCode") + ":"
			                    + res.getString("responseMessage") + "}......");
			    	}
			    }
			}
		} catch (Exception e) {
			LOGGER.info("YNScheduleTasksArganEnergy Energy ScheduleTasks error......");
			e.printStackTrace();
		}
		/*********************************************IDC******************************************************/
		try {
			//查询当天和昨天的数据，按照i0字段的值去筛选，0:已经成功上传、1:上传失败、2:待上传
			List<ArganEnergyIdcModel> Idc_list = arganEnergyIdcModelMapper.selectUnLoadingData(params);
			for (int i = 0; i < Idc_list.size(); i++) {
				ArganEnergyIdcModel arganEnergyIdcModel = Idc_list.get(i);
				String enterpriseCode = arganEnergyIdcModel.getEnterprisecode();
			    if (Strings.isNullOrEmpty(enterpriseCode)) {
			        LOGGER.info("YNScheduleTasksArganEnergy add enterpriseCode is null......");
			    }
			    String apiUrl = getUrlUtils.getUrl(apiUrlType);
			    String softUrl = getUrlUtils.getUrl(softUrlType);
			    if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
			        LOGGER.error("YNScheduleTasksArganEnergy add apiUrl or softUrl is null......");
			    }
			    AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
			    if (akModel == null) {
			        LOGGER.error("YNScheduleTasksArganEnergy add akModel is null......");
			    }
			    String token = akModel.getToken();
			    if (Strings.isNullOrEmpty(token)) {
			        LOGGER.error("YNScheduleTasksArganEnergy add token is null......");
			    }
			    Map<String, Object> body = new Hashtable<>();
			    body.put("ArganEnergyIdcModel", arganEnergyIdcModel);
			    body.put("softurl", softUrl);
			    body.put("token", token);
			    ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/arganEnergy/addIDC", body,
			            new ParameterizedTypeReference<RestResponse<JSONObject>>() {
			            });
			    if (entity.getStatusCode().equals(HttpStatus.OK)) {
			    	JSONObject res = entity.getBody().getResult();
			    	if (res.getInteger("responseCode") == 200) {
			    		JSONObject data = res.getJSONObject("data");
			    		String retime = data.getString("platformTime");
			    		params.put("reportDate",retime);
			    		params.put("i0",0);
			    		params.put("id", arganEnergyIdcModel.getId());
			    		arganEnergyIdcModelMapper.updateById_IDC(params);
			    	}else{
			    		params.put("reportDate",sdf.format(new Date()));
			    		params.put("i0",1);
			    		params.put("id", arganEnergyIdcModel.getId());
			    		arganEnergyIdcModelMapper.updateById_IDC(params);
			    		LOGGER.info("YNScheduleTasksArganEnergy add res has error:{" + res.getString("responseCode") + ":"
			                    + res.getString("responseMessage") + "}......");
			    	}
			    }
			}
		} catch (Exception e) {
			LOGGER.info("YNScheduleTasksArganEnergy IDC ScheduleTasks error......");
			e.printStackTrace();
		}
		/**********************************************Warm*****************************************************/
		try {
			//查询当天和昨天的数据，按照i0字段的值去筛选，0:已经成功上传、1:上传失败、2:待上传
			List<ArganEnergyWarmModel> Warm_list = arganEnergyWarmModelMapper.selectUnLoadingData(params);
			for (int i = 0; i < Warm_list.size(); i++) {
				ArganEnergyWarmModel arganEnergyWarmModel = Warm_list.get(i);
				String enterpriseCode = arganEnergyWarmModel.getEnterprisecode();
			    if (Strings.isNullOrEmpty(enterpriseCode)) {
			        LOGGER.info("YNScheduleTasksArganEnergy add enterpriseCode is null......");
			    }
			    String apiUrl = getUrlUtils.getUrl(apiUrlType);
			    String softUrl = getUrlUtils.getUrl(softUrlType);
			    if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
			        LOGGER.error("YNScheduleTasksArganEnergy add apiUrl or softUrl is null......");
			    }
			    AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
			    if (akModel == null) {
			        LOGGER.error("YNScheduleTasksArganEnergy add akModel is null......");
			    }
			    String token = akModel.getToken();
			    if (Strings.isNullOrEmpty(token)) {
			        LOGGER.error("YNScheduleTasksArganEnergy add token is null......");
			    }
			    Map<String, Object> body = new Hashtable<>();
			    body.put("ArganEnergyWarmModel", arganEnergyWarmModel);
			    body.put("softurl", softUrl);
			    body.put("token", token);
			    ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/arganEnergy/addWarm", body,
			            new ParameterizedTypeReference<RestResponse<JSONObject>>() {
			            });
			    if (entity.getStatusCode().equals(HttpStatus.OK)) {
			    	JSONObject res = entity.getBody().getResult();
			    	if (res.getInteger("responseCode") == 200) {
			    		JSONObject data = res.getJSONObject("data");
			    		String retime = data.getString("platformTime");
			    		params.put("reportDate",retime);
			    		params.put("i0",0);
			    		params.put("id", arganEnergyWarmModel.getId());
			    		arganEnergyWarmModelMapper.updateById_Warm(params);
			    	}else{
			    		params.put("reportDate",sdf.format(new Date()));
			    		params.put("i0",1);
			    		params.put("id", arganEnergyWarmModel.getId());
			    		arganEnergyWarmModelMapper.updateById_Warm(params);
			    		LOGGER.info("YNScheduleTasksArganEnergy add res has error:{" + res.getString("responseCode") + ":"
			                    + res.getString("responseMessage") + "}......");
			    	}
			    }
			}
		} catch (Exception e) {
			LOGGER.info("YNScheduleTasksArganEnergy Warm ScheduleTasks error......");
			e.printStackTrace();
		}
		/***************************************************************************************************/
	}
}
