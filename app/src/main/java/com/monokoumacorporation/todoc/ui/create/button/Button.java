package com.monokoumacorporation.todoc.ui.create.button;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.monokoumacorporation.todoc.R;

public enum Button {
    Tartampion(R.string.projet_tartampion, R.color.charcoal, 1),
    Lucidia(R.string.projet_lucidia, R.color.charcoal, 2),
    Circus(R.string.projet_circus, R.color.charcoal, 3);

    @StringRes
    private final int projectName;

    @ColorRes
    private final int backgroundColor;

    private final int id;

    Button(int projectName, int backgroundColor, int id) {
        this.projectName = projectName;
        this.backgroundColor = backgroundColor;
        this.id = id;
    }

    public int getProjectName() {
        return projectName;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getId() {
        return id;
    }
}
