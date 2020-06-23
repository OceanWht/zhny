package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.ProductStructureModel;

import java.util.List;

public interface YNCompanyProductStructureService {

    RestResponse<JSONObject> companyProductStructureAdd(ProductStructureModel productStructureModel);

    RestResponse<JSONObject> companyProductStructureUpdate(ProductStructureModel productStructureModel);

    RestResponse<JSONObject> companyProductStructureDelete(ProductStructureModel productStructureModel);

    List<ProductStructureModel> companyProductStructureSelect(String enterpriseCode);
}
