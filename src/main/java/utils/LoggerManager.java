/**
 * Copyright (c) 2023 Jala University.
 *
 * This software is the confidential and proprieraty information of Jala University
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Licence agreement you entered into with Jala University.
 */
package utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

public class LoggerManager {
    private Logger logger;
    private static LoggerManager instance;

    protected LoggerManager() {
        initialize();
    }

    public static LoggerManager getInstance() {
        if (instance == null || instance.logger == null) {
            instance = new LoggerManager();
        }
        return instance;
    }

    private void initialize() {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        File file = new File("log4j2.properties");
        context.setConfigLocation(file.toURI());
        logger = LogManager.getLogger(LogManager.class);
    }

    private void log(Level level, String message) {
        logger.log(level, message);
    }

    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    public void error(String message) {
        log(Level.ERROR, message);
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void warn(String message) {
        log(Level.WARN, message);
    }
}
