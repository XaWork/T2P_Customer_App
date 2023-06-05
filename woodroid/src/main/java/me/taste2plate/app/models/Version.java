package me.taste2plate.app.models;

import com.google.gson.annotations.SerializedName;

public class Version{

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public class Data{

		@SerializedName("vendor_ios")
		private String vendorIos;

		@SerializedName("logistic_android")
		private String logisticAndroid;

		@SerializedName("vendor_android")
		private String vendorAndroid;

		@SerializedName("user_ios")
		private String userIos;

		@SerializedName("user_android")
		private String userAndroid;

		@SerializedName("logistic_ios")
		private String logisticIos;

		public void setVendorIos(String vendorIos){
			this.vendorIos = vendorIos;
		}

		public String getVendorIos(){
			return vendorIos;
		}

		public void setLogisticAndroid(String logisticAndroid){
			this.logisticAndroid = logisticAndroid;
		}

		public String getLogisticAndroid(){
			return logisticAndroid;
		}

		public void setVendorAndroid(String vendorAndroid){
			this.vendorAndroid = vendorAndroid;
		}

		public String getVendorAndroid(){
			return vendorAndroid;
		}

		public void setUserIos(String userIos){
			this.userIos = userIos;
		}

		public String getUserIos(){
			return userIos;
		}

		public void setUserAndroid(String userAndroid){
			this.userAndroid = userAndroid;
		}

		public String getUserAndroid(){
			return userAndroid;
		}

		public void setLogisticIos(String logisticIos){
			this.logisticIos = logisticIos;
		}

		public String getLogisticIos(){
			return logisticIos;
		}

	}
}