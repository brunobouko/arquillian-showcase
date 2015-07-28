package be.bouko.webservice;

public class AddressIdThreadLocal {
    private static ThreadLocal<String> addressId = new ThreadLocal<String>();

    public static String getMessageId() {
        return addressId.get();
    }

    public static void setMessageId(String messageId) {
        addressId.set(messageId);
    }
}
