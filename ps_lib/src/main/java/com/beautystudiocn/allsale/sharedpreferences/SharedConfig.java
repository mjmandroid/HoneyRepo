package com.beautystudiocn.allsale.sharedpreferences;

import android.content.Context;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/5/10 10:44
 */

public class SharedConfig {
    private static final String DEFAULT_SHARE_FLAG = "Share";
    private static final String DEFAULT_SHARE_FILE_NAME = "DefaultFile";
    /**
     * 用户标识
     */
    private String shareFlag;
    /**
     * 文件标识
     */
    private String shareFileName;
    /**
     * 是否版本控制(是：每次版本变更，新版本数据初始为空)
     */
    private boolean isVersionControl;
    private int shareMode;

    private SharedConfig(Builder builder) {
        this.shareFlag = builder.shareFlag;
        this.shareFileName = builder.shareFileName;
        this.shareMode = builder.shareMode;
        this.isVersionControl = builder.isVersionControl;
    }

    public String getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(String shareFlag) {
        this.shareFlag = shareFlag;
    }

    public String getShareFileName() {
        return shareFileName;
    }

    public void setShareFileName(String shareFileName) {
        this.shareFileName = shareFileName;
    }

    public int getShareMode() {
        return shareMode;
    }

    public void setShareMode(int shareMode) {
        this.shareMode = shareMode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isVersionControl() {
        return isVersionControl;
    }

    public void setVersionControl(boolean versionControl) {
        isVersionControl = versionControl;
    }

    public static class Builder {
        private String shareFlag = DEFAULT_SHARE_FLAG;
        private String shareFileName = DEFAULT_SHARE_FILE_NAME;
        private boolean isVersionControl = false;
        private int shareMode = Context.MODE_PRIVATE;

        private Builder() {
        }

        public String getShareFlag() {
            return shareFlag;
        }

        public Builder setShareFlag(String shareFlag) {
            this.shareFlag = shareFlag;
            return this;
        }

        public String getShareFileName() {
            return shareFileName;
        }

        public Builder setShareFileName(String shareFileName) {
            this.shareFileName = shareFileName;
            return this;
        }

        public int getShareMode() {
            return shareMode;
        }

        public Builder setShareMode(int shareMode) {
            this.shareMode = shareMode;
            return this;
        }

        public boolean isVersionControl() {
            return isVersionControl;
        }

        public Builder setVersionControl(boolean versionControl) {
            isVersionControl = versionControl;
            return this;
        }

        public SharedConfig build() {
            return new SharedConfig(this);
        }
    }
}
