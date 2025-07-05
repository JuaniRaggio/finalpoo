package com.tp.poo.frontend;

import java.util.EnumMap;
import java.util.Map;

public class MirrorManager implements Visuals <MirrorType> {
    
    private final Map<MirrorType, Boolean> activeMirrors = new EnumMap<>(MirrorType.class);
    
    public void toggleMirror(MirrorType mirrorType, boolean active, CustomizeFigure selectedFigure) {
        activeMirrors.put(mirrorType, active);
        if (selectedFigure != null) {
            if (mirrorType == MirrorType.HORIZONTAL) {
                selectedFigure.setHorizontalMirror(active);
            } else {
                selectedFigure.setVerticalMirror(active);
            }
        }
    }
    
    public boolean isMirrorActive(MirrorType mirrorType) {
        return activeMirrors.getOrDefault(mirrorType, false);
    }
    
    public void syncWithFigure(CustomizeFigure figure) {
        if (figure != null) {
            activeMirrors.put(MirrorType.HORIZONTAL, figure.isHMirror());
            activeMirrors.put(MirrorType.VERTICAL, figure.isVMirror());
        }
    }
} 
