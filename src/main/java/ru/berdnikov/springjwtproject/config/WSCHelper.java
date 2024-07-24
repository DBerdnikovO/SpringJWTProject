package ru.berdnikov.springjwtproject.config;

import org.springframework.stereotype.Component;

/**
 * @author danilaberdnikov on WSCHelper.
 * @project SpringJWTProject
 */
@Component
public class WSCHelper {
    protected enum Roles {
        ADMIN, USER;
    }

    protected enum Resources {
        LOGIN("/login/**"),
        REGISTRATION("/reg/**"),
        REFRESH("/refresh/**"),
        ADMIN("/admin/**"),
        USER("/user/**"),
        PUBLIC("/public/**");

        private String path;

        Resources(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
