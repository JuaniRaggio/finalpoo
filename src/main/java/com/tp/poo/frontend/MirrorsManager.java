package com.tp.poo.frontend;

import javafx.scene.control.CheckBox;

import java.util.EnumMap;

public class MirrorsManager extends EnumMap<Mirrors,CheckBox> implements VisualManager<Mirrors> {

    public MirrorsManager(){
        super(Mirrors.class);
    }

}
