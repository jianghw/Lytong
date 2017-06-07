package com.zantong.mobilecttx.utils;

import java.util.LinkedList;

public class VehicleTypeTools {
    public static String  switchVehicleType(String number){
        String vehicleName = null;
        if("01".equals(number)){
            vehicleName = "大型汽车";
        }else if("02".equals(number)){
            vehicleName = "小型汽车";
        }else if("03".equals(number)){
            vehicleName = "使馆汽车";
        }else if("04".equals(number)){
            vehicleName = "领馆汽车";
        }else if("05".equals(number)){
            vehicleName = "境外汽车";
        }else if("06".equals(number)){
            vehicleName = "外籍汽车";
        }else if("07".equals(number)){
            vehicleName = "两、三轮摩托车";
        }else if("08".equals(number)){
            vehicleName = "轻便摩托车";
        }else if("09".equals(number)){
            vehicleName = "使馆摩托车";
        }else if("10".equals(number)){
            vehicleName = "领馆摩托车";
        }else if("11".equals(number)){
            vehicleName = "境外摩托车";
        }else if("12".equals(number)){
            vehicleName = "外籍摩托车";
        }else if("13".equals(number)){
            vehicleName = "农用运输车";
        }else if("14".equals(number)){
            vehicleName = "拖拉机";
        }else if("15".equals(number)){
            vehicleName = "挂车";
        }else if("16".equals(number)){
            vehicleName = "教练汽车";
        }else if("17".equals(number)){
            vehicleName = "教练摩托车";
        }else if("18".equals(number)){
            vehicleName = "试验汽车";
        }else if("19".equals(number)){
            vehicleName = "试验摩托车";
        }else if("20".equals(number)){
            vehicleName = "临时入境汽车";
        }else if("21".equals(number)){
            vehicleName = "临时入境摩托车";
        }else if("22".equals(number)){
            vehicleName = "临时行驶车";
        }else if("23".equals(number)){
            vehicleName = "警用汽车";
        }else if("24".equals(number)){
            vehicleName = "警用摩托";
        }else if("51".equals(number)){
            vehicleName = "大型新能源汽车";
        }else if("52".equals(number)){
            vehicleName = "小型新能源汽车";
        }



        return vehicleName;
    }
    public static String  switchVehicleCode(String number){
        String vehicleName = "02";
        if("大型汽车".equals(number)){
            vehicleName = "01";
        }else if("小型汽车".equals(number)){
            vehicleName = "02";
        }else if("使馆汽车".equals(number)){
            vehicleName = "03";
        }else if("领馆汽车".equals(number)){
            vehicleName = "04";
        }else if("境外汽车".equals(number)){
            vehicleName = "05";
        }else if("外籍汽车".equals(number)){
            vehicleName = "06";
        }else if("两、三轮摩托车".equals(number)){
            vehicleName = "07";
        }else if("轻便摩托车".equals(number)){
            vehicleName = "08";
        }else if("使馆摩托车".equals(number)){
            vehicleName = "09";
        }else if("领馆摩托车".equals(number)){
            vehicleName = "10";
        }else if("境外摩托车".equals(number)){
            vehicleName = "11";
        }else if("外籍摩托车".equals(number)){
            vehicleName = "12";
        }else if("农用运输车".equals(number)){
            vehicleName = "13";
        }else if("拖拉机".equals(number)){
            vehicleName = "14";
        }else if("挂车".equals(number)){
            vehicleName = "15";
        }else if("教练汽车".equals(number)){
            vehicleName = "16";
        }else if("教练摩托车".equals(number)){
            vehicleName = "17";
        }else if("试验汽车".equals(number)){
            vehicleName = "18";
        }else if("试验摩托车".equals(number)){
            vehicleName = "19";
        }else if("临时入境汽车".equals(number)){
            vehicleName = "20";
        }else if("临时入境摩托车".equals(number)){
            vehicleName = "21";
        }else if("临时行驶车".equals(number)){
            vehicleName = "22";
        }else if("警用汽车".equals(number)){
            vehicleName = "23";
        }else if("大型新能源汽车".equals(number)){
            vehicleName = "51";
        }else if("小型新能源汽车".equals(number)){
            vehicleName = "52";
        }
        return vehicleName;
    }

    public static LinkedList<String> getVehicleType(){
        LinkedList<String> vehicleType = new LinkedList<>();
        vehicleType.add("大型汽车");
        vehicleType.add("小型汽车");
        vehicleType.add("使馆汽车");
        vehicleType.add("领馆汽车");
        vehicleType.add("境外汽车");
        vehicleType.add("外籍汽车");
        vehicleType.add("两、三轮摩托车");
        vehicleType.add("轻便摩托车");
        vehicleType.add("使馆摩托车");
        vehicleType.add("境外摩托车");
        vehicleType.add("外籍摩托车");
        vehicleType.add("农用运输车");
        vehicleType.add("拖拉机");
        vehicleType.add("挂车");
        vehicleType.add("教练汽车");
        vehicleType.add("教练摩托车");
        vehicleType.add("试验汽车");
        vehicleType.add("试验摩托车");
        vehicleType.add("临时入境汽车");
        vehicleType.add("临时入境摩托车");
        vehicleType.add("临时行驶车");
        vehicleType.add("警用汽车");
        vehicleType.add("警用摩托");
        vehicleType.add("大型新能源汽车");
        vehicleType.add("小型新能源汽车");
        return vehicleType;
    }

}
