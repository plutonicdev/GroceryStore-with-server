package com.quintus.labs.grocerystore.payment.payu;

public class AppPreference {

    private String dummyAmount = "10";//"10";
    private String dummyEmail = "xyz@gmail.com";//"";//d.basak.db@gmail.com
    private String dummyPhone = "9876543210";//"";//d.basak.db@gmail.com
    private String productInfo ="product_info";// "product_info";
    private String firstName = "firstname"; //"firstname";
    private boolean isOverrideResultScreen = true;

    public static final String USER_EMAIL = "user_email";
    public static final String USER_MOBILE = "user_mobile";
    public static final String PHONE_PATTERN = "^[987]\\d{9}$";
    public static final long MENU_DELAY = 300;
    public static String USER_DETAILS = "user_details";
    public static int selectedTheme = -1;

    private boolean isDisableWallet, isDisableSavedCards, isDisableNetBanking, isDisableThirdPartyWallets, isDisableExitConfirmation;

    boolean isDisableWallet() {
        return isDisableWallet;
    }

    void setDisableWallet(boolean disableWallet) {
        isDisableWallet = disableWallet;
    }

    boolean isDisableSavedCards() {
        return isDisableSavedCards;
    }

    void setDisableSavedCards(boolean disableSavedCards) {
        isDisableSavedCards = disableSavedCards;
    }

    boolean isDisableNetBanking() {
        return isDisableNetBanking;
    }

    void setDisableNetBanking(boolean disableNetBanking) {
        isDisableNetBanking = disableNetBanking;
    }
    boolean isDisableThirdPartyWallets() {
        return isDisableThirdPartyWallets;
    }

    void setDisableThirdPartyWallets(boolean disableThirdPartyWallets) {
        isDisableThirdPartyWallets = disableThirdPartyWallets;
    }
    boolean isDisableExitConfirmation() {
        return isDisableExitConfirmation;
    }

    void setDisableExitConfirmation(boolean disableExitConfirmation) {
        isDisableExitConfirmation = disableExitConfirmation;
    }

    public String getDummyAmount() {
        return dummyAmount;
    }

    public void setDummyAmount(String dummyAmount) {
        this.dummyAmount = dummyAmount;
    }

    public String getDummyEmail() {
        return dummyEmail;
    }

    public void setDummyEmail(String dummyEmail) {
        this.dummyEmail = dummyEmail;
    }

    public String getDummyPhone() {
        return dummyPhone;
    }

    public void setDummyPhone(String dummyPhone) {
        this.dummyPhone = dummyPhone;
    }

    public boolean isOverrideResultScreen() {
        return isOverrideResultScreen;
    }

    public void setOverrideResultScreen(boolean overrideResultScreen) {
        isOverrideResultScreen = overrideResultScreen;
    }
    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}