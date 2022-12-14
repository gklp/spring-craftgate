package io.gklp.craftgate;

public final class Constants {

    private Constants() {
    }

    public enum PaymentPaths {
        CARD_PAYMENTS("/payment/v1/card-payments"),
        CARD_PAYMENTS_RETRIEVE("/payment/v1/card-payments/{id}"),
        INIT_3D("/payment/v1/card-payments/3ds-init"),
        COMPLETE_3D("/payment/v1/card-payments/3ds-complete"),
        POST_AUTH_PAYMENT("/payment/v1/card-payments/{paymentId}/post-auth"),
        INIT_CHECKOUT_PAYMENT("/payment/v1/checkout-payments/init"),
        RETRIEVE_CHECKOUT_PAYMENT("/payment/v1/checkout-payments/{token}"),
        DEPOSIT_PAYMENT("/payment/v1/deposits"),
        INIT_3D_DEPOSIT("/payment/v1/deposits/3ds-init"),
        COMPLETE_3D_DEPOSIT_PAYMENT("/payment/v1/deposits/3ds-complete"),
        CREATE_FUND_TRANSFER_DEPOSITS_PAYMENT("/payment/v1/deposits/fund-transfer"),
        INIT_GARANTI_PAY_PAYMENT("/payment/v1/garanti-pay-payments"),
        INIT_APM_PAYMENT("/payment/v1/apm-payments/init"),
        COMPLETE_APM_PAYMENT("/payment/v1/apm-payments/complete"),
        RETRIEVE_LOYALTIES("/payment/v1/card-loyalties/retrieve"),
        CREATE_APM_PAYMENT("/payment/v1/apm-payments"),
        REFUND_PAYMENT_TRANSACTION("/payment/v1/refund-transactions"),
        RETRIEVE_REFUND_PAYMENT_TRANSACTION("/payment/v1/refund-transactions/{id}"),
        REFUND_PAYMENT("/payment/v1/refunds"),
        RETRIEVE_REFUND_PAYMENT("/payment/v1/refunds/{id}"),
        STORE_CARD("/payment/v1/cards"),
        UPDATE_CARD("/payment/v1/cards/update"),
        SEARCH_CARD("/payment/v1/cards"),
        DELETE_CARD("/payment/v1/cards"),
        APPROVE_PAYMENT_TRANSACTION("/payment/v1/payment-transactions/approve"),
        DISAPPROVE_PAYMENT_TRANSACTION("/payment/v1/payment-transactions/disapprove"),
        CHECK_MASTER_PASS_USER("/payment/v1/masterpass-payments/check-user"),
        UPDATE_PAYMENT_TRANSACTION("/payment/v1/payment-transactions/{paymentTransactionId}");

        private final String path;

        PaymentPaths(String path) {
            this.path = path;
        }

        public String path() {
            return path;
        }
    }

    public enum PaymentReportingPaths {
        SEARCH_PAYMENTS("/payment-reporting/v1/payments");

        private final String path;

        PaymentReportingPaths(String path) {
            this.path = path;
        }

        public String path() {
            return path;
        }
    }

    public enum CraftgateHeaders {
        X_API_KEY("x-api-key"),
        X_AUTH_VERSION("x-auth-version"),
        X_RND_KEY("x-rnd-key"),
        X_SIGNATURE("x-signature"),
        X_CLIENT_VERSION("x-client-version");

        private final String headerKey;

        CraftgateHeaders(String headerKey) {
            this.headerKey = headerKey;
        }

        public String getHeaderKey() {
            return headerKey;
        }
    }
}
