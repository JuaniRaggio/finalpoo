package com.tp.poo.frontend;

import javafx.scene.control.CheckBox;

import java.util.EnumMap;

public class EffectsManager extends EnumMap<Effects,CheckBox> implements VisualManager<Effects>{

    public EffectsManager() {
        super(Effects.class);

    }


}
