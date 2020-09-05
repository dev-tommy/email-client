package pl.devtommy.controller;

public enum EmailSendingResult {
    SUCCESS,
    NO_RECIPIENT,
    INVALID_RECIPIENT,
    FAILED_BY_PROVIDER,
    FAILED_BY_UNEXPECTED_ERROR;
}
