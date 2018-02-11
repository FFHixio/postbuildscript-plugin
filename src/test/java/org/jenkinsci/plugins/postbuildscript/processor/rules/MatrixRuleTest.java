package org.jenkinsci.plugins.postbuildscript.processor.rules;

import org.jenkinsci.plugins.postbuildscript.model.ExecuteOn;
import org.jenkinsci.plugins.postbuildscript.model.PostBuildItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MatrixRuleTest {

    private MatrixRule matrixRule = new MatrixRule();

    @Mock
    private PostBuildItem item;

    @Test
    public void allowsAxesExecutionIfNotEndOfMatrixBuild() {

        given(item.getExecuteOn()).willReturn(ExecuteOn.AXES);

        boolean actual = matrixRule.allows(item, false);

        assertThat(actual, is(true));

    }

    @Test
    public void allowsMatrixExecutionIfEndOfMatrixBuild() {

        given(item.getExecuteOn()).willReturn(ExecuteOn.MATRIX);

        boolean actual = matrixRule.allows(item, true);

        assertThat(actual, is(true));

    }

    @Test
    public void allowsExecutionIfBothAndNotEndOfMatrixBuild() {

        given(item.getExecuteOn()).willReturn(ExecuteOn.BOTH);

        boolean actual = matrixRule.allows(item, false);

        assertThat(actual, is(true));

    }

    @Test
    public void allowsExecutionIfBothAndEndOfMatrixBuild() {

        given(item.getExecuteOn()).willReturn(ExecuteOn.BOTH);

        boolean actual = matrixRule.allows(item, true);

        assertThat(actual, is(true));

    }

    @Test
    public void deniesAxesExecutionIfEndOfMatrixBuild() {

        given(item.getExecuteOn()).willReturn(ExecuteOn.AXES);

        boolean actual = matrixRule.allows(item, true);

        assertThat(actual, is(false));

    }

    @Test
    public void deniesMatrixExecutionIfNotEndOfMatrixBuild() {

        given(item.getExecuteOn()).willReturn(ExecuteOn.MATRIX);

        boolean actual = matrixRule.allows(item, false);

        assertThat(actual, is(false));

    }

    @Test
    public void formatsViolationMessageForMatrix() {

        given(item.getExecuteOn()).willReturn(ExecuteOn.MATRIX);

        String violationMessage = matrixRule.formatViolationMessage(item, "scriptName");

        assertThat(violationMessage, containsString("scriptName"));
        assertThat(violationMessage, containsString("MATRIX"));

    }

    @Test
    public void formatsViolationMessageForAxes() {

        given(item.getExecuteOn()).willReturn(ExecuteOn.AXES);

        String violationMessage = matrixRule.formatViolationMessage(item, "scriptName");

        assertThat(violationMessage, containsString("scriptName"));
        assertThat(violationMessage, containsString("AXES"));

    }

}
