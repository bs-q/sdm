package q.sdm.utils;

public class ErrorUtils {
    private ErrorUtils(){
        // can not init
    }

    public static final String CUSTOMER_ERROR_UNAUTHORIZED = "ERROR-CUSTOMER-000";
    public static final String CUSTOMER_ERROR_PHONE_EXIST = "ERROR-CUSTOMER-002";
    public static final String CUSTOMER_ERROR_EMAIL_EXIST = "ERROR-CUSTOMER-005";
    public static final String CUSTOMER_ERROR_OLD_PWD_NOT_MATCH = "ERROR-CUSTOMER-006";
    public static final String GENERAL_ERROR_LOGIN_FAILED = "ERROR-GENERAL-003";

    public static String getMessageFromError(String errorCode){
        switch (errorCode){
            case CUSTOMER_ERROR_UNAUTHORIZED:
                return "Phiên làm việc hết hạn, vui lòng đăng nhập lại";
            case CUSTOMER_ERROR_PHONE_EXIST:
                return "Số điện thoại này đã được đăng kí";
            case CUSTOMER_ERROR_EMAIL_EXIST:
                return "Email này đã được đăng kí";
            case CUSTOMER_ERROR_OLD_PWD_NOT_MATCH:
                return "Mật khẩu cũ không chính xác";
            case GENERAL_ERROR_LOGIN_FAILED:
                return "Đăng nhập không thành công, vui lòng kiểm tra lại số điện thoại mà mật khẩu";
            default:
                break;
        }

        return "Có lỗi xảy ra, vui lòng thử lại!";
    }
}
