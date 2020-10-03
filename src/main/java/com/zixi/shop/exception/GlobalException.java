package com.zixi.shop.exception;

import com.zixi.shop.common.AppResultData;

public class GlobalException extends RuntimeException {
    AppResultData<String> appResultData;
    public GlobalException(AppResultData<String> appResultData){
       this.appResultData=appResultData;
    }

    public AppResultData<String> getAppResultData() {
        return appResultData;
    }
}
