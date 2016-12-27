package com.geariot.bigdata.crawler.house.model;

public enum RESCODE {

	SUCCESS(0,"成功"), EXPIRE(1,"token过期"), INVALID_SIG(2,"signature错误"), WRONG_PARAM(3,"参数错误"), WRONG_DEVICE(4,"设备无权限调用") ,
	NO_DEVICE(5,"无调用设备类型参数"), DUPLICATE_PHONE(6,"该手机号已注册"), NOT_FOUND(7,"无该条记录"), NO_SIG(8,"无signature参数"),
	PSW_ERROR(9,"密码错误"),UPDATE_ERROR(10,"更新数据错误"),CREATE_ERROR(11,"存储数据错误"),DATE_FORMAT_ERROR(12,"日期格式错误"),DELETE_ERROR(13,"删除错误"),
	DUPLICATED_ERROR(14,"重复数据"),FILE_ERROR(15,"上传文件错误");

    // 定义私有变量
    private int nCode;

    private String nMsg;
    // 构造函数，枚举类型只能为私有
    private RESCODE(int _nCode, String _nMsg) {

        this.nCode = _nCode;
        this.nMsg = _nMsg;
    }

    public String getMsg() {

        return nMsg;
    }
    
    @Override
    public String toString() {

        return String.valueOf(this.nCode);

    }
}
