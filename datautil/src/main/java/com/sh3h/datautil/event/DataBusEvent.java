package com.sh3h.datautil.event;


public class DataBusEvent {
    public static class ParserResult {
        public enum OperationType {
            APK_START,
            APK_DOING,
            APK_END,
            DATA_START,
            DATA_DOING,
            DATA_END
        }

        private OperationType operationType;
        private boolean isSuccess;
        private String message;

        public ParserResult(OperationType operationType, boolean isSuccess, String message) {
            this.operationType = operationType;
            this.message = message;
            this.isSuccess = isSuccess;
        }

        public OperationType getOperationType() {
            return operationType;
        }

        public void setOperationType(OperationType operationType) {
            this.operationType = operationType;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
