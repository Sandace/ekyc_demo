package com.example.ekycdemo.models;

import java.io.Serializable;

public class KycDataModel implements Serializable {
   public boolean status;
   public KycData data;

   public boolean isStatus() {
      return status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }

   public KycData getData() {
      return data;
   }

   public void setData(KycData data) {
      this.data = data;
   }
}
