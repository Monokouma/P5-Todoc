package com.monokoumacorporation.todoc;

import static org.junit.Assert.assertEquals;

import com.monokoumacorporation.todoc.utils.MainApplication;

import org.junit.Test;

public class MainApplicationTest {

    @Test
    public void nominal_case() {
        // When
        MainApplication application = new MainApplication();

        // Then
        assertEquals(
            MainApplication.getInstance(),
            application
        );
    }
}
