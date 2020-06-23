package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.ProductOutputValueModel;

import java.util.List;

public interface YNCompanyOutputValueService {

    RestResponse<JSONObject> YNCompanyOutputValueAdd(ProductOutputValueModel productOutputValueModel);

    RestResponse<JSONObject> YNCompanyOutputValueUpdate(ProductOutputValueModel productOutputValueModel);

    RestResponse<JSONObject> YNCompanyOutputValueDelete(ProductOutputValueModel productOutputValueModel);

    List<ProductOutputValueModel> companyOutputValueSelect(String enterpriseCode);
}
