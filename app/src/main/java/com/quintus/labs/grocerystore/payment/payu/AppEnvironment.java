package com.quintus.labs.grocerystore.payment.payu;


public class AppEnvironment {

    public enum Type {SANDBOX, PRODUCTION}

    ;
    private Type mode;
    private String merchantKey;
    private String merchantID;
    private String salt;
    private boolean debug;
    private String baseUrl;


    public String merchant_Key() {
        return merchantKey;
    }

    public AppEnvironment setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
        return this;
    }

    public String merchant_ID() {
        return merchantID;
    }

    public AppEnvironment setMerchantID(String merchantID) {
        this.merchantID = merchantID;
        return this;
    }

    public String salt() {
        return salt;
    }

    public AppEnvironment setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public boolean debug() {
        return debug;
    }

    public AppEnvironment setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public Type getMode() {
        return mode;
    }

    public AppEnvironment setMode(Type mode) {
        this.mode = mode;
        return this;
    }

    public String furl() {
        return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
    }

    public String surl() {
        return "https://www.payumoney.com/mobileapp/payumoney/success.php";
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public AppEnvironment setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
