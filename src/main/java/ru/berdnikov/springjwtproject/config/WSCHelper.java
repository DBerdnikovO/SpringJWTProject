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
        AUTH("/auth/**"),
        ADMIN("/admin/**"),
        USER("/user/**"),
        PUBLIC("/public/**"),
        SWAGGER("/swagger-ui/**"),
        SWAGGERV3("/v3/**");

        private String path;

        Resources(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
