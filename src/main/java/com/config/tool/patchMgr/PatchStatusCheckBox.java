package com.config.tool.patchMgr;

import java.io.File;

public class PatchStatusCheckBox {
        public final File file;
        public boolean selected;

        public PatchStatusCheckBox(File file, boolean selected) {
            this.file = file;
            this.selected = selected;
        }

        public File getFile() {
            return file;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }