package com.tp.poo.frontend;

import com.tp.poo.frontend.Figures.CustomizeFigure;
import javafx.scene.control.CheckBox;

import java.util.EnumMap;
import java.util.Map;

public class EffectsManager extends EnumMap<Effects,CheckBox> implements VisualManager<Effects> {

        public EffectsManager() {
            super(Effects.class);
            for (Effects effect : Effects.values()) {
                CheckBox cb = new CheckBox(effect.toString());
                this.put(effect, cb);
            }
        }

        @Override
        public void applyTo(CustomizeFigure figure) {
            for (Map.Entry<Effects, CheckBox> entry : this.entrySet()) {
                figure.setFilter(entry.getKey(), entry.getValue().isSelected());
            }
        }

        @Override
        public void syncFrom(CustomizeFigure figure) {
            for (Map.Entry<Effects, CheckBox> entry : this.entrySet()) {
                entry.getValue().setSelected(figure.getFilters().contains(entry.getKey()));
            }
        }

        @Override
        public Class<Effects> getEnumClass() {
        return Effects.class;
        }


}
