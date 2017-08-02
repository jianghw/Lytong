package com.zantong.mobilecttx.car.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianghw on 2017/7/28.
 * Description:  addVehiclelicense HTTP/1.1
 * Update by:
 * Update day:
 */

public class VehicleLicenseBean implements Parcelable {


    /**
     * id : 1171
     * plateNo : GRL2Q7kwZRMUVimxk9q7zg==
     * fileNum : null
     * vehicleType : 02
     * usrnum : 00022515400010001
     * address : null
     * useCharacter : null
     * carModel : null
     * vin : null
     * engineNo : vjMiJjX2HFA=
     * registerDate : null
     * issueDate : 
     * approvedPeople : null
     * totalMass : null
     * tractionMass : null
     * approvedCapacity : null
     * overallDimesion : null
     * memo : null
     * inspectionRecord : null
     * isDeleted : null
     * brandId : null
     * seriesId : null
     * carModelId : null
     * gasolineType : null
     * engineOilType : null
     * ownerName : null
     * userId : 5419
     * image : null
     * isPayable : 0
     * brand : null
     * series : null
     */

    private int id;
    private String plateNo;
    private String fileNum;
    private String vehicleType;
    private String usrnum;
    private String address;
    private String useCharacter;
    private String carModel;
    private String vin;
    private String engineNo;
    private String registerDate;
    private String issueDate;
    private String approvedPeople;
    private String totalMass;
    private String tractionMass;
    private String approvedCapacity;
    private String overallDimesion;
    private String memo;
    private String inspectionRecord;
    private String isDeleted;
    private String brandId;
    private String seriesId;
    private String carModelId;
    private String gasolineType;
    private String engineOilType;
    private String ownerName;
    private int userId;
    private String image;
    private int isPayable;
    private String brand;
    private String series;

    public VehicleLicenseBean(int isPayable) {
        this.isPayable = isPayable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getUsrnum() {
        return usrnum;
    }

    public void setUsrnum(String usrnum) {
        this.usrnum = usrnum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUseCharacter() {
        return useCharacter;
    }

    public void setUseCharacter(String useCharacter) {
        this.useCharacter = useCharacter;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getApprovedPeople() {
        return approvedPeople;
    }

    public void setApprovedPeople(String approvedPeople) {
        this.approvedPeople = approvedPeople;
    }

    public String getTotalMass() {
        return totalMass;
    }

    public void setTotalMass(String totalMass) {
        this.totalMass = totalMass;
    }

    public String getTractionMass() {
        return tractionMass;
    }

    public void setTractionMass(String tractionMass) {
        this.tractionMass = tractionMass;
    }

    public String getApprovedCapacity() {
        return approvedCapacity;
    }

    public void setApprovedCapacity(String approvedCapacity) {
        this.approvedCapacity = approvedCapacity;
    }

    public String getOverallDimesion() {
        return overallDimesion;
    }

    public void setOverallDimesion(String overallDimesion) {
        this.overallDimesion = overallDimesion;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getInspectionRecord() {
        return inspectionRecord;
    }

    public void setInspectionRecord(String inspectionRecord) {
        this.inspectionRecord = inspectionRecord;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(String carModelId) {
        this.carModelId = carModelId;
    }

    public String getGasolineType() {
        return gasolineType;
    }

    public void setGasolineType(String gasolineType) {
        this.gasolineType = gasolineType;
    }

    public String getEngineOilType() {
        return engineOilType;
    }

    public void setEngineOilType(String engineOilType) {
        this.engineOilType = engineOilType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsPayable() {
        return isPayable;
    }

    public void setIsPayable(int isPayable) {
        this.isPayable = isPayable;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.plateNo);
        dest.writeString(this.fileNum);
        dest.writeString(this.vehicleType);
        dest.writeString(this.usrnum);
        dest.writeString(this.address);
        dest.writeString(this.useCharacter);
        dest.writeString(this.carModel);
        dest.writeString(this.vin);
        dest.writeString(this.engineNo);
        dest.writeString(this.registerDate);
        dest.writeString(this.issueDate);
        dest.writeString(this.approvedPeople);
        dest.writeString(this.totalMass);
        dest.writeString(this.tractionMass);
        dest.writeString(this.approvedCapacity);
        dest.writeString(this.overallDimesion);
        dest.writeString(this.memo);
        dest.writeString(this.inspectionRecord);
        dest.writeString(this.isDeleted);
        dest.writeString(this.brandId);
        dest.writeString(this.seriesId);
        dest.writeString(this.carModelId);
        dest.writeString(this.gasolineType);
        dest.writeString(this.engineOilType);
        dest.writeString(this.ownerName);
        dest.writeInt(this.userId);
        dest.writeString(this.image);
        dest.writeInt(this.isPayable);
        dest.writeString(this.brand);
        dest.writeString(this.series);
    }

    protected VehicleLicenseBean(Parcel in) {
        this.id = in.readInt();
        this.plateNo = in.readString();
        this.fileNum = in.readString();
        this.vehicleType = in.readString();
        this.usrnum = in.readString();
        this.address = in.readString();
        this.useCharacter = in.readString();
        this.carModel = in.readString();
        this.vin = in.readString();
        this.engineNo = in.readString();
        this.registerDate = in.readString();
        this.issueDate = in.readString();
        this.approvedPeople = in.readString();
        this.totalMass = in.readString();
        this.tractionMass = in.readString();
        this.approvedCapacity = in.readString();
        this.overallDimesion = in.readString();
        this.memo = in.readString();
        this.inspectionRecord = in.readString();
        this.isDeleted = in.readString();
        this.brandId = in.readString();
        this.seriesId = in.readString();
        this.carModelId = in.readString();
        this.gasolineType = in.readString();
        this.engineOilType = in.readString();
        this.ownerName = in.readString();
        this.userId = in.readInt();
        this.image = in.readString();
        this.isPayable = in.readInt();
        this.brand = in.readString();
        this.series = in.readString();
    }

    public static final Parcelable.Creator<VehicleLicenseBean> CREATOR = new Parcelable.Creator<VehicleLicenseBean>() {
        @Override
        public VehicleLicenseBean createFromParcel(Parcel source) {
            return new VehicleLicenseBean(source);
        }

        @Override
        public VehicleLicenseBean[] newArray(int size) {
            return new VehicleLicenseBean[size];
        }
    };
}
