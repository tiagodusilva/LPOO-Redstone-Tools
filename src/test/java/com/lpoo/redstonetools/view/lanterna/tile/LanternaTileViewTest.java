package com.lpoo.redstonetools.view.lanterna.tile;

import com.lpoo.redstonetools.model.utils.Power;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LanternaTileViewTest {

    private LanternaTileView tileView;

    @Before
    public void setup() {
        this.tileView = Mockito.mock(LanternaTileView.class, Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

}
