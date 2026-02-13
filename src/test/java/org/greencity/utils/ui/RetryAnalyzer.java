package org.greencity.utils.ui;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult result) {
        return retryCount++ < MAX_RETRY;
    }
}
