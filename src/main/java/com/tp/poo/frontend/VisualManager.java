package com.tp.poo.frontend;

import javafx.scene.control.CheckBox;

import java.util.Map;

public interface VisualManager<E extends Enum<E>> extends Map<E, CheckBox> {

    default void initialize(){
        for (E visual : this.keySet()) {
            CheckBox checkBox = createCheckBox(visual.toString());
            this.put(visual, checkBox);
        }
    }

    private CheckBox createCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setMinWidth(UIConstants.DEFAULT_BUTTON_MIN_WIDTH);
        checkBox.setCursor(UIConstants.DEFAULT_CURSOR_STYLE);
        return checkBox;
    }

    //caso de una clase generica que "unifique" a mirrors y effects
    //   public GenericVisualManager(Class<E> enumClass) {
    //        this.enumClass = enumClass;
    //        for (E value : enumClass.getEnumConstants()) {
    //            CheckBox cb = new CheckBox(value.toString());
    //            cb.setMinWidth(UIConstants.DEFAULT_BUTTON_MIN_WIDTH);
    //            cb.setCursor(UIConstants.DEFAULT_CURSOR_STYLE);
    //            map.put(value, cb);
    //        }
    //    }
    //
    //    @Override
    //    public EnumSet<E> getActive() {
    //        EnumSet<E> active = EnumSet.noneOf(enumClass);
    //        for (Map.Entry<E, CheckBox> entry : map.entrySet()) {
    //            if (entry.getValue().isSelected()) {
    //                active.add(entry.getKey());
    //            }
    //        }
    //        return active;
    //    }
    //
    //    @Override
    //    public void applyTo(CustomizeFigure figure) {
    //        for (Map.Entry<E, CheckBox> entry : map.entrySet()) {
    //            if (entry.getKey() instanceof Effects e) {
    //                figure.setFilter(e, entry.getValue().isSelected());
    //            } else if (entry.getKey() instanceof Mirrors m) {
    //                figure.setMirror(m, entry.getValue().isSelected());
    //            }
    //        }
    //    }
    //
    //    @Override
    //    public void syncFrom(CustomizeFigure figure) {
    //        for (Map.Entry<E, CheckBox> entry : map.entrySet()) {
    //            boolean active = false;
    //            if (entry.getKey() instanceof Effects e) {
    //                active = figure.getFilters().contains(e);
    //            } else if (entry.getKey() instanceof Mirrors m) {
    //                active = figure.getMirrors().containsKey(m);
    //            }
    //            entry.getValue().setSelected(active);
    //        }
    //    }
    //
    //    @Override
    //    public Class<E> getEnumClass() {
    //        return enumClass;
    //    }
}
