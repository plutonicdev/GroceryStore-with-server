package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class InitiatePayment {

    @SerializedName("server_resp")
    private ServerResp serverResp;
    @SerializedName("pg_details")
    private PgDetails pgDetails;

    public ServerResp getServerResp() {
        return serverResp;
    }

    public void setServerResp(ServerResp serverResp) {
        this.serverResp = serverResp;
    }

    public PgDetails getPgDetails() {
        return pgDetails;
    }

    public void setPgDetails(PgDetails pgDetails) {
        this.pgDetails = pgDetails;
    }

}
