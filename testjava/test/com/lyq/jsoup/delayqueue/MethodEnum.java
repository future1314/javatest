package thread.test.com.lyq.jsoup.delayqueue;

public enum MethodEnum {

    SEND_MESSAGE(0, "handlerSendMessage"),
    AUTO_BUY(1, "handlerAutoBuy");

    private int index;
    private String methodName;

    public static MethodEnum getMethodEnumByIndex(int index) {
        MethodEnum[] methodEnums = values();
        for (int i = 0; i < methodEnums.length; i++) {
            MethodEnum methodEnum = methodEnums[i];
            if (methodEnum.getIndex() == index) {
                return methodEnum;
            }
        }
        return null;
    }

    MethodEnum(int index, String methodName) {
        this.index = index;
        this.methodName = methodName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


}
